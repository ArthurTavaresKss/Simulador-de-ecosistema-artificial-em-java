package world.creatures.creature1.ai;

import world.CellType;
import world.WorldGrid;
import world.creatures.Direction;
import world.creatures.FoodVisionResult;
import world.creatures.creature1.Creature1;
import world.creatures.creature1.Creature1MoveData;
import world.creatures.EnemyVisionResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Creature1Movement {

    private static final Random random = new Random();
    private final WorldGrid worldGrid;
    private final Creature1Vision creature1Vision;

    public Creature1Movement(WorldGrid worldGrid) {
        this.worldGrid = worldGrid;
        this.creature1Vision = new Creature1Vision(worldGrid);
    }

    public Creature1MoveData moveCreature(int creatureX, int creatureY, Creature1 creature) {
        // Verifica se a criatura está dentro dos limites
        if (!worldGrid.isInsideBounds(creatureX, creatureY)) {
            return new Creature1MoveData(creatureX, creatureY, false, creature);
        }

        // Procura inimigos próximos primeiro (PRIORIDADE MÁXIMA)
        EnemyVisionResult enemyResult = creature1Vision.lookForEnemy(creatureX, creatureY);
        Direction chosenDirection;

        if (enemyResult.hasFoundEnemy()) {
            // Foge do inimigo
            chosenDirection = chooseEscapeDirection(creatureX, creatureY, enemyResult.getEnemyX(), enemyResult.getEnemyY());
        } else {
            // Caso não haja inimigo, procura comida
            FoodVisionResult foodResult = creature1Vision.lookForFood(creatureX, creatureY);

            if (foodResult.hasFoundFood()) {
                chosenDirection = chooseDirection(creatureX, creatureY, foodResult.getFoodX(), foodResult.getFoodY());
            } else {
                // Caso não veja comida, escolhe uma direção aleatória
                chosenDirection = Direction.values()[random.nextInt(Direction.values().length)];
            }
        }

        // Tenta se mover na direção escolhida ou encontrar outra direção livre
        int[] newPos = tryMove(creatureX, creatureY, chosenDirection);
        int newX = newPos[0];
        int newY = newPos[1];

        // Se não houver movimento válido, permanece parado
        if (newX == creatureX && newY == creatureY) {
            return new Creature1MoveData(creatureX, creatureY, false, creature);
        }

        boolean ateFood = worldGrid.getCell(newX, newY) == CellType.FOOD;
        return new Creature1MoveData(newX, newY, ateFood, creature);
    }

    /**
     * Decide para onde fugir do inimigo
     */
    private Direction chooseEscapeDirection(int creX, int creY, int enemyX, int enemyY) {
        int xDiff = enemyX - creX;
        int yDiff = enemyY - creY;

        // Se o inimigo está mais para os lados, foge para a horizontal oposta
        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            return (xDiff > 0) ? Direction.LEFT : Direction.RIGHT;
        }
        // Se o inimigo está mais para cima/baixo, foge para a vertical oposta
        else if (Math.abs(yDiff) > Math.abs(xDiff)) {
            return (yDiff > 0) ? Direction.UP : Direction.DOWN;
        }
        // Caso esteja na diagonal → escolhe aleatoriamente entre horizontal e vertical oposta
        else {
            Direction horizontal = (xDiff > 0) ? Direction.LEFT : Direction.RIGHT;
            Direction vertical = (yDiff > 0) ? Direction.UP : Direction.DOWN;
            return random.nextBoolean() ? horizontal : vertical;
        }
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
            if (cell == CellType.EMPTY || cell == CellType.FOOD) {
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
