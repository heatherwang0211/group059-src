package edu.upenn.cit594.util;

/**
 * Properties object class including data about
 */
public class CovidData {
    // constructor
    private int zipCode;
    private double negativeCases;
    private double positiveCases;
    private double deathsCount;
    private double hospitalizationsCount;
    private double partialVaxCount;
    private double fullyVaxCount;
    private double boosterCount;
    private String timeStamp;


    // getters and setters
    public CovidData(int zipCode, double negativeCases, double positiveCases, double deathsCount, double hospitalizationsCount,
                     double partialVaxCount, double fullyVaxCount, double boosterCount, String timeStamp) {

        this.zipCode = zipCode;
        this.negativeCases = negativeCases;
        this.positiveCases = positiveCases;
        this.deathsCount = deathsCount;
        this.hospitalizationsCount = hospitalizationsCount;
        this.partialVaxCount = partialVaxCount;
        this.fullyVaxCount = fullyVaxCount;
        this.boosterCount = boosterCount;
        this.timeStamp = timeStamp;

    }

    public int zipCode() {
        return zipCode;
    }

    public double negativeCases() {
        return negativeCases;
    }

    public double positiveCases() {
        return positiveCases;
    }

    public double deathsCount() {
        return deathsCount;
    }

    public double hospitalizationsCount() {
        return hospitalizationsCount;
    }

    public double partialVaxCount() {
        return partialVaxCount;
    }

    public double fullyVaxCount() {
        return fullyVaxCount;
    }

    public double boosterCount() {
        return boosterCount;
    }

    public String timeStamp() {
        return timeStamp;
    }

}