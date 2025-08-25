package world;

import java.awt.Color;

public enum CellType {
    EMPTY(Color.LIGHT_GRAY),
    FOOD(Color.GREEN),
    CREATURE1(Color.MAGENTA),
    CREATURE2(Color.RED),
    OBSTACLE(Color.DARK_GRAY);

    private final Color color;

    CellType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
