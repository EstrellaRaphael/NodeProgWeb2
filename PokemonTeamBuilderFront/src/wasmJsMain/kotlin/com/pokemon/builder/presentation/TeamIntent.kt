package com.pokemon.builder.presentation

sealed class TeamIntent {
    object LoadData : TeamIntent()
    data class AddPokemon(val pokemonId: Int, val nickname: String?) : TeamIntent()
    data class ReleasePokemon(val teamMemberId: Int) : TeamIntent()
}
