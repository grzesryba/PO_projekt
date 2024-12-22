package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

public class GoodHarvestMap extends AbstractWorldMap {
    private final Map<Vector2d, BigGrassPiece> occupiedPositionsByBigGrass = new HashMap<>();
    private final List<BigGrass> bigGrasses = new ArrayList<>();
    private boolean[][] bigGrassArea;
    private int areaMinX;
    private int areaMaxX;
    private int areaMinY;
    private int areaMaxY;


    public GoodHarvestMap(int width, int height, int grassNo) {
        this.width = width;
        this.height = height;
        this.maxX = width - 1;
        this.maxY = height - 1;
        this.minX = 0;
        this.minY = 0;
        this.probability = new boolean[height][width];

        chooseBigGrassArea();
        int normalGrassNo = 0;
        boolean flag = true;
        Random random = new Random();
        for (int i = 0; i < grassNo; i++) {
            if (flag && random.nextInt(2) == 0) {
                if (!addBigPlant()) {
                    flag = false;
                    normalGrassNo += 1;
                }
            } else {
                normalGrassNo += 1;
            }
        }
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, normalGrassNo, probability, occupiedPositionsByBigGrass.keySet());
        this.extraProbabilityGrassFreePositions = randomPositionGenerator.getExtraGrassFreePositions();
        this.normalProbabilityGrassFreePositions = randomPositionGenerator.getNormalGrassFreePositions();
        for (Vector2d grassPosition : randomPositionGenerator) {
            this.grasses.put(grassPosition, new Grass(grassPosition));
        }

        List<Vector2d> toRemove = new ArrayList<>();
        for (Vector2d v : occupiedPositionsByBigGrass.keySet()) {
            for (Vector2d position : normalProbabilityGrassFreePositions) {
                if (position.equals(v)) {
                    toRemove.add(position);
                }
            }
        }
        for (Vector2d v : toRemove) {
            normalProbabilityGrassFreePositions.remove(v);
        }

    }

    private boolean addBigPlant() {
        List<Vector2d> possibleBottomLeftCorners = new ArrayList<>();
        for (int i = areaMinY; i < areaMaxY; i++) {
            for (int j = areaMinX; j < areaMaxX; j++) {
                // sprawdzamy lewy górny róg i czy można to rozszerzyc na cały kwadrat
                if (occupiedPositionsByBigGrass.get(new Vector2d(j % width, i)) == null &&
                        occupiedPositionsByBigGrass.get(new Vector2d((j + 1) % width, i)) == null &&
                        occupiedPositionsByBigGrass.get(new Vector2d(j % width, i + 1)) == null &&
                        occupiedPositionsByBigGrass.get(new Vector2d((j + 1) % width, i + 1)) == null) {

                    possibleBottomLeftCorners.add(new Vector2d(j % width, i + 1));
                }
            }
        }
        if (possibleBottomLeftCorners.isEmpty()) {
            return false;
        } else {
            // Można to zrobić tworząc samo BigGrass i potem pobierając z listy kawałki i dodawać do opbbg
            Random random = new Random();
            Vector2d v = possibleBottomLeftCorners.get(random.nextInt(possibleBottomLeftCorners.size()));
            BigGrass grass = new BigGrass(v);
            occupiedPositionsByBigGrass.put(v, new BigGrassPiece(v, grass));
            Vector2d v1 = new Vector2d((v.getX() + 1) % width, v.getY());
            occupiedPositionsByBigGrass.put(v1, new BigGrassPiece(v1, grass));
            Vector2d v2 = new Vector2d((v.getX() + 1) % width, v.getY() - 1);
            occupiedPositionsByBigGrass.put(v2, new BigGrassPiece(v2, grass));
            Vector2d v3 = new Vector2d(v.getX(), v.getY() - 1);
            occupiedPositionsByBigGrass.put(v3, new BigGrassPiece(v3, grass));
            bigGrasses.add(grass);
            removePositionsForNormalGrass(v, v1, v2, v3);

            return true;
        }
    }

    private void removePositionsForNormalGrass(Vector2d v, Vector2d v1, Vector2d v2, Vector2d v3) {
        List<Vector2d> toRemove = new ArrayList<>();
        for (Vector2d vector2d : normalProbabilityGrassFreePositions) {
            if (vector2d.equals(v) || vector2d.equals(v1) || vector2d.equals(v2) || vector2d.equals(v3)) {
                toRemove.add(vector2d);
            }
        }
        for (Vector2d vector2d : toRemove) {
            normalProbabilityGrassFreePositions.remove(vector2d);
            grasses.remove(vector2d);
        }
        List<Vector2d> toRemove2 = new ArrayList<>();
        for (Vector2d vector2d : grasses.keySet()) {
            if (vector2d.equals(v) || vector2d.equals(v1) || vector2d.equals(v2) || vector2d.equals(v3)) {
                toRemove2.add(vector2d);
            }
        }
        for (Vector2d vector2d : toRemove2) {
            grasses.remove(vector2d);
        }

    }

    private void chooseBigGrassArea() {
        boolean[][] area = new boolean[height][width];
        int a = Math.round((float) Math.sqrt((float) (width * height) / 5));
        Random rand = new Random();
        int x = rand.nextInt(width);
        int y = rand.nextInt(height - a);
        areaMinX = x;
        areaMaxX = x + a - 1;
        areaMinY = y;
        areaMaxY = y + a - 1;

        for (int i = x; i < x + a; i++) {
            for (int j = y; j < y + a; j++) {
                area[j][i % width] = true;
            }
        }
        bigGrassArea = area;

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        boolean occupied = super.isOccupied(position);
        BigGrassPiece bigGrassPiece = occupiedPositionsByBigGrass.get(position);
        if (bigGrassPiece == null && !occupied) {
            return false;
        }
        return true;
    }

    @Override
    public List<WorldElement> objectsAt(Vector2d position) {
        List<WorldElement> worldElements = super.objectsAt(position);
        if (worldElements != null) {
            if (occupiedPositionsByBigGrass.get(position) != null) {
                List<WorldElement> result = new ArrayList<>();
                result.add(occupiedPositionsByBigGrass.get(position));
                result.addAll(worldElements);
                return result;
            }
            return worldElements;
        } else {
            return occupiedPositionsByBigGrass.get(position) != null ? List.of(occupiedPositionsByBigGrass.get(position)) : null;
        }
    }

    @Override
    public void plantEating(int extraEnergy, int extraEnergyBigGrass) {
        super.plantEating(extraEnergy, extraEnergyBigGrass);

        Set<BigGrass> toRemove = new HashSet<>();
        int cnt = 0;
        for (BigGrass grass : bigGrasses){
            List<AbstractAnimal> animalsAtGrassPositions = new ArrayList<>();
            for (BigGrassPiece piece : grass.getPieces()) {
                if(animals.get(piece.getPosition()) != null){
                    animalsAtGrassPositions.addAll(animals.get(piece.getPosition()));
                }
            }
            if (!animalsAtGrassPositions.isEmpty()) {
                List<AbstractAnimal> abstractAnimals = sortAnimals(animalsAtGrassPositions);
                AbstractAnimal firstAnimal = abstractAnimals.getFirst();
                firstAnimal.energy = firstAnimal.energy + extraEnergyBigGrass;
                toRemove.add(grass);
                cnt += 1;
            }
        }

        for (BigGrass grass : toRemove) {
            for (BigGrassPiece piece : grass.getPieces()) {
                occupiedPositionsByBigGrass.remove(piece.getPosition());
                normalProbabilityGrassFreePositions.add(piece.getPosition());
            }
            bigGrasses.remove(grass);
        }
        if (cnt>0){
//            alertListeners(cnt + " big grass eaten");
        }
    }

    @Override
    public void addPlant(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            if(random.nextInt(10) == 0){
                if(addBigPlant()){
                    return;
                }
            }
            super.addPlant(1);
        }
    }
}
