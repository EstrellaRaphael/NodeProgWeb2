import express from "express";
import conexao from "../infra/conexao.js";

const router = express();

function tratamento() {
    if (erro) {
        res.status(404).json({ "erro": erro });
    } else {
        res.status(200).json(resultado);
    }
}

router.use(express.json());

router.get("/", (req, res) => {
    res.send("Bem-vindo à API de Pokémon de Fogo (com MySQL)!");
});

router.get("/pokemon", (req, res) => {
    const sql = "SELECT * FROM pokemon;";
    conexao.query(sql, (erro, resultado) => {
        tratamento(erro, resultado, res);
    });
});

router.get("/pokemon/:id", (req, res) => {
    const id = req.params.id;
    const sql = "SELECT * FROM pokemon WHERE id=?;";
    conexao.query(sql, id, (erro, resultado) => {
        const linha = resultado[0];
        if (erro) {
            res.status(404).json({ "erro": erro });
        } else {
            res.status(200).json(linha);
        }
    });
});

router.post("/pokemon", (req, res) => {
    const pokemon = req.body;
    const sql = "INSERT INTO pokemon SET ?;";
    conexao.query(sql, pokemon, (erro, resultado) => {
        if (erro) {
            res.status(404).json({ "erro": erro });
        } else {
            res.status(201).json(resultado);
        }
    });
});

router.delete("/pokemon/:id", (req, res) => {
    const id = req.params.id;
    const sql = "DELETE FROM pokemon WHERE id=?;";
    conexao.query(sql, id, (erro, resultado) => {
        tratamento(erro, resultado, res);
    });
});

router.put("/pokemon/:id", (req, res) => {
    const id = req.params.id;
    const pokemon = req.body;
    const sql = "UPDATE pokemon SET ? WHERE id=?;";
    conexao.query(sql, [pokemon, id], (erro, resultado) => {
        tratamento(erro, resultado, res);
    });
});

export default router;
