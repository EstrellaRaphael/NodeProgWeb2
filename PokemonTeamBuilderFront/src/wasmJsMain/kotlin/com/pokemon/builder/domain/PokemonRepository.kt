package com.pokemon.builder.domain

interface PokemonRepository {
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun getTeam(): List<TeamMember>
    suspend fun addPokemonToTeam(pokemonId: Int, nickname: String?): Boolean
    suspend fun releasePokemonFromTeam(id: Int): Boolean
}
