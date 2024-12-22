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

    public Simulation(WorldMap map, int animalGenLength, int animalNo, int startEnergy, int sexRequiredEnergy, int reproduceRequiredEnergy, int minMutationNo, int maxMutationNo, int plusGrass, int extraEnergy, int extraEnergyBigGrass) {
        this.animals = new ArrayList<>();

        this.sexRequiredEnergy = sexRequiredEnergy;
        this.reproduceRequiredEnergy = reproduceRequiredEnergy;
        this.minMutationNo = minMutationNo;
        this.maxMutationNo = maxMutationNo;
        this.startEnergy = startEnergy;
        this.plusGrass = plusGrass;
        this.extraEnergy = extraEnergy;
        this.extraEnergyBigGrass = extraEnergyBigGrass;

        Random rand = new Random();
        for (int i = 0; i < animalNo; i++) {
            int x = rand.nextInt(map.getWidth());
            int y = rand.nextInt(map.getHeight());
            Vector2d v = new Vector2d(x, y);
            AbstractAnimal animal = AnimalFactory.createAnimal(AnimalType.CRAZY, v, MapDirection.values()[rand.nextInt(MapDirection.values().length)], animalGenLength, startEnergy);

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

//        pętla powinna się robić w nieskończoność ale dla testów i obserwacji lepiej ustawić jakąś wartość
        for (int i = 0; i < 1000; i++) {
            map.moveAllAnimals();
            map.plantEating(extraEnergy, extraEnergyBigGrass);
            map.deleteDeathAnimals();
            map.addPlant(plusGrass);
            map.reproduce(sexRequiredEnergy, reproduceRequiredEnergy, minMutationNo, maxMutationNo);

            map.alertListeners("Map changed");
            //            System.out.println(map);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public List<AbstractAnimal> getAnimals() {
        return animals;
    }
}
