CREATE DATABASE IF NOT EXISTS dbpokemon;
USE dbpokemon;

CREATE TABLE IF NOT EXISTS pokemon (
    id INT PRIMARY KEY AUTO_INCREMENT,
    dexNumber VARCHAR(3) NOT NULL,
    pokemon VARCHAR(100) NOT NULL
);

INSERT INTO pokemon (id, dexNumber, pokemon) VALUES
(1, '004', 'Charmander'),
(2, '005', 'Charmeleon'),
(3, '006', 'Charizard'),
(4, '037', 'Vulpix'),
(5, '038', 'Ninetales'),
(6, '058', 'Growlithe'),
(7, '059', 'Arcanine'),
(8, '077', 'Ponyta'),
(9, '078', 'Rapidash'),
(10, '126', 'Magmar'),
(11, '136', 'Flareon'),
(12, '146', 'Moltres');
