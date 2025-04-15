package edu.upenn.cit594.util;

public class Population {
    private String zipCode;
    private double population;

    public Population(String zipCode, double population) {
        this.zipCode = zipCode;
        this.population = population;
    }

    // getters and setters

    public String getZipCode() {
        return zipCode;
    }

    public double getPopulation() {
        return population;
    }

}