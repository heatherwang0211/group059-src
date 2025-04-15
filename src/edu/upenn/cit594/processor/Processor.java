package edu.upenn.cit594.processor;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Properties;
import edu.upenn.cit594.util.Population;

import java.util.List;


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
        int population = fetchPopulationsUsingZipCode(zipCode);
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
     * calculate the correlation between property value and full vax rate
     * this can be indicative of access & willingness for vax and socioeconomic conditions
     * positive = higher socioeconomic bracket -> higher vax rate, negative = higher socioeconomic bracket -> lower vax rate
     * @return correlation between full vax rate and property value
     */
    public static int calculateVaccinationPropertyValueCorrelation(int vaccinationRate, int propertyValue) {
        // get vax rate per capita and the avg market value for the geo
        calculateVaccinationsPerCapita();
        calculateAverageMarketValue();

        // filter for the geos that have FULL data for market value and vax rate

        // calculate correlation
        double correlation = correlationCalculator(vaccinationRate, propertyValue);

        // print out result interpretation, for what the correlation ends up being
        String interpretation;
        if (correlation > 0.7) {
            interpretation = "Strong positive correlation: Areas with higher property values have significantly higher vaccination rates";
        } else if (correlation > 0.3) {
            interpretation = "Somewhat positive correlation: Areas with higher property values have somewhat higher vaccination rates";
        } else if (correlation > -0.3) {
            interpretation = "Weak or no correlation: Property values and vaccination rates don't appear related";
        } else if (correlation > -0.7) {
            interpretation = "Somewhat negative correlation: Areas with higher property values tend to have somewhat lower vaccination rates";
        } else {
            interpretation = "Strong negative correlation: Areas with higher property values tend to have significantly lower vaccination rates";
        }
        return 0;
    }

    /**
     * this is a helper method for calculateVaccinationPropertyValueCorrelation method above
     * @param vaccinationRate the vax rate to be used in the calculation
     * @param propertyValue the avg market value to be used in calculation
     * @return the correlation between the two
     */
    private static double correlationCalculator(double vaccinationRate, double propertyValue) {
        return 0;
    }

    /**
     *
     * @param zipCode
     * @return
     */
    public static int fetchPopulationsUsingZipCode(String zipCode) {
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

