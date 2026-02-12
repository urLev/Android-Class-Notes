package com.goble.studentlist.data

import kotlinx.coroutines.delay

class StudentRepository {
    suspend fun fetchStudentData(): Student {
        delay(2000)
        return Student(
            name = "Gabe",
            studentId = "xxxxxx",
            pronouns = "he/him",
            gradYear = 2026,
            currentClasses = listOf(
                Course(
                    name = "Basket Weaving",
                    courseCode = "COMP393-02",
                    instructor = "Braught"
                )
            ),
            transcript = listOf(
                Course(
                    name = "Some Class pt. 1",
                    courseCode = "COMP190",
                    instructor = "Braught"
                ),
                Course(
                    name = "Some Class pt. 2",
                    courseCode = "COMP290",
                    instructor = "Goble"
                )
            ),
        )
    }
}