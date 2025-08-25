package world.creatures.creature2;

import world.CellType;
import world.WorldGrid;
import world.clock.TickListener;
import world.configurations.Configurations;
import world.creatures.creature1.Creature1;
import world.creatures.creature2.ai.Creature2Movement;

public class Creature2 implements TickListener {

    public int x;
    public int y;
    private final WorldGrid worldGrid;
    private int lifetime = Configurations.CREATURE2_LIFETIME;
    private int survivalTime = Configurations.CREATURE2_SURVIVALTIME;
    private final Creature2Movement creature2Movement;
    private int timesEaten = 0;

    public Creature2(WorldGrid worldGrid, int x, int y) {
        this.x = x;
        this.y = y;
        this.worldGrid = worldGrid;
        this.creature2Movement =  new Creature2Movement(worldGrid);
        worldGrid.tick.addTickListener(this);
    }

    public void setCreatureToWorld() {
        int[] xy = worldGrid.getRandomPosEmpty();
        this.x = xy[0];
        this.y = xy[1];
        worldGrid.setCell(xy[0], xy[1], CellType.CREATURE2);
        worldGrid.creaturesDataStore.addToCreature2List(this);
    }

    private void setChildToWorld() {

    }

    public void destroyCreature() {
        worldGrid.setCell(x, y, CellType.EMPTY);
        worldGrid.tick.removeTickListener(this);
        worldGrid.creaturesDataStore.removeOfCreature2List(this);
    }

    public void giveBirth() {
        int[] pos = randomPosAround(x, y);
        Creature2 child = new Creature2(worldGrid, pos[0], pos[1]);
        worldGrid.setCell(pos[0], pos[1], CellType.CREATURE1);
        worldGrid.creaturesDataStore.addToCreature2List(child);
    }

    private int[] randomPosAround(int cx, int cy) {
        int startX = cx - 1;
        int startY = cy - 1;
        int endX = cx + 1;
        int endY = cy + 1;
        // Tenta encontrar uma posição vazia ao redor
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (!worldGrid.isInsideBounds(x, y)) continue;
                if (worldGrid.getCell(x, y) == CellType.EMPTY) {
                    return new int[]{x, y}; // retorna imediatamente a primeira posição vazia encontrada
                }
            }
        }
        // Se não encontrar, retorna a posição da mãe
        return new int[]{cx, cy};
    }

    public void setXandY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void onTick(int tickCount) {
        if ((lifetime > 0) || survivalTime > 0) {
            if (tickCount % 2 == 0) {
                survivalTime -= 1;
                lifetime -=1;
                Creature2MoveData result = creature2Movement.moveCreature(x, y, this);
                worldGrid.setCell(x, y, CellType.EMPTY);
                worldGrid.setCell(result.newX, result.newY, CellType.CREATURE2);
                this.x = result.newX;
                this.y = result.newY;

                if (result.ateFood) {
                    survivalTime = Configurations.CREATURE2_SURVIVALTIME;
                    timesEaten++;
                    Creature1 creatureEaten = worldGrid.creaturesDataStore.findFirstCreature1WithXandY(x, y);
                    if (creatureEaten != null) {
                        creatureEaten.destroyCreature();
                    }

                    if (timesEaten % Configurations.CREATURE2_TIMES_EATEN_TO_REPRODUCE == 0) {
                        giveBirth();
                    }
                }
            }
        } else {
            destroyCreature();
        }
    }
}
