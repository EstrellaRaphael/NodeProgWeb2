import TeamRepository from "../repositories/TeamRepository.js";
import PokemonRepository from "../repositories/PokemonRepository.js";

class TeamController {
    async index(req, res) {
        try {
            const result = await TeamRepository.findAll();
            res.status(200).json(result);
        } catch (erro) {
            res.status(500).json({ erro: erro.message });
        }
    }

    async store(req, res) {
        try {
            let { pokemon_id, nickname } = req.body;

            const totalMembers = await TeamRepository.countMembers();
            if (totalMembers >= 6) {
                return res.status(403).json({ message: "Seu time já está cheio! O limite máximo é de 6 Pokémon." });
            }

            const pokemonExists = await PokemonRepository.findById(pokemon_id);
            if (pokemonExists.length === 0) {
                return res.status(404).json({ message: "Pokémon inválido ou não encontrado na Pokédex." });
            }

            if (!nickname) {
                nickname = pokemonExists[0].pokemon;
            }

            const result = await TeamRepository.create({ pokemon_id, nickname });
            res.status(201).json({ message: "Pokémon adicionado ao time com sucesso!", result });
        } catch (erro) {
            res.status(500).json({ erro: erro.message });
        }
    }

    async update(req, res) {
        try {
            const id = req.params.id;
            const { nickname } = req.body;

            if (!nickname) {
                return res.status(400).json({ message: "Por favor, forneça um novo nickname." });
            }

            const result = await TeamRepository.update(id, { nickname });
            if (result.affectedRows > 0) {
                res.status(200).json({ message: "Apelido atualizado com sucesso!" });
            } else {
                res.status(404).json({ message: "Integrante do time não encontrado." });
            }
        } catch (erro) {
            res.status(500).json({ erro: erro.message });
        }
    }

    async delete(req, res) {
        try {
            const id = req.params.id;
            const result = await TeamRepository.delete(id);
            if (result.affectedRows > 0) {
                res.status(200).json({ message: "Pokémon removido do time (solto)." });
            } else {
                res.status(404).json({ message: "Integrante do time não encontrado." });
            }
        } catch (erro) {
            res.status(500).json({ erro: erro.message });
        }
    }
}

export default new TeamController();
