## :pencil2: SQLite vs Room

This project created for comparing **SQLite** and **Room** library. You can download the project, build and run it, and see the code and read this article to underestand more about SQLite and Room in general. I hope this is help you to learn something new at the end. enjoy :)

> for using this repository, it is better to know **Kotlin language** and enough knowladge about **Android development**

## :question: In general, What is SQLite?

[SQLite](https://en.wikipedia.org/wiki/SQLite) is a library that is used to do SQL operations like **Read, Write, etc…** SQLite wrote with C programming language. as its own documentation says, it has a few third-party libraries that are used in the source code, and in the end, it doesn’t have many dependencies to the other libraries to build and run! this library is used on many platforms, like Android, Web browsers, Operating systems, etc… also, the source code of this project is free and open to use. We can check it out [at this link.](https://github.com/sqlite/sqlite)

## :question: What is SQL in general?

If we wanna explain SQL in a short time, SQL is a simple language for accessing and managing databases that are compatible with this language. SQL stands for **Structured Query Language** so with that, you can have operations like Write, Read, Insert, Delete, Search, etc... on your database. [for more information, you can read this page.](https://www.w3schools.com/sql/sql_intro.asp)

## :scroll: Some samples of SQL language

It’s good to have some examples of SQL for those who wanna know more about this language. I prefer to show five main operations in SQL:

- Create a Table:

```sql
CREATE TABLE tbl_student(
		student_id int,
		first_name varchar(255),
		last_name varchar(255),
		...
);
```

- Insert a Row on a Table:

```sql
INSERT INTO tbl_student(student_id, first_name, last_name) VALUES (5, Abolfazl, Rezaei)
```

- Update a Row on a Table:

```sql
UPDATE tbl_student
SET student_id = 2 , first_name = Abolfazl, last_name = Rezaei
WHERE student_id = 5
```

> You should have conditions for your Update query, because if you don’t, the Update query applied to all of your records on the database.
    
- Delete a Row on a Table:

```sql
DELETE FROM tbl_student WHERE student_id = 2
```

> if you don’t have conditions for the delete query, this query delete all informations from your table in database!

- Search among items of a Table:

```sql
SELECT * FROM tbl_student WHERE student_id = 2
```

## :game_die: What are the features of SQLite?

- **It is Open source:** one of the good things about the open source softwares is, you can edit the interface, source code, and other stuff to make it more efficient for your code. also, it can bring a lot of new features, updating functionalities faster, and more. if you saw an open source project, you can see some sections on that repository like **Issues** or **Pull requests(Merge Request on Gitlab)**. these two sections help a project to improve, fix bugs and bring new features. this is the benefit that an open source project has. for more information, you can see the [**SQLite repository on Github**](https://github.com/sqlite/sqlite).
- **It is a self-contained Project**: as I said at the introduction of this article, SQLite source code doesn’t contain a lot of dependencies outside of the project. [as this documentation says](https://www.sqlite.org/selfcontained.html), it has only a few C libraries for doing all stuff at the end.
- **SQLite is Serverless:** one of the things that most of the SQL libraries should have, is you should have a server or some other machine to run the database into it, and then, use that database via **API** or some other ways. SQLite introduces itself as a **Classic serverless**. it means that it doesn't need to have its own thread, machine, system, etc to run and use.
> If you wanna know more about types of **Serverless** and their definitions, [you can check this link and read the section two.](https://www.sqlite.org/serverless.html)
- **SQLite is zero-configuration:** as you can guess until now, after all these features, SQLite doesn’t need to configure, setup, control, or anything else. as the document says: **SQLite just works =))))**
- **SQLite is Transactional:** If you run an operation(like Insert a new row on the table of database), SQLite warranty that this operation to be completely done or not at all. this is a good thing because if you have an operation, and it doesn’t complete, might damage your data in the database or your whole application crashed.
- **SQLite can work across all platforms:** We mentioned that SQLite doesn’t require any server or configuration to run. so, information should have space to save, right? for that, SQLite represents all information in tables as a file in a platform. and one of the cool things about this feature is, you can read these files between your platforms without any conversion process or changing.

these are all features that SQLite gave us to build a strong Database in our application without worrying about managing and controlling that. everything is controlled on its own.

## :computer: Implementing SQLite in Android

after all these explanations, it’s time to go throw the code and implement SQLite in an application to better understand the code.

so, SQLite has a package in android libraries that cames with core functionalities in that. [as Android documentation explains it]([https://developer.android.com/training/data-storage/sqlite](https://developer.android.com/training/data-storage/sqlite#DbHelper)), we should have a class that extends `SQLiteOpenHelper`. this class contains all functionalities that We should have to do for any operations on the database. when we create our class for maintaining the database, we must override three functions that contain the main functionality of the database: 

- `onCreate` function: when we create an instance of our class, this function is called to let you create your database into your application. so you should call create table query in this function.
- `onUpgrade` function: let’s think about it, when your application grows up, you might change your database and table structure, so you should have the capability to migrate from the old one to the new one. this is when this function comes in. you can write your migration queries and functionalities here.
- `onDowngrade` function: It is clear when you have migration for your database, you should have queries and functionalities for downgrading the database if needed.
> Do we have version control for our database?
> of course, we have. for managing upgrade and downgrade of the database, we must have a version for our database. you can pass this value in the `SQLiteOpenHelper` constructor. for more information about `SQLiteOpenHelper`, [you can read this documentation](https://developer.android.com/training/data-storage/sqlite#DbHelper).
    

### Inserting on the Database

after creating your database, our database is ready to use in our application. but before that, It’s good to mention that Having a helper class for managing operations is so good. because you have your all functionalities in one place, and also you can manage them very easily. for that, we created `StudentOperations` class. you can see it [here](https://github.com/AbolfaZlRezaEe/SQLiteVSRoom/blob/master/app/src/main/java/com/example/sqlitevsroom/model/sqlite/StudentOperations.kt).

for inserting a new row on our table, we must pass all values that we wanna store. for that, Android builds a class that names `ConentValues`. with that you can pass your (key, value) parameters to store in the database(key is the name of the column, and value is the thing that you wanna store on that column). It is somehow like this: 

```kotlin
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
```

> What are `readableDatabase` and `WriteableDatabase`?
> It’s a good question. as you can guess, if you wanna read data from the database, you must use the `readableDatabase` function and have a query to get data from the database. and also for writing into the database, you must use `writableDatabase`. but be careful, these two functions are two heavy for Main-Thread in Android. you should run these operations on a different thread.
    

### Updating a row on the Database

For updating rows in the database, `writableDatabase` has a function called `update`. this function returns an integer as result that shows how many rows are affected by this query. also, we have two parameters here for managing conditions that if they are true, we wanna change. first is `whereClause` and the second is `whereArgs`. `whereClause` defines what value I wanna check, and `whereArgs` defines the other side of check in this definition. let’s have a look at the code:

```kotlin
fun updateStudent(
        database: StudentDatabaseHelper,
        student: StudentSQLiteDataclass
    ): Boolean /* true: object is updated. false: failed or not found */ {
        val contentValue = ContentValues().apply {
            put(StudentQueryObject.COLUMN_FIRST_NAME, student.firstName)
            put(StudentQueryObject.COLUMN_LAST_NAME, student.lastName)
        }

        val selection = "${student.studentId} LIKE ?" /* whereClause */
        val selectionArg = arrayOf("${student.studentId}") /* whereArgs */
        return database.writableDatabase.update(
            StudentQueryObject.TABLE_NAME,
            contentValue,
            selection,
            selectionArg
        ) > 0
    }
```

### Deleting a row on the Database

After we talked about inserting and updating functionalities of SQLite database in android, deleting a row from the Database is easier to understand. so, let’s have a look at the code:

```kotlin
fun deleteStudent(
        database: StudentDatabaseHelper,
        id: Int
    ): Boolean/* true: object is updated. false: failed or not found */ {
        val selection = "${StudentQueryObject.COLUMN_STUDENT_ID} = ?"
        val selectionArg = arrayOf("$id")
        return database.writableDatabase.delete(
            StudentQueryObject.TABLE_NAME,
            selection,
            selectionArg
        ) > 0
    }
```

### Searching into rows on the Database

before explaining how we can search throw rows in the database, we should know about the `Cursor` definition first.

### Cursor:

When we use a database in android, and we do some database operations as a query, android gives us a Cursor as the result. this class contains the result of that query. so as you can guess, we have a Cursor for SQLite in every query. also one of the important things about the cursor is, it is a heavy object in android, and after we use that we must close it to free the resources.

so, searching among rows in android is somehow like this in our application:

```kotlin
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
```

these were all general things that you can do with SQLite. but our journey doesn’t complete yet. If we have a big application and we wanna use the database, raw SQLite is more harmful than useful. because we might have a lot of boilerplate at the end of the day. also, we might have forgotten to write all things that queries should have, and it causes bugs and crashes in our application. this is the time that Room comes in.

## :white_circle: Room, is a better way to use the Database on Android?

Let’s have a short and useful answer at the start of the sentence: definitely Yes!

In general, Room collects all boilerplate codes that you must write to use the SQLite library and make it easier to use in Android applications. also, it has a lot of capabilities for working with other components in android like `LiveData`, `RxJava`, `Kotlin Coroutines`, etc… You can imagine Room as a wrapper on the SQLite library. Room library writes all functionalities in compile time and it can check and show problems to you at the compile time. it is so good because if you are an android developer, you prefer to catch exceptions in compile time rather in run time. also, it uses **Annotations** for managing functionalities in code. let’s see the benefits of Room library in reality:

### Create Your Database and Tables

For creating the Database, we should have an abstract class that is annotated with `@Database` and extends `RoomDatabase`. also, you should specify your entities and the version of your database in this annotation. something like this:

```kotlin
@Database(entities = [StudentRoomDataclass::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
}
```

and also for creating entities in Room, you should annotate your dataclass with `@Entity` annotation and specify the name of the table on that. like this:

```kotlin
@Entity(
    tableName = "tbl_student"
)
data class StudentRoomDataclass(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var studentId: Int = 0,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String
)
```

by default, Room chooses the name that you write for your properties in the dataclass, but you can choose different names with `@ColumnInfo` annotation. also, `@PrimaryKey` defines that this property is the primary key. and if you set `true` for `autoGenerate` value, Room enables `AUTOINCREMENT` for that.

### Write operations that we want

It’s time to write operations in our application. Room calls this `DAO`. with Dao, you specify the queries that you wanna execute in your database. for our application that is like this:

```kotlin
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
```

as you can see, you must have an interface that is annotated with `@Dao` annotation. for inserting items on tables you must use `@Insert`, for updating items you must use `@Update`, for deleting an item you must use `@Delete`, and for having custom queries, you can use `@Query` annotation above your functions in your interface class.

after implementing `StudentDao`, we should introduce it to our Database. for that we should do this:

```kotlin
@Database(entities = [StudentRoomDataclass::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
	abstract fun getStudentDao(): StudentDao
}
```

with this, Room understands that it should implement functionalities of your Dao in the database. so with this, you can use and do operations on your database.

in the end, you can easily create your database like this:

```kotlin
Room.databaseBuilder(
   context, StudentDatabase::class.java, DATABASE_NAME
).build()
```

and then, access the Dao to do operations:

```kotlin
val database = Room.databaseBuilder(
   context, StudentDatabase::class.java, DATABASE_NAME
).build()
val dao = database.getStudentDao()

// doing operations like this:
dao.insertStudent(student)
```

oh, and don’t forget that you must use another thread to run Room operations. because Database operations are two heavy and the main thread doesn’t good for that!

## :blue_book: Conclusion

In all majors, it’s good to know the basics of the subject first, and then, tools or easy ways to use those basics. knowing the basics helps you to understand deeper what is going on under the hood. SQLite is the basic of database stuff in android and also it is behind the Room library codes. so now, you know two things: 1. How SQLite in android works and how can we use that 2. a good tool for using SQLite in android is to use a wrapper that name of it is Room.
