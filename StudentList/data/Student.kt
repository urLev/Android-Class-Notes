package com.goble.studentlist.data

data class Student(
    val name:String,
    val studentId: String,
    val pronouns: String,
    val gradYear:Int,
    val currentClasses: List<Course>,
    val transcript:List<Course>
)
