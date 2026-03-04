import conexao from "../database/index.js";

class PokemonRepository {
    findAll() {
        const sql = "SELECT * FROM pokemon;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado);
            });
        });
    }

    findById(id) {
        const sql = "SELECT * FROM pokemon WHERE id=?;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, id, (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado);
            });
        });
    }
}

export default new PokemonRepository();