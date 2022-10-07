package com.example.sqlitevsroom.view.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sqlitevsroom.databinding.StudentForumFragmentBinding
import com.example.sqlitevsroom.model.dataclass.StudentRoomDataclass
import com.example.sqlitevsroom.model.dataclass.StudentSQLiteDataclass
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StudentForumDialogFragment : BottomSheetDialogFragment() {
    private var _binding: StudentForumFragmentBinding? = null
    private val binding get() = _binding!!

    private var sqliteStudent: StudentSQLiteDataclass? = null
    private var roomStudent: StudentRoomDataclass? = null

    private var onSubmitClickListener: ((
        studentId: Int?,
        firstName: String,
        lastName: String,
        bottomSheet: StudentForumDialogFragment
    ) -> Unit)? =
        null

    companion object {
        const val KEY_SQLITE_STUDENT_INFORMATION = "STUDENT_SQLITE_INFORMATION_KEY"
        const val KEY_ROOM_STUDENT_INFORMATION = "STUDENT_ROOM_INFORMATION_KEY"

        fun newInstance(
            bundle: Bundle? = null,
            submitClickListener: (
                studentId: Int?,
                firstName: String,
                lastName: String, bottomSheet: StudentForumDialogFragment
            ) -> Unit
        ): StudentForumDialogFragment {
            return StudentForumDialogFragment().apply {
                arguments = bundle
                setSubmitClickListener(submitClickListener)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StudentForumFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readFromBundleIfExist()

        initListeners()
    }

    private fun initListeners() {
        binding.saveMaterialButton.setOnClickListener {
            if (binding.firstNameEditText.length() == 0
                || binding.lastNameEditText.length() == 0
            ) {
                binding.warningTextView.visibility = View.VISIBLE
                binding.warningTextView.text = "You should fill all information!"
                return@setOnClickListener
            } else {
                binding.warningTextView.visibility = View.GONE
            }

            if (onSubmitClickListener != null) {
                onSubmitClickListener?.invoke(
                    getStudentId(),
                    binding.firstNameEditText.text.toString(),
                    binding.lastNameEditText.text.toString(),
                    this
                )
            }
        }
    }

    private fun getStudentId(): Int? {
        return if (sqliteStudent != null) {
            sqliteStudent?.studentId!!
        } else if (roomStudent != null) {
            roomStudent?.studentId!!
        } else {
            null
        }
    }

    private fun readFromBundleIfExist() {
        val sqliteStudentInformation =
            arguments?.getParcelable<StudentSQLiteDataclass>(KEY_SQLITE_STUDENT_INFORMATION)
        if (sqliteStudentInformation != null) {
            this.sqliteStudent = sqliteStudentInformation
            binding.firstNameEditText.setText(sqliteStudentInformation.firstName)
            binding.lastNameEditText.setText(sqliteStudentInformation.lastName)
            binding.idMaterialCardView.visibility = View.VISIBLE
            binding.studentIdTextView.text = sqliteStudentInformation.studentId.toString()
        } else {
            val roomStudentInformation =
                arguments?.getParcelable<StudentRoomDataclass>(KEY_ROOM_STUDENT_INFORMATION)
            if (roomStudentInformation != null) {
                this.roomStudent = roomStudentInformation
                binding.firstNameEditText.setText(roomStudentInformation.firstName)
                binding.lastNameEditText.setText(roomStudentInformation.lastName)
                binding.idMaterialCardView.visibility = View.VISIBLE
                binding.studentIdTextView.text = roomStudentInformation.studentId.toString()
            }
        }
    }

    fun setSubmitClickListener(
        callBack: (
            studentId: Int?,
            firstName: String,
            lastName: String,
            bottomSheet: StudentForumDialogFragment
        ) -> Unit
    ) {
        this.onSubmitClickListener = callBack
    }

    fun setError(message: String) {
        binding.warningTextView.visibility = View.VISIBLE
        binding.warningTextView.text = message
    }
}