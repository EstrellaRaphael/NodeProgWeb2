import { Router } from "express";
import PokemonController from "./app/controllers/PokemonController.js";
import TeamController from "./app/controllers/TeamController.js";

const router = Router();

router.get("/pokemon", PokemonController.index);
router.get("/pokemon/:id", PokemonController.show);

router.get("/team", TeamController.index);
router.post("/team", TeamController.store);
router.put("/team/:id", TeamController.update);
router.delete("/team/:id", TeamController.delete);

export default router;
