import app from "./src/app.js";
import conexao from "./infra/conexao.js";

const port = 3000;

conexao.connect((err) => {
    if (err) {
        console.error("Erro ao conectar ao banco de dados:", err);
        return;
    }
    console.log("Conexão com o banco de dados estabelecida com sucesso!");
    app.listen(port, () => {
        console.log(`Server rodando em http://localhost:${port}`);
    })
});

