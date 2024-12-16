package agh.ics.oop.model;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;

    public Animal() {
        this(new Vector2d(2, 2));

    }

    public Animal(Vector2d position) {
        this.position = position;
        this.direction = MapDirection.NORTH;
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

    public void move(MoveDirection direction, MoveValidator validator) {

        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                Vector2d move = this.direction.toUnitVector();
                if (validator.canMoveTo(this.position.add(move))) {
                    this.position = this.position.add(move);
                }
            }
            case BACKWARD -> {
                Vector2d move = this.direction.toUnitVector();
                if (validator.canMoveTo(this.position.subtract(move))) {
                    this.position = this.position.subtract(move);
                }
            }
        }
    }
}
