package com.example.apipractice.data

import androidx.compose.ui.util.packInts
import com.example.apipractice.network.RetrofitInstance
import com.google.gson.Gson

class PlayerRepository {
    private val apiKey:String = "7fd6ce12-a9f6-4c0f-ae06-5968e98377e7"
    private val gson = Gson()

    suspend fun searchAllPlayers(query: String): List<Player> {
        val rawJson = RetrofitInstance.api.getPlayers(
            apiKey = apiKey,
            search = query,
            perPage = 20
        )

        val response = gson.fromJson(rawJson, PlayersResponse::class.java)
        return response.data
    }

    suspend fun searchPlayer(id: Int): Player {
        val rawJson = RetrofitInstance.api.getPlayer(
            apiKey = apiKey,
            playerId = id
        )

        val response = gson.fromJson(rawJson, PlayerResponse::class.java)
        return response.data
    }
}