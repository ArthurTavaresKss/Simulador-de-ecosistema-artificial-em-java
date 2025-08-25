package world.food;

import world.CellType;
import world.WorldGrid;
import world.clock.TickListener;
import world.configurations.Configurations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodGenerator implements TickListener {

    private final WorldGrid worldGrid;
    private final List<FoodData> spawnedFood = new ArrayList<>();
    private static final int FOOD_LIFETIME = Configurations.FOOD_LIFETIME;

    public FoodGenerator(WorldGrid worldGrid) {
        this.worldGrid = worldGrid;
        worldGrid.tick.addTickListener(this);
    }

    @Override
    public void onTick(int tickCount) {
        if (tickCount % 2 == 0) {
            generateFood(1, tickCount);
            generateFood(1, tickCount);
            generateFood(1, tickCount);
            generateFood(1, tickCount);
            generateFood(1, tickCount);
            generateFood(1, tickCount);
            generateFood(1, tickCount);
            generateFood(1, tickCount);
            generateFood(1, tickCount);
            generateFood(1, tickCount);
        }

        // Verifica comidas expiradas
        removeExpiredFood(tickCount);
    }

    // Gera comida em posições aleatórias
    public void generateFood(int number, int tickCount) {
        for (int i = 0; i < number; i++) {
            int x = (int) (Math.random() * worldGrid.getWorldSize());
            int y = (int) (Math.random() * worldGrid.getWorldSize());

            // Só gera comida em células vazias
            if (worldGrid.getCell(x, y) == CellType.EMPTY) {
                worldGrid.setCell(x, y, CellType.FOOD);
                spawnedFood.add(new FoodData(x, y, tickCount));
            } else {
                number += 1;
            }
        }
    }

    public void removeFood(int x, int y) {
        worldGrid.setCell(x, y, CellType.EMPTY);
    }

    private void removeExpiredFood(int tickCount) {
        Iterator<FoodData> iterator = spawnedFood.iterator();

        while (iterator.hasNext()) {
            FoodData food = iterator.next();
            if (tickCount - food.spawnTick >= FOOD_LIFETIME) {
                removeFood(food.x, food.y);
                iterator.remove();
            }
        }
    }
}
