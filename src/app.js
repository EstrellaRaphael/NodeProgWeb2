import express from "express";

const selecoes = [
    { id: 1, selecao: "Brasil", grupo: "G" },
    { id: 2, selecao: "Suíça", grupo: "G" },
    { id: 3, selecao: "Camarões", grupo: "G" },
    { id: 4, selecao: "Sérvia", grupo: "G" }
];

const app = express();

app.get("/", (req, res) => {
    res.send("Hello World!");
});

app.get("/selecoes", (req, res) => {
    res.status(200).send(selecoes);
});

export default app;
