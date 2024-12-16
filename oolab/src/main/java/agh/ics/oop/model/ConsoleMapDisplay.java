package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int n;

    public ConsoleMapDisplay() {
        this.n = 0;
    }

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        n += 1;
        System.out.println("Simulation ID: " + worldMap.getId());
        System.out.println("Update number: " + n);
        System.out.println("Message: " + message);
        System.out.println(worldMap);
    }
}