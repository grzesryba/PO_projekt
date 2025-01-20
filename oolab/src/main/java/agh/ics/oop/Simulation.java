package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation implements Runnable {
    private final List<AbstractAnimal> animals;
    private final WorldMap map;
    private final int sexRequiredEnergy;
    private final int reproduceRequiredEnergy;
    private final int minMutationNo;
    private final int maxMutationNo;
    private final int startEnergy;
    private final int plusGrass;
    private final int extraEnergy;
    private final int extraEnergyBigGrass;
    private final AnimalType animalType;
    private final Object lock = new Object();
    private volatile boolean isPaused = false;
    private int delay = 500;
    private volatile boolean active = true;

    public Simulation(WorldMap map, int animalGenLength, int animalNo, int startEnergy, int sexRequiredEnergy, int reproduceRequiredEnergy, int minMutationNo, int maxMutationNo, int plusGrass, int extraEnergy, int extraEnergyBigGrass, AnimalType animalType) {
        this.animals = new ArrayList<>();

        this.sexRequiredEnergy = sexRequiredEnergy;
        this.reproduceRequiredEnergy = reproduceRequiredEnergy;
        this.minMutationNo = minMutationNo;
        this.maxMutationNo = maxMutationNo;
        this.startEnergy = startEnergy;
        this.plusGrass = plusGrass;
        this.extraEnergy = extraEnergy;
        this.extraEnergyBigGrass = extraEnergyBigGrass;
        this.animalType = animalType;

        Random rand = new Random();
        for (int i = 0; i < animalNo; i++) {
            int x = rand.nextInt(map.getWidth());
            int y = rand.nextInt(map.getHeight());
            Vector2d v = new Vector2d(x, y);
            AbstractAnimal animal = AnimalFactory.createAnimal(animalType, v, MapDirection.values()[rand.nextInt(MapDirection.values().length)], animalGenLength, startEnergy);

//            zamknięty cykl - dla normalnego zwierzaka po przejściu tych ruchów powinien być(i jest) znowu w tym samym miejscu
//                              dla crazy animal kończy za każdym razem w innym
//            AbstractAnimal animal = AnimalFactory.createAnimal(AnimalType.NORMAL, positions.get(i), MapDirection.NORTH, List.of(0,2,4,0,6,7,5), startEnergy);

            try {
                if (map.place(animal)) {
                    animals.add(animal);
                }
            } catch (IncorrectPositionException e) {
                System.out.println("Warning! " + e.getMessage());
            }
        }
        this.map = map;
    }

    public void run() {
        map.updateStats();
        System.out.println(map.getStatistics());
        int lastIdx = 0;

        for (int i = 0; ; ) { // Pętla symulacji
            if (!active) {
                break;
            }
            synchronized (lock) {
                while (isPaused) {
                    if(!active){
                        break;
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            // Główna logika symulacji
            map.getStatistics().addLine(i);
            map.deleteDeathAnimals(i);
            map.moveAllAnimals();
            map.plantEating(extraEnergy, extraEnergyBigGrass);
            map.reproduce(sexRequiredEnergy, reproduceRequiredEnergy, minMutationNo, maxMutationNo);
            map.addPlant(plusGrass);
            map.alertListeners("Map changed");
            map.updateStats();
            System.out.println(map.getStatistics());
            lastIdx += 1;

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            i += 1;
        }

        map.getStatistics().addLine(lastIdx);
        map.getStatistics().writeToCsv();
    }

    public void PauseSimulation() {
        if (!isPaused) {
            isPaused = true;
            System.out.println("Simulation paused.");
        }
    }

    public void ResumeSimulation() {
        if (isPaused) {
            isPaused = false;
            synchronized (lock) {
                lock.notify();
            }
            System.out.println("Simulation resumed.");
        }
    }

    public void setSpeed(int speed) {
        delay = speed;
    }

    public List<AbstractAnimal> getAnimals() {
        return animals;
    }

    public void close() {
        synchronized (lock) {
            active = false;
            lock.notify();
        }
    }
}
