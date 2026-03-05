package com.goble.myquizapp.data

data class Question(
    val category: Category,
    val question:String,
    val options:List<String>,
    val answer:Int
)