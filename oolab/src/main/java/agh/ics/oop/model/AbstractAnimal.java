package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAnimal implements WorldElement {
    protected MapDirection direction;
    protected Vector2d position;
    protected final List<Integer> genList = new ArrayList<>();
    protected int currentGenIdx = 0;
    protected int energy;
    protected int age = 0;
    protected int childNo = 0;
    protected AnimalType animalType;

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getChildNo() {
        return childNo;
    }

    @Override
    public String toString() {
        return this.direction.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void changeDirection(int rotate) {
        this.direction = this.direction.rotate(rotate);
    }

    public void move(WorldMap validator) {
//        System.out.println(genList.get(currentGenIdx));
        changeDirection(genList.get(currentGenIdx));
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
        currentGenIdx = (currentGenIdx + 1) % genList.size();
        energy -= 1;
        age += 1;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public List<Integer> getGenList() {
        return genList;
    }
}
