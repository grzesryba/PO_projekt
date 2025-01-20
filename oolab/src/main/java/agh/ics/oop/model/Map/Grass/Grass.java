package agh.ics.oop.model.Map.Grass;

import agh.ics.oop.model.Map.WorldElement;
import agh.ics.oop.model.Others.Vector2d;

public class Grass implements WorldElement {
    private final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
