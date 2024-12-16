package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d>,Iterator<Vector2d> {
    private final List<Vector2d> positions;
    private int idx = 0;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        List<Vector2d> allPositions = new ArrayList<>();
        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxHeight; j++) {
                allPositions.add(new Vector2d(i,j));
            }
        }
        Collections.shuffle(allPositions);
        this.positions = allPositions.subList(0, grassCount);
    }

    @Override
    public Iterator<Vector2d> iterator() {
        idx = 0;
        return positions.iterator();
    }

    @Override
    public boolean hasNext() {
        return idx < positions.size();
    }

    @Override
    public Vector2d next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Brak kolejnych pozycji.");
        }
        return positions.get(idx++);
    }
}
