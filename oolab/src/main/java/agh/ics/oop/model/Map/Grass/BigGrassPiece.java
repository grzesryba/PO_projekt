package agh.ics.oop.model.Map.Grass;

import agh.ics.oop.model.Map.WorldElement;
import agh.ics.oop.model.Others.Vector2d;

public class BigGrassPiece implements WorldElement {
    private final Vector2d position;
    private final BigGrass parent;

    public BigGrassPiece(Vector2d position, BigGrass parent) {
        this.position = position;
        this.parent = parent;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "$";
    }
}
