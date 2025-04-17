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
     * HW: In the requirement it asks the user to input a YYYYMMDD to search for the date so we need it
     * ZH Response: I added and used the timestamp field in the updated logic
     * HW: i removed partial and full to just have 1 vaccination input, bc it is a choice made by the user
     * ZH Response: noted - even if the user selects, I kept the helper method generic and passed a null so we can use it as is.
     */
    public static int calculateVaccinationsPerCapita(int vaccination, int zipCode, String timeStamp) {
        int population = fetchPopulationsUsingZipCode(zipCode);
        if (population == 0) {
            return 0;
        }

        double totalVaccines = getVaccinationCountForZipDate(
                String.valueOf(zipCode),
                timeStamp,
                null);

        return (int) (totalVaccines / population);
    }

    /**
     *
     * @param marketValue
     * @param zipCode
     * @return
     */
    public static int calculateAverageMarketValue(int marketValue, int zipCode) {
        // use the two inputs and count number of entries for properties to use as average
        // ZH: Sounds good - based on that, i came up with the logic below:
        if (propertiesList == null) {
            return 0;
        }

        int propertyCount = 0;
        String zipCodeString = String.valueOf(zipCode);

        for (Properties property : propertiesList) {
            if (zipStr.equals(property.zipCode())) {
                propertyCount++;
            }
        }

        if (propertyCount == 0) {
            return 0;
        }

        double average = (double) marketValue / propertyCount;
        return (int) average;
    }

    /**
     *
     * @param livableArea
     * @param zipCode
     * @return
     */
    public static int calculateAverageLivableArea(int livableArea, int zipCode) {
        // use the two inputs and count number of entries for properties to use as average
        // ZH: Sounds good - based on that, i came up with the logic below:

        if (propertiesList == null) {
            return 0;
        }

        int propertyCount = 0;
        String zipCodeString = String.valueOf(zipCode);

        for (Properties property : propertiesList) {
            if (zipStr.equals(property.zipCode())) {
                propertyCount++;
            }
        }

        if (propertyCount == 0) {
            return 0;
        }

        double average = (double) livableArea / propertyCount;
        return (int) average;
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

    /**
     * Returns a list of properties from a given zipCode
     * @param zipCode
     * @return
     */
    private List<Properties> getPropertiesForZip(String zipCode) {
        List<Properties> listOfProperties = new ArrayList<>();
        if (propertiesList == null) {
            return listOfProperties;
        }
        for (Properties property : propertiesList) {
            if (zipCode.equals(property.zipCode())) {
                listOfProperties.add(prop);
            }
        }
        return listOfProperties;
    }

    /**
     * Sums up all the market values of all the homes in a given zipCode
     * @param zipCode
     * @return
     */
    private double getTotalMarketValueForZip(String zipCode) {
        double total = 0.0;
        if (propertiesList == null) {
            return 0.0;
        }
        for (Properties property : propertiesList) {
            if (zipCode.equals(property.zipCode())) {
                total += property.marketValue();
            }
        }
        return total;
    }

    /**
     * Returns the count of vaccinations (full, partial or total) for a given timestamp.
     * @param zipCode
     * @param date
     * @param levelOfVaccination
     * @return
     */
    private double getVaccinationCountForZipDate(String zipCode,
                                                 String date,
                                                 Boolean levelOfVaccination) {
        double total = 0.0;
        if (covidData == null) {
            return 0.0;
        }
        for (CovidData row : covidData) {
            if (zipCode.equals(row.zipCode()) &&
                    row.timeStamp().startsWith(date)) {

                if (levelOfVaccination == null) {
                    total += row.partialVaxCount() + row.fullyVaxCount();
                } else if (levelOfVaccination) {
                    total += row.fullyVaxCount();
                } else {
                    total += row.partialVaxCount();
                }
            }
        }
        return total;
    }



}

