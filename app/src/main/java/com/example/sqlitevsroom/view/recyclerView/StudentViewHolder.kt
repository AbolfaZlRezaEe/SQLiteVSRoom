package com.example.sqlitevsroom.view.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitevsroom.databinding.ItemStudentBinding
import com.example.sqlitevsroom.model.dataclass.StudentRoomDataclass
import com.example.sqlitevsroom.model.dataclass.StudentSQLiteDataclass

class StudentViewHolder(
    private val binding: ItemStudentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(student: StudentSQLiteDataclass) {
        binding.studentIdTextView.text = student.studentId.toString()
        binding.studentFullNameTextView.text =
            "${student.firstName} ${student.lastName}"
    }

    fun bind(student: StudentRoomDataclass) {
        binding.studentIdTextView.text = student.studentId.toString()
        binding.studentFullNameTextView.text =
            "${student.firstName} ${student.lastName}"
    }
}