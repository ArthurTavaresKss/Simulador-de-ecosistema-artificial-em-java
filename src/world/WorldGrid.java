package world;

import world.clock.Tick;
import world.configurations.Configurations;
import world.creatures.CreaturesDataStore;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class WorldGrid extends JFrame {
    private int size;
    private JPanel[][] cells;
    private CellType[][] cellStates; // <- Matriz para armazenar o estado de cada célula
    public Tick tick = new Tick(Configurations.TICK_INTERVAL);
    public CreaturesDataStore creaturesDataStore = new CreaturesDataStore(this);

    public WorldGrid(int size) {
        this.size = size;
        this.cells = new JPanel[size][size];
        this.cellStates = new CellType[size][size];

        setTitle("Simulador de Vida Artificial");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tick.startTick(this);

        // GridLayout com size x size células
        JPanel gridPanel = new JPanel(new GridLayout(size, size));
        add(gridPanel);

        // Criar células e iniciar como EMPTY
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JPanel cell = new JPanel();
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                gridPanel.add(cell);
                cells[i][j] = cell;
                setCell(i, j, CellType.EMPTY);
            }
        }
    }

    // Define o estado da célula e atualiza a cor automaticamente
    public void setCell(int x, int y, CellType type) {
        cellStates[x][y] = type;
        cells[x][y].setBackground(type.getColor());
    }

    // Retorna o estado atual de uma célula
    public CellType getCell(int x, int y) {
        return cellStates[x][y];
    }

    public int getWorldSize() {
        return size;
    }

    public void generateObstacles(int number) {
        Random random = new Random();

        for (int i = 0; i < number; i++) {
            int startX = random.nextInt(getWorldSize());
            int startY = random.nextInt(getWorldSize());

            // Tamanho aleatório da parede: 1 a 5 blocos
            int wallLength = random.nextInt(5) + 1;

            // Direção aleatória: true = horizontal, false = vertical
            boolean horizontal = random.nextBoolean();

            boolean placed = false;

            // Tenta encontrar um local válido para a parede
            for (int attempts = 0; attempts < 10 && !placed; attempts++) {
                // Verifica se cabe no mundo
                if (horizontal && startX + wallLength >= getWorldSize()) {
                    startX = random.nextInt(getWorldSize() - wallLength);
                } else if (!horizontal && startY + wallLength >= getWorldSize()) {
                    startY = random.nextInt(getWorldSize() - wallLength);
                }

                // Verifica se todas as células estão vazias
                boolean canPlace = true;
                for (int j = 0; j < wallLength; j++) {
                    int checkX = horizontal ? startX + j : startX;
                    int checkY = horizontal ? startY : startY + j;

                    if (getCell(checkX, checkY) != CellType.EMPTY) {
                        canPlace = false;
                        break;
                    }
                }

                // Se o espaço está livre, cria a parede
                if (canPlace) {
                    for (int j = 0; j < wallLength; j++) {
                        int placeX = horizontal ? startX + j : startX;
                        int placeY = horizontal ? startY : startY + j;
                        setCell(placeX, placeY, CellType.OBSTACLE);
                    }
                    placed = true;
                } else {
                    // Se não conseguiu, tenta outra posição
                    startX = random.nextInt(getWorldSize());
                    startY = random.nextInt(getWorldSize());
                }
            }

            // Se não conseguiu posicionar a parede, compensa no loop
            if (!placed) {
                i--;
            }
        }
    }


    public int[] getRandomPosEmpty() {
        int worldSize = getWorldSize();
        int maxTries = 1000; // Limite de tentativas para evitar travamento
        int x, y;

        for (int i = 0; i < maxTries; i++) {
            x = (int) (Math.random() * worldSize);
            y = (int) (Math.random() * worldSize);

            if (getCell(x, y) == CellType.EMPTY) {
                return new int[]{x, y};
            }
        }

        // Se não encontrar nenhuma célula vazia, retorna (-1, -1)
        return new int[]{-1, -1};
    }

    public boolean isInsideBounds(int x, int y) {
        int size = getWorldSize();
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}
