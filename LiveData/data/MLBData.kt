package com.example.apipractice.data

import com.google.gson.annotations.SerializedName

data class Team(
    val id: Int,
    val slug: String?,
    val abbreviation: String?,
    @SerializedName("display_name") val displayName: String?,
    val location: String?,
    val league: String?,
    val division: String?
)

data class Player(
    val id: Int,

    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("debut_year")
    val debutYear: Int?,

    val jersey: String?,
    val position: String?,
    val active: Boolean?,

    @SerializedName("birth_place")
    val birthPlace: String?,

    val dob: String?,
    val age: Int?,
    val height: String?,
    val weight: String?,
    val draft: String?,

    @SerializedName("bats_throws")
    val batsThrows: String?,
    val team: Team?
)