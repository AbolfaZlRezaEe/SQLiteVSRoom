package com.example.sqlitevsroom.view.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitevsroom.databinding.ItemStudentBinding
import com.example.sqlitevsroom.sqlite.Student

class StudentViewHolder(
    private val binding: ItemStudentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(student: Student) {
        binding.studentIdTextView.text = student.studentId.toString()
        binding.studentFullNameTextView.text =
            "${student.firstName} ${student.lastName}"
    }
}