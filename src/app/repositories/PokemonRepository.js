import conexao from "../database/index.js";

class PokemonRepository {
    findAll() {
        const sql = "SELECT * FROM pokemon;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, (erro, resultado) => {
                if (erro) return reject(erro);
                const row = JSON.parse(JSON.stringify(resultado));
                return resolve(row);
            });
        });
    }

    findById(id) {
        const sql = "SELECT * FROM pokemon WHERE id=?;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, id, (erro, resultado) => {
                if (erro) return reject(erro);
                const row = JSON.parse(JSON.stringify(resultado));
                return resolve(row);
            });
        });
    }

    create(pokemon) {
        const sql = "INSERT INTO pokemon SET ?;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, pokemon, (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado);
            });
        });
    }

    update(pokemon, id) {
        const sql = "UPDATE pokemon SET ? WHERE id=?;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, [pokemon, id], (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado);
            });
        });
    }

    delete(id) {
        const sql = "DELETE FROM pokemon WHERE id=?;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, id, (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado);
            });
        });
    }
}

export default new PokemonRepository();