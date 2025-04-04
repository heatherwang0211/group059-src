package edu.upenn.cit594.processor;

import edu.upenn.cit594.logging.Logger;

public class Processor {
    private Logger logger;

    public Processor(Logger logger) {
        this.logger = logger;
    }

    /**
     *
     * @param population
     * @param zipCode
     * @return
     */
    public static int calculateTotalPopulation(int population, String zipCode) {
        return population;
    }

    /**
     *
     * @param partialVaccination
     * @param fullVaccination
     * @param zipCode
     * @param timeStamp
     * @return
     */
    public static int calculateVaccinationsPerCapita(int partialVaccination, int fullVaccination, String zipCode, String timeStamp) {
        return 0;
    }

    /**
     *
     * @param marketValue
     * @param zipCode
     * @return
     */
    public static int calculateAverageMarketValue(int marketValue, String zipCode) {
        // use the two inputs and count number of entries for properties to use as average
        return 0;
    }

    /**
     *
     * @param livableArea
     * @param zipCode
     * @return
     */
    public static int calculateAverageLivableArea(int livableArea, String zipCode) {
        // use the two inputs and count number of entries for properties to use as average
        return 0;
    }

    /**
     *
     * @param population
     * @param marketValue
     * @param zipCode
     * @return
     */
    public static int calculateMarketValuePerCapita(int population, int marketValue, String zipCode) {
        return 0;
    }

    /**
     *
     * @return
     */
    public static int calculateCustomFeature() {
        return 0;
    }
}

