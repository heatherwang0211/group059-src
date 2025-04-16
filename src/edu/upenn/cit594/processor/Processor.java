package edu.upenn.cit594.processor;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Properties;

import java.util.List;

public class Processor {
    private Logger logger;

    //need to add the lists to store and manipulate this information later
    private List<CovidData> covidData;
    //HW: hesitating about whether this should be static, as I was getting warnings on fetchPopulationsUsingZipCode
    // method for using instance vs. static
    private static List<Population> populationList;
    private List<Properties> propertiesList;
// added comment

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
    public static int calculateTotalPopulation(int population, int zipCode) {
        int totalPopulation = 0;
        for (Population instance : populationList) {
            double newPopulationToAdd = instance.getPopulation(); // Need to check the getPopulations field.
            totalPopulation += newPopulationToAdd;
        }
        return totalPopulation;
    }

    /**
     * this method calculates the vaccination rate per capita and the vaccination input can either be partial or full
     * vaccination data, which is inputted by the user
     * @param vaccination
     * @param zipCode
     * @param timeStamp
     * @return
     *
     * I think we need to check this one. We may not need Timestamp at all
     * HW: In the requirement it asks the user to input a YYYYMMDD to search for the date so we need it
     * HW: i removed partial and full to just have 1 vaccination input, bc it is a choice made by the user
     */
    public static int calculateVaccinationsPerCapita(int vaccination, int zipCode, String timeStamp) {
    int population = fetchPopulationsUsingZipCode(zipCode);
    if (population == 0) {
        return 0;
    }
    double totalVaccines = vaccination;
    // HW: we need something else here that uses timeStamp to look for the vax rate on that specific date

    double finalValue = totalVaccines / population;

    return (int) finalValue;

    }

    /**
     *
     * @param marketValue
     * @param zipCode
     * @return
     */
    public static int calculateAverageMarketValue(int marketValue, int zipCode) {
        // use the two inputs and count number of entries for properties to use as average

        return 0;
    }

    /**
     *
     * @param livableArea
     * @param zipCode
     * @return
     */
    public static int calculateAverageLivableArea(int livableArea, int zipCode) {
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
    public static int calculateMarketValuePerCapita(int population, int marketValue, int zipCode) {
        return 0;
    }

    /**
     * this method calculates the ratio of death rate to avg market value of properties in a ZIP code
     * death rate = death from covid data / population data
     * divided by average market value = total market value in zip code/ number of houses
     * @return death to income ratio
     */
    public static int calculateDeathToPropertyValueRatio(int population, int deathCount, int zipCode, int propertyValue) {
        return 0;
    }

    //HW: I am not too sure if we should use static or instances here
    // we are processing different data one by one which feels like instance
    // but that also feels like creating a lot of inefficiencies
    public static int fetchPopulationsUsingZipCode(int zipCode) {
        if (populationList == null) {
            return 0;
        }

        for (Population person : populationList) {
            if (person.getZipCode() == zipCode) {
                return person.getPopulation();
            }
        }
        return 0;
    }

}

