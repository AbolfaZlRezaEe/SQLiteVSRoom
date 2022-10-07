package com.example.sqlitevsroom.model.sqlite

import android.content.ContentValues
import com.example.sqlitevsroom.model.dataclass.StudentSQLiteDataclass

object StudentOperations {

    fun insertStudent(
        database: StudentDatabaseHelper,
        firstName: String,
        lastName: String
    ): Long /* returns the id of row that database inserted into it */ {
        val newStudent = ContentValues().apply {
            put(StudentQueryObject.COLUMN_FIRST_NAME, firstName)
            put(StudentQueryObject.COLUMN_LAST_NAME, lastName)
        }
        return database.writableDatabase.insert(
            StudentQueryObject.TABLE_NAME,
            null /* used for nullable values only! */,
            newStudent
        )
    }

    fun searchStudentFromDatabase(
        database: StudentDatabaseHelper,
        informationThatYouNeed: Array<String>? = null,
        whichPropertyForCondition: String,
        valueOfPropertyShouldMatch: Array<String>,
        sortOrder: String = "${StudentQueryObject.COLUMN_STUDENT_ID} ASC"
    ): List<StudentSQLiteDataclass> {
        val cursor = database.readableDatabase.query(
            StudentQueryObject.TABLE_NAME,
            informationThatYouNeed,
            whichPropertyForCondition,
            valueOfPropertyShouldMatch,
            null /* I don't wanna have GroupBy feature */,
            null /* I don't wanna Having feature */,
            sortOrder
        )
        val result = mutableListOf<StudentSQLiteDataclass>()
        with(cursor) {
            while (moveToNext()) {
                val studentIdColumnIndex =
                    cursor.getColumnIndex(StudentQueryObject.COLUMN_STUDENT_ID)
                val studentFirstNameColumnIndex =
                    cursor.getColumnIndex(StudentQueryObject.COLUMN_FIRST_NAME)
                val studentLastNameColumnIndex =
                    cursor.getColumnIndex(StudentQueryObject.COLUMN_LAST_NAME)

                val studentId = cursor.getInt(studentIdColumnIndex)
                val studentFirstName = cursor.getString(studentFirstNameColumnIndex)
                val studentLastName = cursor.getString(studentLastNameColumnIndex)

                result.add(
                    StudentSQLiteDataclass(
                        studentId = studentId,
                        firstName = studentFirstName,
                        lastName = studentLastName
                    )
                )
            }
        }
        // Close the cursor
        cursor.close()
        return result
    }

    fun getAllStudentInformation(
        database: StudentDatabaseHelper,
        informationThatYouNeed: Array<String>? = null,
        sortOrder: String = "${StudentQueryObject.COLUMN_STUDENT_ID} ASC"
    ): List<StudentSQLiteDataclass> {
        val cursor = database.readableDatabase.query(
            StudentQueryObject.TABLE_NAME,
            informationThatYouNeed,
            null /* Give me all students */,
            null /* Give me all students */,
            null /* I don't wanna have GroupBy feature */,
            null /* I don't wanna Having feature */,
            sortOrder
        )

        val students = mutableListOf<StudentSQLiteDataclass>()
        with(cursor) {
            while (moveToNext()) {
                val studentIdColumnIndex =
                    cursor.getColumnIndex(StudentQueryObject.COLUMN_STUDENT_ID)
                val studentFirstNameColumnIndex =
                    cursor.getColumnIndex(StudentQueryObject.COLUMN_FIRST_NAME)
                val studentLastNameColumnIndex =
                    cursor.getColumnIndex(StudentQueryObject.COLUMN_LAST_NAME)

                val studentId = cursor.getInt(studentIdColumnIndex)
                val studentFirstName = cursor.getString(studentFirstNameColumnIndex)
                val studentLastName = cursor.getString(studentLastNameColumnIndex)


                students.add(
                    StudentSQLiteDataclass(
                        studentId = studentId,
                        firstName = studentFirstName,
                        lastName = studentLastName
                    )
                )
            }
        }
        cursor.close()
        return students
    }

    fun deleteStudent(
        database: StudentDatabaseHelper,
        id: Int
    ): Boolean {
        val selection = "${StudentQueryObject.COLUMN_STUDENT_ID} = ?"
        val selectionArg = arrayOf("$id")
        return database.writableDatabase.delete(
            StudentQueryObject.TABLE_NAME,
            selection,
            selectionArg
        ) > 0
    }

    fun updateStudent(
        database: StudentDatabaseHelper,
        student: StudentSQLiteDataclass
    ): Boolean {
        val contentValue = ContentValues().apply {
            put(StudentQueryObject.COLUMN_FIRST_NAME, student.firstName)
            put(StudentQueryObject.COLUMN_LAST_NAME, student.lastName)
        }

        val selection = "${student.studentId} LIKE ?"
        val selectionArg = arrayOf("${student.studentId}")
        return database.writableDatabase.update(
            StudentQueryObject.TABLE_NAME,
            contentValue,
            selection,
            selectionArg
        ) > 0
    }
}