package world.creatures.creature2;

public class Creature2MoveData {
    public Creature2 creature;
    public int newX;
    public int newY;
    public boolean ateFood;

    public Creature2MoveData(int newX, int newY, boolean ateFood, Creature2 creature) {
        this.creature = creature;
        this.newX = newX;
        this.newY = newY;
        this.ateFood = ateFood;
    }
}