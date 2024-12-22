package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "N";
            case EAST -> "E";
            case WEST -> "W";
            case SOUTH -> "S";
            case NORTHEAST -> "NE";
            case SOUTHEAST -> "SE";
            case NORTHWEST -> "NW";
            case SOUTHWEST -> "SW";
        };
    }

    public MapDirection next() {
        return switch (this) {
            case NORTH -> NORTHEAST;
            case EAST -> SOUTHEAST;
            case WEST -> NORTHWEST;
            case SOUTH -> SOUTHWEST;
            case NORTHEAST -> EAST;
            case SOUTHEAST -> SOUTH;
            case NORTHWEST -> NORTH;
            case SOUTHWEST -> WEST;
        };
    }

    public MapDirection previous() {
        return switch (this) {
            case NORTHEAST -> NORTH;
            case SOUTHEAST -> EAST;
            case NORTHWEST -> WEST;
            case SOUTHWEST -> SOUTH;
            case EAST -> NORTHEAST;
            case SOUTH -> SOUTHEAST;
            case NORTH -> NORTHWEST;
            case WEST -> SOUTHWEST;
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case EAST -> new Vector2d(1, 0);
            case WEST -> new Vector2d(-1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1,1);
            case SOUTHEAST -> new Vector2d(1,-1);
            case SOUTHWEST -> new Vector2d(-1,-1);
            case NORTHWEST -> new Vector2d(-1,1);
        };
    }

    public MapDirection rotate(int angle) {
        MapDirection[] values = values();
        return values[(this.ordinal() + angle) % values.length];
    }

}

