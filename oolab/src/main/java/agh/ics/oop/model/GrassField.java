package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final int n;
    private final Map<Vector2d, Grass> grass = new HashMap<>();

    public GrassField(int n) {
        this.n = n;

//        lab 5 - niedeterministyczna złożoność losowania
//        randomGenerateFromLab5(n);

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator((int)(Math.sqrt(n *10)), (int)(Math.sqrt(n *10)), n);
        Iterator<Vector2d> positionsIterator = randomPositionGenerator.iterator();

//        while(positionsIterator.hasNext()) {
//            Vector2d grassPosition = positionsIterator.next();
//            grass.put(grassPosition, new Grass(grassPosition));
//            updateCorners(grassPosition);
//        }

        RandomPositionGenerator randomPositionGenerator2 = new RandomPositionGenerator((int)(Math.sqrt(n *10)), (int)(Math.sqrt(n *10)), n);
        for(Vector2d grassPosition2 : randomPositionGenerator2) {
            grass.put(grassPosition2, new Grass(grassPosition2));
            updateCorners(grassPosition2);
        }

    }

    private void randomGenerateFromLab5(int n) {
        int cnt = 0;
        List<Integer> used = new ArrayList<>();
        while (cnt < n) {
            int x = (int)(Math.random()*Math.sqrt(n *10));
            int y = (int)(Math.random()*Math.sqrt(n *10));
            Vector2d v = new Vector2d(x,y);
            if (used.contains(x*10+y)){
                continue;
            } else{
                used.add(x*10+y);
                grass.put(v, new Grass(v));
                minX = Math.min(minX, x);
                minY = Math.min(minY, y);
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
                cnt += 1;
            }
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null || grass.get(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement elem = super.objectAt(position);
        if (elem == null){
            return grass.get(position);
        } else{
            return elem;
        }
    }

    @Override
    public List<WorldElement> getElements(){
        List<WorldElement> elements = super.getElements();
        elements.addAll(grass.values());
        return elements;
    }
}
