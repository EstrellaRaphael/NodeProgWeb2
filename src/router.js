import { Router } from "express";
import PokemonController from "./app/controllers/PokemonController.js";

const router = Router();

router.get("/pokemon", PokemonController.index);
router.get("/pokemon/:id", PokemonController.show);
router.post("/pokemon", PokemonController.store);
router.put("/pokemon/:id", PokemonController.update);
router.delete("/pokemon/:id", PokemonController.delete);

export default router;
