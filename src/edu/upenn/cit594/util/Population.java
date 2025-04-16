package edu.upenn.cit594.util;

public class Population {
    private int zipCode;
    private double population;

    public Population(int zipCode, double population) {
        this.zipCode = zipCode;
        this.population = population;
    }

    // getters and setters

    public int getZipCode() {
        return zipCode;
    }

    public double getPopulation() {
        return population;
    }

}