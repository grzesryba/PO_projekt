package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAnimal implements WorldElement {

    protected AnimalStatistics animalStats;

    public void move(WorldMap validator) {
//        System.out.println(genList.get(currentGenIdx));
        animalStats.changeDirection(animalStats.getGenList().get(animalStats.getCurrentGenIdx()));
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
        animalStats.setCurrentGenIdx((animalStats.getCurrentGenIdx() + 1) % animalStats.getGenList().size());
        animalStats.setEnergy(animalStats.getEnergy() - 1);
        animalStats.aging();
    }

    public boolean isAlive() {
        return animalStats.getEnergy() > 0;
    }

    public Vector2d getPosition() {
        return animalStats.getPosition();
    }

    public AnimalStatistics getAnimalStats() {
        return animalStats;
    }

    @Override
    public String toString() {
        return this.getAnimalStats().getDirection().toString();
    }
}
