package com.example.sqlitevsroom.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitevsroom.R
import com.example.sqlitevsroom.databinding.FragmentRoomBinding
import com.example.sqlitevsroom.model.dataclass.StudentRoomDataclass
import com.example.sqlitevsroom.model.room.StudentDao
import com.example.sqlitevsroom.model.room.StudentDatabase
import com.example.sqlitevsroom.view.bottomSheets.StudentForumDialogFragment
import com.example.sqlitevsroom.view.bottomSheets.StudentForumDialogFragment.Companion.KEY_ROOM_STUDENT_INFORMATION
import com.example.sqlitevsroom.view.recyclerView.StudentRoomAdapter

class RoomFragment : Fragment() {

    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: StudentDatabase
    private lateinit var dao: StudentDao

    private var studentRoomAdapter: StudentRoomAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        initDatabase()
    }

    private fun initListeners() {
        binding.addRoomStudentFab.setOnClickListener {
            StudentForumDialogFragment
                .newInstance { _, firstName, lastName, bottomSheet ->
                    StudentDatabase.doOperation {
                        val finalStudent = StudentRoomDataclass(
                            firstName = firstName,
                            lastName = lastName
                        )
                        val studentId = dao.insertStudent(finalStudent)
                        if (studentId != -1L) {
                            finalStudent.studentId = studentId.toInt()
                            requireActivity().runOnUiThread {
                                studentRoomAdapter?.addStudent(finalStudent)
                                showEmptyState(false)
                                binding.studentsRoomRecyclerView.smoothScrollToPosition(0)
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

        binding.searchRoomEditText.addTextChangedListener { editable ->
            if (editable != null) {
                if (editable.toString().isEmpty()) {
                    StudentDatabase.doOperation {
                        val students = dao.getAllStudents()
                        requireActivity().runOnUiThread { initializeStudentAdapter(students) }
                    }
                } else {
                    StudentDatabase.doOperation {
                        val searchedStudents = dao.searchByFirstName(editable.toString())
                        requireActivity().runOnUiThread {
                            studentRoomAdapter?.addStudents(searchedStudents)
                        }
                    }
                }
            }
        }
    }

    private fun initDatabase() {
        database = StudentDatabase.initDatabase(requireContext())
        dao = database.getStudentDao()

        StudentDatabase.doOperation {
            val students = dao.getAllStudents()
            requireActivity().runOnUiThread { initializeStudentAdapter(students) }
        }
    }

    private fun initializeStudentAdapter(students: List<StudentRoomDataclass>) {
        if (students.isEmpty()) {
            showEmptyState(true)
        } else {
            showEmptyState(false)

            if (studentRoomAdapter == null) {
                studentRoomAdapter = StudentRoomAdapter()
            }
            studentRoomAdapter?.addStudents(students)

            binding.studentsRoomRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.studentsRoomRecyclerView.adapter = studentRoomAdapter

            initAdapterListeners()
        }
    }

    private fun initAdapterListeners() {
        studentRoomAdapter?.setStudentClickListener { student ->
            val bundle = Bundle().apply {
                putParcelable(KEY_ROOM_STUDENT_INFORMATION, student)
            }
            StudentForumDialogFragment
                .newInstance(bundle) { studentId, firstName, lastName, bottomSheet ->
                    val finalStudent = StudentRoomDataclass(
                        studentId = studentId!!,
                        firstName = firstName,
                        lastName = lastName
                    )
                    StudentDatabase.doOperation {
                        dao.updateStudent(finalStudent)
                    }
                    requireActivity().runOnUiThread {
                        studentRoomAdapter?.updateStudent(finalStudent)
                        bottomSheet.dismiss()
                    }
                }.show(childFragmentManager, null)
        }

        studentRoomAdapter?.setStudentLongClickListener { student ->
            StudentDatabase.doOperation {
                dao.deleteStudent(student)
                requireActivity().runOnUiThread {
                    studentRoomAdapter?.removeStudent(student)
                    if (studentRoomAdapter?.listIsEmpty()!!) {
                        showEmptyState(true)
                    }
                }

            }
        }
    }

    private fun showEmptyState(show: Boolean) {
        if (show) {
            binding.emptyStateTextView.visibility = View.VISIBLE
            binding.searchRoomEditText.visibility = View.GONE
        } else {
            binding.emptyStateTextView.visibility = View.GONE
            binding.searchRoomEditText.visibility = View.VISIBLE
        }
    }
}