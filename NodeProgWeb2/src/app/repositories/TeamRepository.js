import conexao from "../database/index.js";

class TeamRepository {
    findAll() {
        const sql = `
            SELECT t.id, t.pokemon_id, t.nickname, p.pokemon as species, p.dexNumber
            FROM team t
            INNER JOIN pokemon p ON t.pokemon_id = p.id;
        `;
        return new Promise((resolve, reject) => {
            conexao.query(sql, (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado);
            });
        });
    }

    countMembers() {
        const sql = "SELECT COUNT(*) as total FROM team;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado[0].total);
            });
        });
    }

    create(member) {
        const sql = "INSERT INTO team SET ?;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, member, (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado);
            });
        });
    }

    update(id, member) {
        const sql = "UPDATE team SET ? WHERE id=?;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, [member, id], (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado);
            });
        });
    }

    delete(id) {
        const sql = "DELETE FROM team WHERE id=?;";
        return new Promise((resolve, reject) => {
            conexao.query(sql, id, (erro, resultado) => {
                if (erro) return reject(erro);
                return resolve(resultado);
            });
        });
    }
}

export default new TeamRepository();
