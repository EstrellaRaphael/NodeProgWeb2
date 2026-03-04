package com.pokemon.builder.data.repository

import com.pokemon.builder.data.network.dto.PokemonDto
import com.pokemon.builder.data.network.dto.TeamMemberDto
import com.pokemon.builder.data.network.dto.toDomain
import com.pokemon.builder.data.network.ktorClient
import com.pokemon.builder.domain.Pokemon
import com.pokemon.builder.domain.PokemonRepository
import com.pokemon.builder.domain.TeamMember
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PokemonRepositoryImpl : PokemonRepository {
    private val baseUrl = "http://localhost:3000"

    override suspend fun getAllPokemon(): List<Pokemon> {
        val response: List<PokemonDto> = ktorClient.get("$baseUrl/pokemon").body()
        return response.map { it.toDomain() }
    }

    override suspend fun getTeam(): List<TeamMember> {
        val response: List<TeamMemberDto> = ktorClient.get("$baseUrl/team").body()
        return response.map { it.toDomain() }
    }

    override suspend fun addPokemonToTeam(pokemonId: Int, nickname: String?): Boolean {
        val response = ktorClient.post("$baseUrl/team") {
            contentType(ContentType.Application.Json)
            setBody(mapOf(
                "pokemon_id" to pokemonId.toString(),
                "nickname" to (nickname ?: "")
            ))
        }
        return response.status.value in 200..299
    }

    override suspend fun releasePokemonFromTeam(id: Int): Boolean {
        val response = ktorClient.delete("$baseUrl/team/$id")
        return response.status.value in 200..299
    }
}
