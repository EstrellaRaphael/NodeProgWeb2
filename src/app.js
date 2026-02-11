import express from "express";

const selecoes = [
    { id: 1, selecao: "Brasil", grupo: "G" },
    { id: 2, selecao: "Suíça", grupo: "G" },
    { id: 3, selecao: "Camarões", grupo: "G" },
    { id: 4, selecao: "Sérvia", grupo: "G" }
];

function buscaIndexSelecao(id) {
    return selecoes.findIndex(selecao => selecao.id == id);
}

function buscaSelecao(id) {
    return selecoes.find(selecao => selecao.id == id);
}

const app = express();

app.use(express.json());

app.get("/", (req, res) => {
    res.send("Hello World!");
});

app.get("/selecoes", (req, res) => {
    res.status(200).send(selecoes);
});

app.post("/selecoes", (req, res) => {
    selecoes.push(req.body);
    res.status(201).send("Seleção cadastrada com sucesso!");
});

app.get("/selecoes/:id", (req, res) => {
    res.json(buscaSelecao(req.params.id))
});

app.delete("/selecoes/:id", (req, res) => {
    let index = buscaIndexSelecao(req.params.id)
    selecoes.splice(index, 1);
    res.status(200).send("Seleção excluída com sucesso!");
});

app.put("/selecoes/:id", (req, res) => {
    let index = buscaIndexSelecao(req.params.id)
    selecoes[index] = req.body;
    res.status(200).send("Seleção atualizada com sucesso!");
});

export default app;
