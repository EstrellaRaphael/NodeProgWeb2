import PokemonRepository from "../repositories/PokemonRepository.js";

class PokemonController {
    async index(req, res) {
        try {
            const result = await PokemonRepository.findAll();
            res.status(200).json(result);
        } catch (erro) {
            res.status(500).json({ erro });
        }
    }

    async show(req, res) {
        try {
            const id = req.params.id;
            const result = await PokemonRepository.findById(id);
            if (result.length > 0) {
                res.status(200).json(result[0]);
            } else {
                res.status(404).json({ message: "Pokémon não encontrado" });
            }
        } catch (erro) {
            res.status(500).json({ erro });
        }
    }
}

export default new PokemonController();