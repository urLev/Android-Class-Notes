package com.goble.myquizapp.data

object QuestionRepository {
    private val allQuestions = listOf(
        Question(
            category = Category.COMPSCI,
            question = "What runtime complexity is fastest?",
            options = listOf("1", "n", "nlogn", "n^2"),
            answer = 0
        ),
        Question(
            category = Category.COMPSCI,
            question = "What runtime complexity is slowest?",
            options = listOf("1", "n", "nlogn", "n^2"),
            answer = 3
        ),
        Question(
            category = Category.POPCULT,
            question = "Who is Taylor Swift's fiance?",
            options = listOf("Travis Kelcee", "n", "nlogn", "n^2"),
            answer = 0
        ),
        Question(
            category = Category.POPCULT,
            question = "Which movie did Leo win his first oscar for?",
            options = listOf("1", "n", "nlogn", "The Revnant"),
            answer = 3
        ),
    )

    fun getQuestions(category: Category) : List<Question> = allQuestions.filter { it.category == category }.shuffled()

    fun getCategories(): List<Category> = Category.values().toList()
}