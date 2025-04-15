package edu.upenn.cit594.processor;

import edu.upenn.cit594.logging.Logger;

public class Processor {
    private Logger logger;

    //need to add the lists to store and manipulate this information later
    private List<CovidData> covidData;
    private List<Population> populationList;
    private List<Properties> propertiesList;


    /**
     * Constuctor for the processor that needs the logger + all the data-lists.
     * @param logger
     */
    public Processor(Logger logger, List<CovidData> covidData,List<Population> populationList,  List<Properties> propertiesList) {
        this.logger = logger;
        this.covidData = covidData;
        this.populationList = populationList;
        this.propertiesList = propertiesList;
    }

    /**
     * Computes total populations
     * @param population
     * @param zipCode
     * @return
     */
    public static int calculateTotalPopulation(int population, String zipCode) {
        int totalPopulation = 0;
        for (Population instance : populationList) {
            double newPopulationToAdd = instance.getPopulation(); // Need to check the getPopulations field.
            totalPopulation += newPopulationToAdd;
        }
        return totalPopulation;
    }

    /**
     *
     * @param partialVaccination
     * @param fullVaccination
     * @param zipCode
     * @param timeStamp
     * @return
     *
     * I think we need to check this one. We may not need Timestamp at all
     */
    public static int calculateVaccinationsPerCapita(int partialVaccination, int fullVaccination, String zipCode, String timeStamp) {
int population = fetchPopulationsUsingZipCode);
if (population == 0) {
    return 0;
}
double totalVaccines = partialVaccination + fullVaccination;

double finalValue = totalVaccines / population;

return (int) finalValue;

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

    public static int fetchPopulationsUsingZipCode( String zipCode) {
        if (populationList == null) {
            return 0;
        }

        for (Population person : populationList) {
            if (person.getZipCode().equals(zipCode)) {
                return person.getPopulation();
            }
        }
        return 0;
    }

}

