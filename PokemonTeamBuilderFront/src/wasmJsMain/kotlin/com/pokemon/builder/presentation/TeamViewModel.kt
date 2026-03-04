package com.pokemon.builder.presentation

import com.pokemon.builder.domain.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamViewModel(
    private val repository: PokemonRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val _state = MutableStateFlow(TeamState())
    val state: StateFlow<TeamState> = _state.asStateFlow()

    init {
        handleIntent(TeamIntent.LoadData)
    }

    fun handleIntent(intent: TeamIntent) {
        when (intent) {
            is TeamIntent.LoadData -> loadInitialData()
            is TeamIntent.AddPokemon -> addPokemon(intent.pokemonId, intent.nickname)
            is TeamIntent.ReleasePokemon -> releasePokemon(intent.teamMemberId)
        }
    }

    private fun loadInitialData() {
        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val pokemons = repository.getAllPokemon()
                val team = repository.getTeam()
                _state.update {
                    it.copy(
                        isLoading = false,
                        availablePokemon = pokemons,
                        team = team
                    )
                }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        error = "Erro ao conectar com a API. Verifique se o backend está rodando em http://localhost:3000. Detalhe: ${e.message}" 
                    ) 
                }
            }
        }
    }

    private fun addPokemon(pokemonId: Int, nickname: String?) {
        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val success = repository.addPokemonToTeam(pokemonId, nickname)
            if (success) {
                // Reload team
                handleIntent(TeamIntent.LoadData)
            } else {
                _state.update { it.copy(isLoading = false, error = "Não foi possível adicionar o Pokémon ou o time já está cheio (limite de 6).") }
            }
        }
    }

    private fun releasePokemon(teamMemberId: Int) {
        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val success = repository.releasePokemonFromTeam(teamMemberId)
            if (success) {
                // Reload team
                handleIntent(TeamIntent.LoadData)
            } else {
                _state.update { it.copy(isLoading = false, error = "Não foi possível soltar o Pokémon.") }
            }
        }
    }
}
