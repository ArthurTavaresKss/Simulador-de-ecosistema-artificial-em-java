package world.creatures.creature1.ai;

import world.WorldGrid;
import world.CellType;
import world.configurations.Configurations;
import world.creatures.EnemyVisionResult;
import world.creatures.FoodVisionResult;

public class Creature1Vision {

    private final int creatureEyesDistance = Configurations.CREATURE1_SIGHT_DISTANCE;
    private final WorldGrid worldGrid;

    public Creature1Vision(WorldGrid worldGrid) {
        this.worldGrid = worldGrid;
    }

    public FoodVisionResult lookForFood(int creatureX, int creatureY) {
        boolean foundFood = false;
        int foodX = -1;
        int foodY = -1;

        int startX = creatureX - creatureEyesDistance;
        int startY = creatureY - creatureEyesDistance;
        int endX = creatureX + creatureEyesDistance;
        int endY = creatureY + creatureEyesDistance;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {

                if (!worldGrid.isInsideBounds(x, y)) continue;

                if (worldGrid.getCell(x, y) == CellType.FOOD) {
                    foundFood = true;
                    foodX = x;
                    foodY = y;
                    break;
                }
            }
            if (foundFood) break;
        }
        return new FoodVisionResult(foundFood, foodX, foodY);
    }

    public EnemyVisionResult lookForEnemy(int creatureX, int creatureY) {
        boolean foundEnemy = false;
        int enemyX = -1;
        int enemyY = -1;

        int startX = creatureX - creatureEyesDistance;
        int startY = creatureY - creatureEyesDistance;
        int endX = creatureX + creatureEyesDistance;
        int endY = creatureY + creatureEyesDistance;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {

                if (!worldGrid.isInsideBounds(x, y)) continue;

                if (worldGrid.getCell(x, y) == CellType.CREATURE2) {
                    foundEnemy = true;
                    enemyX = x;
                    enemyY = y;
                    break;
                }
            }
            if (foundEnemy) break;
        }
        return new EnemyVisionResult(foundEnemy, enemyX, enemyY);
    }

}
