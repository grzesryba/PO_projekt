package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final int id = this.hashCode();
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected int minX = Integer.MAX_VALUE;
    protected int minY = Integer.MAX_VALUE;
    protected int maxX = 0;
    protected int maxY = 0;
    protected List<MapChangeListener> listeners = new ArrayList<>();

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        MapDirection oldDirection = animal.getDirection();
        animal.move(direction, this);
        Vector2d newPosition = animal.getPosition();
        animals.remove(oldPosition);
        updateCorners(newPosition);
        animals.put(newPosition, animal);
        alertListeners("Animal changed position from " + oldPosition + "," + oldDirection + " to " + newPosition + "," + animal.getDirection());
    }

    public void updateCorners(Vector2d newPosition) {
        minX = Math.min(minX, newPosition.getX());
        minY = Math.min(minY, newPosition.getY());
        maxX = Math.max(maxX, newPosition.getX());
        maxY = Math.max(maxY, newPosition.getY());
    }

    @Override
    public boolean place(Animal animal) throws IncorrectPositionException {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            animals.put(position, animal);
            updateCorners(position);
            alertListeners("Animal placed at: " + position);
            return true;
        }
        throw new IncorrectPositionException(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        if (animals.get(position) == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        Boundary bounds = getCurrentBounds();
        return mapVisualizer.draw(bounds.leftBottom(), bounds.rightTop());
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

    public Boundary getCurrentBounds() {
        return new Boundary(new Vector2d(minX, minY), new Vector2d(maxX, maxY));
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void addListener(MapChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MapChangeListener listener) {
        listeners.remove(listener);
    }

    public void alertListeners(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }

}
