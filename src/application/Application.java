package application;

import world.clock.TickListener;
import world.configurations.Configurations;
import world.creatures.creature1.Creature1;
import world.creatures.creature2.Creature2;
import world.food.FoodGenerator;
import world.WorldGrid;
import world.clock.Tick;

public class Application{

    static WorldGrid world = new WorldGrid(Configurations.WORLD_SIZE);

    public static void main(String[] args) throws InterruptedException {
        world.setVisible(true);
        world.generateObstacles(100);

        FoodGenerator foodGenerator = new FoodGenerator(world);

        Creature1 creature1Parent = new Creature1(world, 0, 0);
        creature1Parent.setCreatureToWorld();
        Creature1 creature1Parent2 = new Creature1(world, 0, 0);
        creature1Parent2.setCreatureToWorld();
        Creature1 creature1Parent3 = new Creature1(world, 0, 0);
        creature1Parent3.setCreatureToWorld();
        Creature1 creature1Parent4 = new Creature1(world, 0, 0);
        creature1Parent4.setCreatureToWorld();

    }
}