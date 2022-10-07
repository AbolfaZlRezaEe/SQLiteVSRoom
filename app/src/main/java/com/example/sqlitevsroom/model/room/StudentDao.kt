package com.example.sqlitevsroom.model.room

import androidx.room.*
import com.example.sqlitevsroom.model.dataclass.StudentRoomDataclass

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: StudentRoomDataclass): Long

    @Update
    fun updateStudent(student: StudentRoomDataclass)

    @Delete
    fun deleteStudent(student: StudentRoomDataclass)

    @Query("SELECT * FROM tbl_student")
    fun getAllStudents(): List<StudentRoomDataclass>

    @Query("SELECT * FROM tbl_student WHERE first_name LIKE '%' || :firstName || '%'")
    fun searchByFirstName(firstName: String): List<StudentRoomDataclass>
}