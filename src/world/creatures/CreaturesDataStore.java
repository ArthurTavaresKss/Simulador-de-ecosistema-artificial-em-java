package world.creatures;

import world.WorldGrid;
import world.clock.TickListener;
import world.creatures.creature1.Creature1;
import world.creatures.creature2.Creature2;

import java.util.ArrayList;
import java.util.List;

public class CreaturesDataStore implements TickListener {

    private WorldGrid worldGrid;
    private List<Creature1> creature1List = new ArrayList<>();
    private List<Creature2> creature2List = new ArrayList<>();

    public CreaturesDataStore(WorldGrid worldGrid) {
        this.worldGrid = worldGrid;
        worldGrid.tick.addTickListener(this);
    }

    public void addToCreature1List(Creature1 creature1) {
        creature1List.add(creature1);
    }

    public void removeOfCreature1List(Creature1 creature1) {
        creature1List.remove(creature1);
    }

    public void addToCreature2List(Creature2 creature2) {
        creature2List.add(creature2);
    }

    public void removeOfCreature2List(Creature2 creature2) {
        creature2List.remove(creature2);
    }

    public Creature1 findFirstCreature1WithXandY(int x, int y) {
        return creature1List.stream()
                .filter(c -> c.x == x && c.y == y)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void onTick(int tickCount) {
        if (tickCount == 1000) {
            Creature2 creature2Parent = new Creature2(worldGrid, 0, 0);
            creature2Parent.setCreatureToWorld();
        }
    }
}
