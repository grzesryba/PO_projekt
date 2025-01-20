package agh.ics.oop.model.Statistics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationStatistics {
    private final int id = this.hashCode();
    private int animalNo;
    private int plantNo;
    private int freePlacesNo;
    private int mostCommonGen;
    private int avgEnergy;
    private int deathAnimalAvgLifetime;
    private int avgChildNo;
    private final List<String> fileLines = new ArrayList<>();

    public int getAnimalNo() {
        return animalNo;
    }

    public int getPlantNo() {
        return plantNo;
    }

    public int getFreePlacesNo() {
        return freePlacesNo;
    }

    public int getMostCommonGen() {
        return mostCommonGen;
    }

    public int getAvgEnergy() {
        return avgEnergy;
    }

    public int getDeathAnimalAvgLifetime() {
        return deathAnimalAvgLifetime;
    }

    public int getAvgChildNo() {
        return avgChildNo;
    }

    public void setStats(int animalNo, int plantNo, int freePlacesNo, int mostCommonGen, int avgEnergy, int deathAnimalAvgLifetime, int avgChildNo) {
        this.animalNo = animalNo;
        this.plantNo = plantNo;
        this.freePlacesNo = freePlacesNo;
        this.mostCommonGen = mostCommonGen;
        this.avgEnergy = avgEnergy;
        this.deathAnimalAvgLifetime = deathAnimalAvgLifetime;
        this.avgChildNo = avgChildNo;
    }

    public void addLine(int day) {
        String line = String.format("%d,%d,%d,%d,%d,%d,%d,%d\n",day,animalNo,plantNo,freePlacesNo,mostCommonGen,avgEnergy,deathAnimalAvgLifetime,avgChildNo);
        fileLines.add(line);
    }

    public void writeToCsv(){
        String dir = "./Stats/";
        String filename = dir + "stats " + id + ".csv";
        try (FileWriter writer = new FileWriter(filename)){
            writer.append("Day,AnimalNo,PlantNo,FreePlacesNo,MostPopularGen,AvgEnergy,DeathAnimalAvgLifetime,AvgChildNo\n");
            for (String line : fileLines) {
                writer.append(line);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return
                "Liczba zwierząt: " + animalNo + "\n" +
                "Liczba roślin: " + plantNo + "\n" +
                "Liczba wolnych miejsc: " + freePlacesNo + "\n" +
                "Najczęstszy gen: " + mostCommonGen + "\n" +
                "Średnia energia: " + avgEnergy + "\n" +
                "Średnia długość życia martwych zwierząt: " + deathAnimalAvgLifetime + "\n" +
                "Średnia liczba dzieci: " + avgChildNo;
    }

}
