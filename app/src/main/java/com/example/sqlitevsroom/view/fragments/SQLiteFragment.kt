package com.example.sqlitevsroom.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitevsroom.R
import com.example.sqlitevsroom.databinding.FragmentSqliteBinding
import com.example.sqlitevsroom.model.dataclass.Student
import com.example.sqlitevsroom.model.sqlite.StudentDatabaseHelper
import com.example.sqlitevsroom.model.sqlite.StudentOperations
import com.example.sqlitevsroom.model.sqlite.StudentQueryObject
import com.example.sqlitevsroom.view.bottomSheets.StudentForumDialogFragment
import com.example.sqlitevsroom.view.recyclerView.StudentAdapter
import com.google.android.material.snackbar.Snackbar

class SQLiteFragment : Fragment() {
    private var _binding: FragmentSqliteBinding? = null
    private val binding get() = _binding!!

    private lateinit var studentDatabaseHelper: StudentDatabaseHelper
    private var studentAdapter: StudentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSqliteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        initDatabase()
    }

    private fun initDatabase() {
        studentDatabaseHelper = StudentDatabaseHelper(requireContext())
        studentDatabaseHelper.doOperation {
            val students = StudentOperations
                .getAllStudentInformation(database = studentDatabaseHelper)
            requireActivity().runOnUiThread { initializeStudentAdapter(students) }
        }
    }

    private fun initializeStudentAdapter(students: List<Student>) {
        if (students.isEmpty()) {
            showEmptyState(true)
        } else {
            showEmptyState(false)

            if (studentAdapter == null) {
                studentAdapter = StudentAdapter()
            }
            studentAdapter?.addStudents(students)

            binding.studentsSqliteRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.studentsSqliteRecyclerView.adapter = studentAdapter

            initAdapterListeners()
        }
    }

    private fun showEmptyState(show: Boolean) {
        if (show) {
            binding.emptyStateTextView.visibility = View.VISIBLE
            binding.searchSQLiteEditText.visibility = View.GONE
        } else {
            binding.emptyStateTextView.visibility = View.GONE
            binding.searchSQLiteEditText.visibility = View.VISIBLE
        }
    }

    private fun initAdapterListeners() {
        studentAdapter?.setStudentClickListener { student ->
            StudentForumDialogFragment
                .newInstance(student) { finalStudentInformation, bottomSheet ->
                    studentDatabaseHelper.doOperation {
                        val itemUpdated = StudentOperations.updateStudent(
                            database = studentDatabaseHelper,
                            finalStudentInformation
                        )
                        if (itemUpdated) {
                            // Bring operation back to the main thread to do ui stuff
                            requireActivity().runOnUiThread {
                                studentAdapter?.updateStudent(finalStudentInformation)
                                bottomSheet.dismiss()
                            }
                        } else {
                            requireActivity().runOnUiThread {
                                bottomSheet.setError(resources.getString(R.string.updatingFailedMessage))
                            }
                        }
                    }
                }.show(childFragmentManager, null)
        }

        studentAdapter?.setStudentLongClickListener { student ->
            studentDatabaseHelper.doOperation {
                val itemDeleted = StudentOperations.deleteStudent(
                    database = studentDatabaseHelper,
                    student.studentId
                )
                if (itemDeleted) {
                    requireActivity().runOnUiThread {
                        studentAdapter?.removeStudent(student)
                        if (studentAdapter?.listIsEmpty()!!) {
                            showEmptyState(true)
                        }
                    }
                } else {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.deletingFailedMessage),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initListeners() {
        binding.addSqliteStudentExtendedFab.setOnClickListener {
            StudentForumDialogFragment
                .newInstance { finalStudentInformation, bottomSheet ->
                    studentDatabaseHelper.doOperation {
                        val studentId = StudentOperations.insertStudent(
                            database = studentDatabaseHelper,
                            firstName = finalStudentInformation.firstName,
                            lastName = finalStudentInformation.lastName,
                        )
                        if (studentId != -1L) {
                            finalStudentInformation.studentId = studentId.toInt()
                            // Bring operation back to the main thread to do ui stuff
                            requireActivity().runOnUiThread {
                                studentAdapter?.addStudent(finalStudentInformation)
                                showEmptyState(false)
                                binding.studentsSqliteRecyclerView.smoothScrollToPosition(0)
                                bottomSheet.dismiss()
                            }
                        } else {
                            requireActivity().runOnUiThread {
                                bottomSheet.setError(resources.getString(R.string.insertingFailedMessage))
                            }
                        }
                    }
                }.show(childFragmentManager, null)
        }

        binding.searchSQLiteEditText.addTextChangedListener { editable ->
            if (editable != null) {
                if (editable.toString().isEmpty()) {
                    studentDatabaseHelper.doOperation {
                        val students = StudentOperations
                            .getAllStudentInformation(database = studentDatabaseHelper)
                        requireActivity().runOnUiThread { initializeStudentAdapter(students) }
                    }
                } else {
                    studentDatabaseHelper.doOperation {
                        val searchedStudents = StudentOperations.searchStudentFromDatabase(
                            database = studentDatabaseHelper,
                            whichPropertyForCondition = StudentQueryObject.QUERY_SEARCH_WITH_FIRST_NAME,
                            valueOfPropertyShouldMatch = arrayOf("%$editable%"),
                        )
                        requireActivity().runOnUiThread {
                            studentAdapter?.addStudents(searchedStudents)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        studentDatabaseHelper.close()
        super.onDestroy()
    }
}