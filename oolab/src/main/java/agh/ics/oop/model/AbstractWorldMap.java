package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap {
    protected final int id = this.hashCode();
    protected List<AbstractAnimal> animalList = new ArrayList<>();
    protected Map<Vector2d, ArrayList<AbstractAnimal>> animals = new HashMap<>();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    protected Map<Vector2d, Grass> grasses = new HashMap<>();
    protected List<WorldElement> elements = new ArrayList<>();
    protected boolean[][] probability;

    protected List<Vector2d> normalProbabilityGrassFreePositions = new ArrayList<>();
    protected List<Vector2d> extraProbabilityGrassFreePositions = new ArrayList<>();


    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);

    protected int width;
    protected int height;
    protected int minX = Integer.MAX_VALUE;
    protected int minY = Integer.MAX_VALUE;
    protected int maxX = 0;
    protected int maxY = 0;
    protected List<MapChangeListener> listeners = new ArrayList<>();

    @Override
    public void move(AbstractAnimal animal) {
        Vector2d oldPosition = animal.getPosition();
        MapDirection oldDirection = animal.getDirection();
        if (animal.getEnergy() > 0) {
            animal.move(this);
        }
        Vector2d newPosition = animal.getPosition();
        animals.get(oldPosition).remove(animal);
        addAnimalToMap(animal, newPosition);
//        alertListeners("Animal changed position from " + oldPosition + "," + oldDirection + " to " + newPosition + "," + animal.getDirection());
    }

    public void updateCorners(Vector2d newPosition) {
        minX = Math.min(minX, newPosition.getX());
        minY = Math.min(minY, newPosition.getY());
        maxX = Math.max(maxX, newPosition.getX());
        maxY = Math.max(maxY, newPosition.getY());
    }

    @Override
    public boolean place(AbstractAnimal animal) throws IncorrectPositionException {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            addAnimalToMap(animal, position);
//            alertListeners("Animal placed at: " + position);
            elements.add(animal);
            animalList.add(animal);
            return true;
        }
        throw new IncorrectPositionException(position);
    }

    private void addAnimalToMap(AbstractAnimal animal, Vector2d position) {
        if (animals.get(position) == null) {
            ArrayList<AbstractAnimal> newAnimals = new ArrayList<>();
            newAnimals.add(animal);
            animals.put(position, newAnimals);
        } else {
            animals.get(position).add(animal);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if ((animals.get(position) == null || animals.get(position).isEmpty()) && grasses.get(position) == null) {
            return false;
        }
        return true;
    }

    @Override
    public List<WorldElement> objectsAt(Vector2d position) {
        List<WorldElement> elemsAtPosition = new ArrayList<>();
        if (grasses.get(position) != null) {
            elemsAtPosition.add(grasses.get(position));
        }
        List<AbstractAnimal> animalsAtPosition = animals.get(position);
        if (animalsAtPosition != null) {
            elemsAtPosition.addAll(animalsAtPosition);
        }
        if (elemsAtPosition.isEmpty()) {
            return null;
        }
        return elemsAtPosition;
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.getY() > maxY || position.getY() < minY) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        Boundary bounds = getCurrentBounds();
        return mapVisualizer.draw(bounds.leftBottom(), bounds.rightTop());
    }

    @Override
    public List<WorldElement> getElements() {
        return elements;
    }

    public Boundary getCurrentBounds() {
        return new Boundary(new Vector2d(minX, minY), new Vector2d(maxX, maxY));
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void addListener(MapChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MapChangeListener listener) {
        listeners.remove(listener);
    }

    public void alertListeners(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }


    public void plantEating(int extraEnergy, int extraEnergyBigGrass) {

        List<Vector2d> grassToRemove = new ArrayList<>();

        for (Grass grass : grasses.values()) {
            Vector2d position = grass.getPosition();
            List<AbstractAnimal> animalsAtPosition = animals.get(position);
            if (animalsAtPosition != null && !animalsAtPosition.isEmpty()) {
                List<AbstractAnimal> collect = sortAnimals(animalsAtPosition);
                AbstractAnimal first = collect.getFirst();
                first.setEnergy(first.getEnergy() + extraEnergy);
                grassToRemove.add(position);

            }
        }
        for (Vector2d position : grassToRemove) {
            grasses.remove(position);
            if (probability[position.getY()][position.getX()]) {
                extraProbabilityGrassFreePositions.add(position);
            } else {
                normalProbabilityGrassFreePositions.add(position);
            }
        }
    }

    protected static List<AbstractAnimal> sortAnimals(List<AbstractAnimal> animalsAtPosition) {
        Random random = new Random();
        return animalsAtPosition.stream()
                .sorted(Comparator.comparingInt(AbstractAnimal::getEnergy)
                        .thenComparing(AbstractAnimal::getAge)
                        .thenComparing(AbstractAnimal::getChildNo)
                        .thenComparing(v -> random.nextInt())).collect(Collectors.toList());
    }

    public void addPlant(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            if (!extraProbabilityGrassFreePositions.isEmpty() && !normalProbabilityGrassFreePositions.isEmpty()) {
                if (random.nextInt(5) != 0) {
                    addExtraProbabilityGrass();
                } else {
                    addNormalProbabilityGrass();
                }
            } else if (!extraProbabilityGrassFreePositions.isEmpty()) {
                addExtraProbabilityGrass();
            } else if (!normalProbabilityGrassFreePositions.isEmpty()) {
                addNormalProbabilityGrass();
            } else {
                return;
            }
            //alertListeners("Plant added");
        }
    }

    //Funkcja do testów
    public boolean addPlantAtPosition(Vector2d position) {
        if (!extraProbabilityGrassFreePositions.contains(position)) {
            grasses.put(position, new Grass(position));
            extraProbabilityGrassFreePositions.remove(position);
            return true;
        } else if (!normalProbabilityGrassFreePositions.contains(position)) {
            grasses.put(position, new Grass(position));
            normalProbabilityGrassFreePositions.remove(position);
            return true;
        }
        return false; // Pozycja jest już zajęta lub nie istnieje na liście wolnych miejsc
    }




    private void addNormalProbabilityGrass() {
        Random random = new Random();
        int idx = random.nextInt(normalProbabilityGrassFreePositions.size());
        Vector2d key = normalProbabilityGrassFreePositions.get(idx);
        grasses.put(key, new Grass(key));
        normalProbabilityGrassFreePositions.remove(key);
    }

    private void addExtraProbabilityGrass() {
        Random random = new Random();
        int idx = random.nextInt(extraProbabilityGrassFreePositions.size());
        Vector2d key = new Vector2d(extraProbabilityGrassFreePositions.get(idx).getX(), extraProbabilityGrassFreePositions.get(idx).getY());
        grasses.put(key, new Grass(key));
        extraProbabilityGrassFreePositions.remove(key);
    }

    public void reproduce(int sexRequiredEnergy, int reproduceRequiredEnergy, int minMutation, int maxMutation) {
        for (Vector2d position : animals.keySet()) {
            if (animals.get(position).size() >= 2) {
                List<AbstractAnimal> sortedAnimals = sortAnimals(animals.get(position));
                if (sortedAnimals.get(1).getEnergy() > sexRequiredEnergy) {
                    AbstractAnimal aAnimal = sortedAnimals.get(0);
                    AbstractAnimal bAnimal = sortedAnimals.get(1);
                    int aEnergy = aAnimal.getEnergy();
                    int bEnergy = bAnimal.getEnergy();
                    List<Integer> aGenList = aAnimal.getGenList();
                    List<Integer> bGenList = bAnimal.getGenList();
                    float percent = (float) aEnergy / (aEnergy + bEnergy);
                    int aGenNo = Math.max(Math.round(aGenList.size() * percent),1);
                    int bGenNo = bGenList.size() - aGenNo;
                    Random random = new Random();
                    List<Integer> newGenList = new ArrayList<>();

                    //losowanie strony dla "silniejszego" zwierzaka
//                    System.out.println("A: " + aGenList + " " + aEnergy + " " + aGenNo + " " + bGenList + " " + bEnergy + " " + bGenNo);
                    if (random.nextInt(2) == 0) {
                        newGenList.addAll(aGenList.subList(0, aGenNo));
                        newGenList.addAll(bGenList.subList(bGenList.size() - bGenNo , bGenList.size()));
                    } else {
                        newGenList.addAll(bGenList.subList(0, bGenNo));
                        newGenList.addAll(aGenList.subList(aGenList.size() - aGenNo , aGenList.size()));
                    }

                    //Mutacje
                    int mutationNo = random.nextInt(minMutation, maxMutation + 1);
                    for (int i = 0; i < mutationNo; i++) {
                        newGenList.set(random.nextInt(newGenList.size()), random.nextInt(8));
                    }

                    AbstractAnimal newAnimal = AnimalFactory.createAnimal(
                            aAnimal.animalType,
                            aAnimal.getPosition(),
                            MapDirection.values()[random.nextInt(8)],
                            newGenList,
                            2 * reproduceRequiredEnergy);

                    aAnimal.setEnergy(aAnimal.getEnergy() - reproduceRequiredEnergy);
                    bAnimal.setEnergy(bAnimal.getEnergy() - reproduceRequiredEnergy);

                    addAnimalToMap(newAnimal, newAnimal.getPosition());
                    animalList.add(newAnimal);
                    aAnimal.childNo += 1;
                    bAnimal.childNo += 1;
                }
            }
        }
    }

    public void moveAllAnimals() {
        for (AbstractAnimal animal : animalList) {
            this.move(animal);
        }
    }

    public void deleteDeathAnimals() {
        List<AbstractAnimal> animalsToRemove = new ArrayList<>();
        int cnt = 0;
        for (int i = 0; i < animalList.size(); i++) {
            if (animalList.get(i).getEnergy() <= 0) {
                animalsToRemove.add(animalList.get(i));
                this.deleteAnimal(animalList.get(i));
                cnt += 1;
            }
        }
        for (AbstractAnimal animal : animalsToRemove) {
            animalList.remove(animal);
        }
        if(cnt > 0) {
//            alertListeners(cnt + " Animals removed");
        }
    }


    public void deleteAnimal(AbstractAnimal animal) {
        Vector2d position = animal.getPosition();
        animals.get(position).remove(animal);
    }


}
