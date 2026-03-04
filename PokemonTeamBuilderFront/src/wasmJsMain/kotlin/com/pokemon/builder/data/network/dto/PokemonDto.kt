package com.pokemon.builder.data.network.dto

import com.pokemon.builder.domain.Pokemon
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val id: Int,
    val dexNumber: String,
    val pokemon: String,
    val type: String
)

fun PokemonDto.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        dexNumber = dexNumber,
        name = pokemon,
        type = type
    )
}
