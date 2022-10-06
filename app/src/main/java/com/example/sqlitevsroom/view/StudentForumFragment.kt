package com.example.sqlitevsroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sqlitevsroom.databinding.StudentForumFragmentBinding
import com.example.sqlitevsroom.sqlite.Student

class StudentForumFragment : Fragment() {
    private var _binding: StudentForumFragmentBinding? = null
    private val binding get() = _binding!!

    private var student: Student? = null
    private var onSubmitClickListener: ((student: Student) -> Unit)? = null

    companion object {
        private const val KEY_STUDENT_INFORMATION = "STUDENT_INFORMATION_KEY"

        fun newInstance(
            student: Student,
            submitClickListener: (student: Student) -> Unit
        ): StudentForumFragment {
            return StudentForumFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_STUDENT_INFORMATION, student)
                }
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
                return@setOnClickListener
            } else {
                binding.warningTextView.visibility = View.GONE
            }
            if (student != null) {
                student?.let {
                    it.firstName = binding.firstNameEditText.text.toString()
                    it.lastName = binding.lastNameEditText.text.toString()
                }
            } else {
                student = Student(
                    firstName = binding.firstNameEditText.text.toString(),
                    lastName = binding.lastNameEditText.text.toString()
                )
            }
            if (onSubmitClickListener != null) {
                onSubmitClickListener?.invoke(student!!)
            }
        }
    }

    private fun readFromBundleIfExist() {
        val studentInformation = arguments?.getParcelable<Student>(KEY_STUDENT_INFORMATION)
        studentInformation?.let {
            this.student = it
            binding.firstNameEditText.setText(it.firstName)
            binding.lastNameEditText.setText(it.lastName)
            binding.idMaterialCardView.visibility = View.VISIBLE
            binding.studentIdTextView.text = it.studentId.toString()
        }
    }

    fun setSubmitClickListener(callBack: (student: Student) -> Unit) {
        this.onSubmitClickListener = callBack
    }
}