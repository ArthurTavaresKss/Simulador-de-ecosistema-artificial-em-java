package world.creatures;

public class FoodVisionResult {
    private final boolean foundFood;
    private final int foodX;
    private final int foodY;

    public FoodVisionResult(boolean foundFood, int foodX, int foodY) {
        this.foundFood = foundFood;
        this.foodX = foodX;
        this.foodY = foodY;
    }

    public boolean hasFoundFood() {
        return foundFood;
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }
}
