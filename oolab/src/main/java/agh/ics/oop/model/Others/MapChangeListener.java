package agh.ics.oop.model.Others;

import agh.ics.oop.model.Map.WorldMap;

public interface MapChangeListener {
    void mapChanged(WorldMap worldMap, String message);
}
