package world.creatures.creature2.ai;

import world.CellType;
import world.WorldGrid;
import world.creatures.Direction;
import world.creatures.FoodVisionResult;
import world.creatures.creature2.Creature2;
import world.creatures.creature2.Creature2MoveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Creature2Movement {

    private static final Random random = new Random();
    private final WorldGrid worldGrid;
    private final Creature2Vision creature2Vision;

    public Creature2Movement(WorldGrid worldGrid) {
        this.worldGrid = worldGrid;
        this.creature2Vision = new Creature2Vision(worldGrid);
    }

    public Creature2MoveData moveCreature(int creatureX, int creatureY, Creature2 creature) {
        // Verifica se a criatura está dentro dos limites
        if (!worldGrid.isInsideBounds(creatureX, creatureY)) {
            return new Creature2MoveData(creatureX, creatureY, false, creature);
        }

        // Procura comida próxima
        FoodVisionResult result = creature2Vision.lookForFood(creatureX, creatureY);

        Direction chosenDirection;

        if (result.hasFoundFood()) {
            // Vai em direção à comida
            chosenDirection = chooseDirection(creatureX, creatureY, result.getFoodX(), result.getFoodY());
        } else {
            // Caso não veja comida, escolhe uma direção aleatória
            chosenDirection = Direction.values()[random.nextInt(Direction.values().length)];
        }

        // Tenta se mover na direção escolhida ou encontrar outra direção livre
        int[] newPos = tryMove(creatureX, creatureY, chosenDirection);
        int newX = newPos[0];
        int newY = newPos[1];

        // Se não houver movimento válido, permanece parado
        if (newX == creatureX && newY == creatureY) {
            return new Creature2MoveData(creatureX, creatureY, false, creature);
        }

        boolean ateFood = worldGrid.getCell(newX, newY) == CellType.CREATURE1;
        return new Creature2MoveData(newX, newY, ateFood, creature);
    }

    /**
     * Tenta mover para a direção preferida. Se bloqueada, tenta as demais direções disponíveis.
     */
    private int[] tryMove(int x, int y, Direction preferredDirection) {
        // Lista de direções para tentar, começando pela preferida
        List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(directions);
        directions.remove(preferredDirection);
        directions.add(0, preferredDirection);

        // Testa todas as direções possíveis
        for (Direction dir : directions) {
            int[] pos = moveToDirection(x, y, dir);
            int newX = pos[0];
            int newY = pos[1];

            if (!worldGrid.isInsideBounds(newX, newY)) continue;

            CellType cell = worldGrid.getCell(newX, newY);
            if (cell == CellType.EMPTY || cell == CellType.CREATURE1 || cell == CellType.FOOD) {
                return pos; // Movimento válido
            }
        }

        // Nenhuma direção disponível → fica parado
        return new int[]{x, y};
    }

    private Direction chooseDirection(int creX, int creY, int foodX, int foodY) {
        int xDiff = foodX - creX;
        int yDiff = foodY - creY;

        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            return (xDiff > 0) ? Direction.RIGHT : Direction.LEFT;
        } else {
            return (yDiff > 0) ? Direction.DOWN : Direction.UP;
        }
    }

    private int[] moveToDirection(int x, int y, Direction direction) {
        int newX = x;
        int newY = y;

        switch (direction) {
            case UP -> newY--;
            case RIGHT -> newX++;
            case DOWN -> newY++;
            case LEFT -> newX--;
        }

        return new int[]{newX, newY};
    }
}
