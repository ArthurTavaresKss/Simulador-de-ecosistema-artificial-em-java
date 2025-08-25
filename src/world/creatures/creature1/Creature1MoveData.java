package world.creatures.creature1;

public class Creature1MoveData {
    public Creature1 creature;
    public int newX;
    public int newY;
    public boolean ateFood;

    public Creature1MoveData(int newX, int newY, boolean ateFood, Creature1 creature) {
        this.creature = creature;
        this.newX = newX;
        this.newY = newY;
        this.ateFood = ateFood;
    }
}