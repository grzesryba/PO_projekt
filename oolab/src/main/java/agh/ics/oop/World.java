package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {


//        settings:
        int width = 10;
        int height = 10;
        int grassNo = 8;

//        WARIANT MAPY (WZROSTU ROŚLIN)
        AbstractWorldMap map = new SimpleWorldMap(width,height,grassNo);
//        AbstractWorldMap map = new GoodHarvestMap(width, height, grassNo);

        int extraEnergy = 1;
        int extraEnergyBigGrass = 6;
        int plusGrass = 3;
        int animalNo = 2;
        int animalStartEnergy = 10;
        int sexRequiredEnergy = 4;
        int reproduceRequiredEnergy = 2;
        int minMutationNo = 0;
        int maxMutationNo = 7;
        int genLength = 5;

//        AnimalType type = AnimalType.NORMAL;
        AnimalType type = AnimalType.CRAZY;

        ConsoleMapDisplay listener = new ConsoleMapDisplay();
        map.addListener(listener);

        Simulation simulation = new Simulation(
                map,
                genLength,
                animalNo,
                animalStartEnergy,
                sexRequiredEnergy,
                reproduceRequiredEnergy,
                minMutationNo,
                maxMutationNo,
                plusGrass,
                extraEnergy,
                extraEnergyBigGrass,
                type);

        simulation.run();

    }
}
