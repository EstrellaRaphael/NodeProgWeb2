import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pokemon.builder.data.repository.PokemonRepositoryImpl
import com.pokemon.builder.domain.Pokemon
import com.pokemon.builder.domain.TeamMember
import com.pokemon.builder.presentation.TeamIntent
import com.pokemon.builder.presentation.TeamViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun App() {
    val repository = remember { PokemonRepositoryImpl() }
    val viewModel = remember { TeamViewModel(repository) }
    val state by viewModel.state.collectAsState()

    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFFE3350D),
            primaryVariant = Color(0xFFB32308),
            secondary = Color(0xFF31A7D9)
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pokémon Team Builder") },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Coluna Esquerda: Meu Time
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Meu Time (${state.team.size}/6)",
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(state.team) { member ->
                                    TeamMemberCard(member) {
                                        viewModel.handleIntent(TeamIntent.ReleasePokemon(member.id))
                                    }
                                }
                                if (state.team.isEmpty()) {
                                    item {
                                        Text("Seu time está vazio. Adicione alguns Pokémon!", color = Color.Gray)
                                    }
                                }
                            }
                        }

                        // Coluna Direita: Pokémon Disponíveis
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Pokémon Disponíveis",
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            
                            var searchQuery by remember { mutableStateOf("") }
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                label = { Text("Buscar por nome ou tipo") },
                                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                            )

                            val filteredList = state.availablePokemon.filter {
                                it.name.contains(searchQuery, ignoreCase = true) ||
                                it.type.contains(searchQuery, ignoreCase = true)
                            }
                            
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(filteredList) { pokemon ->
                                    PokemonCard(
                                        pokemon = pokemon,
                                        canAdd = state.team.size < 6,
                                        onAdd = { nickname ->
                                            viewModel.handleIntent(TeamIntent.AddPokemon(pokemon.id, nickname))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                
                state.error?.let { errorMessage ->
                    Snackbar(
                        modifier = Modifier.padding(16.dp).align(Alignment.BottomCenter),
                        action = {
                            TextButton(onClick = { viewModel.handleIntent(TeamIntent.LoadData) }) {
                                Text("Fechar", color = MaterialTheme.colors.primary)
                            }
                        }
                    ) {
                        Text(errorMessage)
                    }
                }
            }
        }
    }
}

@Composable
fun TeamMemberCard(member: TeamMember, onRelease: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PokemonImage(
                    pokemonId = member.pokemonId,
                    modifier = Modifier.size(60.dp).padding(end = 12.dp)
                )
                Column {
                    Text(
                        text = member.nickname ?: member.species,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "#${member.dexNumber} - ${member.species}",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                }
            }
            IconButton(onClick = onRelease) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Soltar Pokémon",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun PokemonCard(pokemon: Pokemon, canAdd: Boolean, onAdd: (String?) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { if (canAdd) showDialog = true },
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PokemonImage(
                    pokemonId = pokemon.id,
                    modifier = Modifier.size(50.dp).padding(end = 12.dp)
                )
                Column {
                    Text(
                        text = pokemon.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = "#${pokemon.dexNumber} - ${pokemon.type}",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                }
            }
            if (canAdd) {
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                ) {
                    Text("Adicionar", color = Color.White)
                }
            } else {
                Text("Time Cheio", color = Color.Gray, style = MaterialTheme.typography.caption)
            }
        }
    }

    if (showDialog) {
        var nickname by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Adicionar ${pokemon.name} ao Time") },
            text = {
                Column {
                    Text("Deseja dar um apelido? (Opcional)")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        label = { Text("Apelido") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onAdd(nickname.takeIf { it.isNotBlank() })
                    showDialog = false
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun PokemonImage(pokemonId: Int, modifier: Modifier = Modifier) {
    // Usando Sprites oficiais do PokeAPI.
    // O id no banco parece bater com o id nacional da dex para a Gen 1.
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
    
    Box(
        modifier = modifier.clip(CircleShape).background(Color(0xFFF2F2F2)),
        contentAlignment = Alignment.Center
    ) {
        KamelImage(
            resource = asyncPainterResource(imageUrl),
            contentDescription = "Pokemon Sprite",
            modifier = Modifier.fillMaxSize(),
            onLoading = { progress -> CircularProgressIndicator(progress) },
            onFailure = { error -> 
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("?", fontWeight = FontWeight.Bold)
                    // Text(error.message ?: "Erro", style = MaterialTheme.typography.caption)
                }
            },
            contentScale = ContentScale.Fit
        )
    }
}
