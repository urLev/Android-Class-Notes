package com.example.apipractice.data

// -----------------------------------------------
// Player Search (search_player_all endpoint)
// -----------------------------------------------
data class PlayerSearchRow(
    val playerId: String = "",
    val fullName:String = "",
    val teamFull:String = "",
    val teamAbbrev:String = "",
    val position:String = "",
    val bats:String = "",
    val throws:String = "",
    val activeSw:String = ""
)

data class PlayerDetail(
    val playerId: String = "",
    val fullName: String = "",
    val teamName: String = "",
    val teamAbbrev: String = "",
    val position: String = "",
    val jerseyNumber: String = "",
    val birthDate: String = "",
    val birthCity: String = "",
    val birthCountry: String = "",
    val age: String = "",
    val heightFeet: String = "",
    val heightInches: String = "",
    val weight: String = "",
    val bats: String = "",
    val throws: String = "",
    val proDebutDate: String = "",
    val status: String = "",
    val college: String = "",
    val nickname: String = "",
    val twitterId: String = ""
)