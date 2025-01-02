package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class AnimalStatistics {
    private final List<Integer> genList = new ArrayList<>();
    private final AnimalType animalType;
    private MapDirection direction;
    private Vector2d position;
    private int currentGenIdx = 0;
    private int energy;
    private int age = 0;
    private int childNo = 0;
    private int eatenPlants = 0;
    private int deathDay;

    public AnimalStatistics(MapDirection direction, Vector2d position, int energy, AnimalType animalType, List<Integer> genList) {
        this.direction = direction;
        this.position = position;
        this.energy = energy;
        this.animalType = animalType;
        this.genList.addAll(genList);
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getChildNo() {
        return childNo;
    }

    public void addChild() {
        childNo += 1;
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

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void changeDirection(int rotate) {
        this.direction = this.direction.rotate(rotate);
    }

    public List<Integer> getGenList() {
        return genList;
    }

    public void aging() {
        age += 1;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public int getCurrentGenIdx() {
        return currentGenIdx;
    }

    public void setCurrentGenIdx(int currentGenIdx) {
        this.currentGenIdx = currentGenIdx;
    }

    public int getAge() {
        return age;
    }

    public int getCurrentGen() {
        return genList.get(currentGenIdx);
    }

    public void addEatenPlant() {
        eatenPlants += 1;
    }

    public int getEatenPlants() {
        return eatenPlants;
    }

    public void setDeathDay(int deathDay) {
        this.deathDay = deathDay;
    }

}
