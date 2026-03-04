package com.pokemon.builder.presentation

import com.pokemon.builder.domain.Pokemon
import com.pokemon.builder.domain.TeamMember

data class TeamState(
    val isLoading: Boolean = false,
    val team: List<TeamMember> = emptyList(),
    val availablePokemon: List<Pokemon> = emptyList(),
    val error: String? = null
)
