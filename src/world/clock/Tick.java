package world.clock;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import world.WorldGrid;

public class Tick {
    private final int tickInterval; // intervalo em ms
    private Timer timer;
    private  final List<TickListener> listeners = new ArrayList<>();
    public int ticksRun = 0;

    public Tick(int tickInterval) {
        this.tickInterval = tickInterval;
    }

    // Inicia o sistema de ticks
    public void startTick(WorldGrid world) {
        timer = new Timer(tickInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticksRun++;
                notifyListeners();
            }
        });
        timer.start();
    }

    // Para os ticks (se precisar pausar o jogo)
    public void stopTick() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    public void addTickListener(TickListener tickListener) {
        listeners.add(tickListener);
    }

    public void removeTickListener(TickListener tickListener) {
        listeners.remove(tickListener);
    }

    private void notifyListeners() {
        for (TickListener listener : new ArrayList<>(listeners)) {
            listener.onTick(ticksRun);
        }
    }

}
