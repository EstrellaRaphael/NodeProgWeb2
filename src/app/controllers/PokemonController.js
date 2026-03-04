import PokemonRepository from "../repositories/PokemonRepository.js";

class PokemonController {
    async index(req, res) {
        try {
            const result = await PokemonRepository.findAll();
            res.status(200).json(result);
        } catch (erro) {
            res.status(404).json({ erro });
        }
    }

    async show(req, res) {
        try {
            const id = req.params.id;
            const result = await PokemonRepository.findById(id);
            if (result.length > 0) {
                res.status(200).json(result[0]);
            } else {
                res.status(404).json({ erro: "Pokemon não encontrado" });
            }
        } catch (erro) {
            res.status(404).json({ erro });
        }
    }

    async store(req, res) {
        try {
            const pokemon = req.body;
            const result = await PokemonRepository.create(pokemon);
            res.status(201).json(result);
        } catch (erro) {
            res.status(404).json({ erro });
        }
    }

    async update(req, res) {
        try {
            const id = req.params.id;
            const pokemon = req.body;
            const result = await PokemonRepository.update(pokemon, id);
            res.status(200).json(result);
        } catch (erro) {
            res.status(404).json({ erro });
        }
    }

    async delete(req, res) {
        try {
            const id = req.params.id;
            const result = await PokemonRepository.delete(id);
            res.status(200).json(result);
        } catch (erro) {
            res.status(404).json({ erro });
        }
    }
}

export default new PokemonController();