package edu.upenn.cit594.util;

/**
 * Properties object class including data about
 */
public class Properties {
    // constructor
    private int zipCode;
    private double livableArea;
    private double marketValue;


    // getters and setters
    public Properties(int zipCode, double livableArea, double marketValue) {
        this.zipCode = zipCode;
        this.livableArea = livableArea;
        this.marketValue = marketValue;

    }

    public int zipCode() {
        return zipCode;
    }

    public double livableArea() {
        return livableArea;
    }

    public double marketValue() {
        return marketValue;
    }

}