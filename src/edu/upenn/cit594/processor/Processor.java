package edu.upenn.cit594.processor;

import edu.upenn.cit594.logging.Logger;

public class Processor {
    private Logger logger;

    //need to add the lists to store and manipulate this information later
    private  List<CovidData> covidData;
    private  List<Population> populationList;
    private  List<Properties> propertiesList;

    /**
     * Constuctor for the processor that needs the logger + all the data-lists.
     *
     * @param logger
     */
    public Processor(Logger logger, List<CovidData> covidData, List<Population> populationList, List<Properties> propertiesList) {
        this.logger = logger;
        this.covidData = covidData;
        this.populationList = populationList;
        this.propertiesList = propertiesList;
    }

    /**
     * This is the actual use of the strategy pattern that lets us set us set a strategy and then crunch the numbers based on the other classes.
     * @param pattern
     * @param zipCode
     * @return
     */
    public double crunchNumbersFromStrategy(simpleStrategy pattern, String zipCode) {
        double output = pattern.compute(zipCode);
        return output;
    }

    /**
     * Computes total population across every zipCode
     * @return
     */
    public int calculateTotalPopulation() {
        if (populationList == null) {
            return 0;
        }
        double sum = 0;
        for (Population row : populationList) {
            sum += row.getPopulation();
        }

        int total = (int) sum; // double check the casting type

        return total;
    }

    /**
     * This calculates the total vaccinations per capita and uses a helper method to determine what level of vaccination we want
     * @param zipCode
     * @param timeStamp
     * @return
     */
    public double calculateVaccinationsPerCapita(String zipCode, String timeStamp) {
        int population = getPopulationsUsingZipCode(zipCode);
        if (population == 0) {
            return 0.0;
        }

        // In this case, null means we want all the vaccines (See the helper method below)
        double totalVaccines = countAllVaccines(zipCode, timeStamp, null);
        return totalVaccines / population;
    }

    /**
     * This method counts all vaccines for a zipcode and time window. it uses the levelOfVaccination flag to determine if the user wants full vaccines (true), single doses (false), or both categories together (null).
     * @param zipCode
     * @param date
     * @param levelOfVaccination
     * @return
     */
    private double countAllVaccines(String zipCode, String date, Boolean levelOfVaccination) {
        double total = 0.0;
        if (covidData == null) {
            return 0.0;
        }
        for (CovidData record : covidData) {
            if (record.zipCode().equals(zipCode) && record.timeStamp().startsWith(date)) {
                // null means all vaccines
                if (levelOfVaccination == null) {
                    total += record.partialVaxCount() + record.fullyVaxCount();
                    // true means only full vaccines vaccines
                } else if (levelOfVaccination == true) {
                    total += record.fullyVaxCount();
                    // false (i.e. everything else) means only partial vaccines vaccines
                } else {
                    total += record.partialVaxCount();
                }
            }
        }
        return total;
    }

    /**
     * Calculations the market value per capita by looping through the data and getting the inputs to the ratio
     * @return
     */
    public double calculateMarketValuePerCapita() {
        // run some lazy null checks up front to terminate a process if we can. Lazy evaluation
        if (propertiesList == null || populationList == null) {
            return 0;
        }

        //get all the market values
        double totalMarketValue = 0.0;
        for (Properties property : propertiesList) {
            totalMarketValue += property.marketValue();
        }

        //sum up the populations
        int totalPopulation = 0;
        for (Population row : populationList) {
            totalPopulation += row.getPopulation();
        }

        //Check if our denominator will be zero which would throw an error
        if (totalPopulation == 0) {
            return 0;
        }

        // calculate the final result as a ratio
        double marketValuePerCapita = (totalMarketValue / totalPopulation);
                //System.out.println(marketValuePerCapita);
        return marketValuePerCapita;
    }

    /**
     *
     * @param zipCode
     * @return
     */
    public int getPopulationsUsingZipCode(String zipCode) {
            if (populationList == null) {
                return 0;
            }
            for (Population row : populationList) {
                if (row.getZipCode().equals(zipCode)) {
                    return row.getPopulation();
                }
            }
            return 0;
    }

}

