import express from "express";

const pokemonFogo = [
    { id: 1, dexNumber: "004", pokemon: "Charmander" },
    { id: 2, dexNumber: "005", pokemon: "Charmeleon" },
    { id: 3, dexNumber: "006", pokemon: "Charizard" },
    { id: 4, dexNumber: "037", pokemon: "Vulpix" },
    { id: 5, dexNumber: "038", pokemon: "Ninetales" },
    { id: 6, dexNumber: "058", pokemon: "Growlithe" },
    { id: 7, dexNumber: "059", pokemon: "Arcanine" },
    { id: 8, dexNumber: "077", pokemon: "Ponyta" },
    { id: 9, dexNumber: "078", pokemon: "Rapidash" },
    { id: 10, dexNumber: "126", pokemon: "Magmar" },
    { id: 11, dexNumber: "136", pokemon: "Flareon" },
    { id: 12, dexNumber: "146", pokemon: "Moltres" }
];

function buscaIndexPokemon(id) {
    return pokemonFogo.findIndex(pokemon => pokemon.id == id);
}

function buscaPokemon(id) {
    return pokemonFogo.find(pokemon => pokemon.id == id);
}

const app = express();

app.use(express.json());

app.get("/", (req, res) => {
    res.send("Bem-vindo à API de Pokémon de Fogo!");
});

app.get("/pokemon", (req, res) => {
    res.status(200).send(pokemonFogo);
});

app.post("/pokemon", (req, res) => {
    pokemonFogo.push(req.body);
    res.status(201).send("Pokémon cadastrado com sucesso!");
});

app.get("/pokemon/:id", (req, res) => {
    const pokemon = buscaPokemon(req.params.id);
    if (pokemon) {
        res.status(200).json(pokemon);
    } else {
        res.status(404).send("Pokémon não encontrado.");
    }
});

app.delete("/pokemon/:id", (req, res) => {
    let index = buscaIndexPokemon(req.params.id);
    if (index !== -1) {
        pokemonFogo.splice(index, 1);
        res.status(200).send("Pokémon excluído com sucesso!");
    } else {
        res.status(404).send("Pokémon não encontrado.");
    }
});

app.put("/pokemon/:id", (req, res) => {
    let index = buscaIndexPokemon(req.params.id);
    if (index !== -1) {
        pokemonFogo[index] = req.body;
        res.status(200).send("Pokémon atualizado com sucesso!");
    } else {
        res.status(404).send("Pokémon não encontrado.");
    }
});

export default app;
