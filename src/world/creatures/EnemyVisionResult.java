package world.creatures;

public class EnemyVisionResult {
    private final boolean foundEnemy;
    private final int enemyX;
    private final int enemyY;

    public EnemyVisionResult(boolean foundEnemy, int enemyX, int enemyY) {
        this.foundEnemy = foundEnemy;
        this.enemyX = enemyX;
        this.enemyY = enemyY;
    }

    public boolean hasFoundEnemy() {
        return foundEnemy;
    }

    public int getEnemyX() {
        return enemyX;
    }

    public int getEnemyY() {
        return enemyY;
    }
}
