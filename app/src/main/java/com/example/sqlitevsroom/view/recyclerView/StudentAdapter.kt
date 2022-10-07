package com.example.sqlitevsroom.view.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitevsroom.databinding.ItemStudentBinding
import com.example.sqlitevsroom.model.dataclass.Student

class StudentAdapter : RecyclerView.Adapter<StudentViewHolder>() {
    private var students: MutableList<Student> = mutableListOf()
    private var clickListenerCallback: ((student: Student) -> Unit)? = null
    private var onLongClickListenerCallback: ((student: Student) -> Unit)? = null

    fun setStudentClickListener(clickListenerCallback: (student: Student) -> Unit) {
        this.clickListenerCallback = clickListenerCallback
    }

    fun setStudentLongClickListener(onLongClickListenerCallback: (student: Student) -> Unit) {
        this.onLongClickListenerCallback = onLongClickListenerCallback
    }

    fun addStudents(students: List<Student>) {
        this.students.clear()
        this.students.addAll(students)
        notifyDataSetChanged()
    }

    fun addStudent(student: Student) {
        this.students.add(student)
        val targetIndex = students.indexOf(student)
        notifyItemInserted(targetIndex)
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

    fun listIsEmpty(): Boolean {
        return students.isEmpty()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): StudentViewHolder {
        return StudentViewHolder(
            ItemStudentBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        ).setupClickListener(
            { position -> clickListenerCallback?.invoke(students[position]) },
            { position -> onLongClickListenerCallback?.invoke(students[position]) }
        )
    }

    override fun onBindViewHolder(viewHolder: StudentViewHolder, position: Int) {
        viewHolder.bind(students[position])
    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun <T : RecyclerView.ViewHolder> T.setupClickListener(
        onClick: (position: Int) -> Unit,
        onLongClick: (position: Int) -> Unit
    ): T {
        itemView.setOnClickListener {
            onClick.invoke(adapterPosition)
        }
        itemView.setOnLongClickListener {
            onLongClick.invoke(adapterPosition)
            return@setOnLongClickListener false
        }
        return this
    }
}