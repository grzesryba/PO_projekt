package agh.ics.oop.model.Map;

import agh.ics.oop.model.Animals.AbstractAnimal;
import agh.ics.oop.model.Others.Boundary;
import agh.ics.oop.model.Others.IncorrectPositionException;
import agh.ics.oop.model.Others.MoveValidator;
import agh.ics.oop.model.Others.Vector2d;
import agh.ics.oop.model.Statistics.SimulationStatistics;

import java.util.List;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    /**
     * Place a animal on the map.
     *
     * @param animal The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the move is not valid.
     */
    boolean place(AbstractAnimal animal) throws IncorrectPositionException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(AbstractAnimal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    List<WorldElement> objectsAt(Vector2d position);

    List<WorldElement> getElements();

    Boundary getCurrentBounds();

    int getId();

    int getWidth();

    int getHeight();

    void deleteAnimal(AbstractAnimal animal);

    void plantEating(int extraEnergy, int extraEnergyBigGrass);

    void addPlant(int n);

    void reproduce(int sexRequiredEnergy, int reproduceRequiredEnergy, int minMutation, int maxMutation);

    void moveAllAnimals();

    void deleteDeathAnimals(int deathDay);

    void alertListeners(String message);

    void updateStats();

    SimulationStatistics getStatistics();

    boolean[][] getProbabilityMap();

}