package com.example.sqlitevsroom.view.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitevsroom.databinding.ItemStudentBinding
import com.example.sqlitevsroom.sqlite.Student

class StudentAdapter : RecyclerView.Adapter<StudentViewHolder>() {
    private var students: MutableList<Student> = mutableListOf()

    fun addStudents(students: List<Student>) {
        this.students.clear()
        this.students.addAll(students)
    }

    fun addStudent(student: Student) {
        this.students.add(0, student)
        notifyItemInserted(0)
    }

    fun updateStudent(student: Student) {
        this.students.find { it.studentId == student.studentId }
            ?.let {
                it.firstName = student.firstName
                it.lastName = student.lastName
            }
        val targetIndex = this.students.indexOf(student)
        notifyItemChanged(targetIndex)
    }

    fun removeStudent(student: Student) {
        val targetIndex = students.indexOf(student)
        students.removeAt(targetIndex)
        notifyItemRemoved(targetIndex)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): StudentViewHolder {
        return StudentViewHolder(
            ItemStudentBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: StudentViewHolder, position: Int) {
        viewHolder.bind(students[position])
    }

    override fun getItemCount(): Int {
        return students.size
    }
}