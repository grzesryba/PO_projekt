package agh.ics.oop.model.Animals;

import agh.ics.oop.model.Others.MapDirection;
import agh.ics.oop.model.Statistics.AnimalStatistics;
import agh.ics.oop.model.Others.Vector2d;
import agh.ics.oop.model.Map.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrazyAnimal extends AbstractAnimal {
    public CrazyAnimal(Vector2d position, MapDirection direction, int genLength, int energy) {
        Random rand = new Random();
        List<Integer> genList = new ArrayList<>();
        for (int i = 0; i < genLength; i++) {
            genList.add(rand.nextInt(8));
        }
        animalStats = new AnimalStatistics(direction,position,energy, AnimalType.CRAZY,genList);
    }

    public CrazyAnimal(Vector2d position, MapDirection direction, List<Integer> genList, int energy) {
        animalStats = new AnimalStatistics(direction,position,energy,AnimalType.CRAZY,genList);
    }

    @Override
    public void move(WorldMap validator) {
        Random rand = new Random();
        int x = rand.nextInt(5);
        if (x == 0) {
            Integer rotate = animalStats.getGenList().get(rand.nextInt(animalStats.getGenList().size()));
//            System.out.println(rotate);
            animalStats.changeDirection(rotate);
            Vector2d newPosition = animalStats.getPosition().add(animalStats.getDirection().toUnitVector());
            if (validator.canMoveTo(newPosition)) {
                if (newPosition.getX() > validator.getCurrentBounds().rightTop().getX()) {
                    newPosition = new Vector2d(0, newPosition.getY());
                } else if (newPosition.getX() < validator.getCurrentBounds().leftBottom().getX()) {
                    newPosition = new Vector2d(validator.getCurrentBounds().rightTop().getY(), newPosition.getY());
                }
                animalStats.setPosition(newPosition);
            } else {
                animalStats.changeDirection(4);
            }
            animalStats.setEnergy(animalStats.getEnergy() - 1);
            animalStats.aging();
        } else {
            super.move(validator);
        }
    }
}
