package world.creatures.creature2.ai;

import world.CellType;
import world.WorldGrid;
import world.configurations.Configurations;
import world.creatures.FoodVisionResult;

public class Creature2Vision {

    private final int creatureEyesDistance = Configurations.CREATURE2_SIGHT_DISTANCE;
    private final WorldGrid worldGrid;

    public Creature2Vision(WorldGrid worldGrid) {
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

                if (worldGrid.getCell(x, y) == CellType.CREATURE1) {
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
}
