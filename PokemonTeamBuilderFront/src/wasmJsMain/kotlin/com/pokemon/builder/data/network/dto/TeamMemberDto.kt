package com.pokemon.builder.data.network.dto

import com.pokemon.builder.domain.TeamMember
import kotlinx.serialization.Serializable

@Serializable
data class TeamMemberDto(
    val id: Int,
    val pokemon_id: Int,
    val nickname: String? = null,
    val species: String,
    val dexNumber: String
)

fun TeamMemberDto.toDomain(): TeamMember {
    return TeamMember(
        id = id,
        pokemonId = pokemon_id,
        nickname = nickname,
        species = species,
        dexNumber = dexNumber
    )
}
