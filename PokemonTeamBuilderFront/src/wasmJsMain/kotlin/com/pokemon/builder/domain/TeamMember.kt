package com.pokemon.builder.domain

data class TeamMember(
    val id: Int,
    val pokemonId: Int,
    val nickname: String?,
    val species: String,
    val dexNumber: String
)
