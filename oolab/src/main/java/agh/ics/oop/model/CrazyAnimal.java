package agh.ics.oop.model;

import java.util.List;
import java.util.Random;

public class CrazyAnimal extends AbstractAnimal {
    public CrazyAnimal(Vector2d position, MapDirection direction, int genLength, int energy) {
        this.animalType = AnimalType.CRAZY;
        this.position = position;
        this.direction = direction;
        this.energy = energy;
        Random rand = new Random();
        for (int i = 0; i < genLength; i++) {
            genList.add(rand.nextInt(8));
        }
    }

    public CrazyAnimal(Vector2d position, MapDirection direction, List<Integer> genList, int energy) {
        this.animalType =AnimalType.CRAZY;
        this.position = position;
        this.direction = direction;
        this.genList.addAll(genList);
        this.energy = energy;
    }

    @Override
    public void move(WorldMap validator) {
        Random rand = new Random();
        int x = rand.nextInt(5);
        if (x == 0) {
            Integer rotate = genList.get(rand.nextInt(genList.size()));
//            System.out.println(rotate);
            changeDirection(rotate);
            Vector2d newPosition = this.position.add(this.direction.toUnitVector());
            if (validator.canMoveTo(newPosition)) {
                if (newPosition.getX() > validator.getCurrentBounds().rightTop().getX()) {
                    newPosition = new Vector2d(0, newPosition.getY());
                } else if (newPosition.getX() < validator.getCurrentBounds().leftBottom().getX()) {
                    newPosition = new Vector2d(validator.getCurrentBounds().rightTop().getY(), newPosition.getY());
                }
                this.position = newPosition;
            } else {
                changeDirection(4);
            }
            energy -= 1;
            age += 1;
        } else {
            super.move(validator);
        }
    }
}
