package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap{
    private final int height;
    private final int width;
    private final Vector2d rightTop;
    private final Vector2d leftBottom;

    public RectangularMap(int height, int width) {
        this.height = height;
        this.width = width;
        minX = 0;
        minY = 0;
        maxX = width - 1;
        maxY = height - 1;
        this.rightTop = new Vector2d(width - 1, height - 1);
        this.leftBottom = new Vector2d(0, 0);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return leftBottom.precedes(position) && rightTop.follows(position) && super.canMoveTo(position);
    }

    public Vector2d getRightTop() {
        return rightTop;
    }

    public Vector2d getLeftBottom() {
        return leftBottom;
    }

}
