package agh.ics.oop.model;

public class IncorrectPositionException extends Exception{
    public IncorrectPositionException(Vector2d v) {
        super("Position (" + v.getX() + ", " + v.getY() + ") is not correct");
    }
}
