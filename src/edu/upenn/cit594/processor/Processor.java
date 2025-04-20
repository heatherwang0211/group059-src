package edu.upenn.cit594.processor;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Properties;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {
    private static Logger logger;

    private static List<CovidData> covidData;
    private static List<Population> populationList;
    private static List<Properties> propertiesList;
    private static Map<Integer, Integer> VaccinationMarketValueByZipCode = new HashMap<>();
    private static strategyAverageCalculator avgMarketValue = new AvgMarketValueCalculation(propertiesList);
    private static strategyAverageCalculator avgLivableArea = new AvgLivableAreaCalculation();


    /**
     * Created this innit method so that we can use the static methods without needing an instance of the class.
     *
     * @param logger
     * @param populationList
     * @param propertiesList
     * @param covidData
     */
    public static void init(Logger logger,
                            List<Population> populationList,
                            List<Properties> propertiesList,
                            List<CovidData> covidData) {

        Processor.logger = logger;
        Processor.populationList = populationList;
        Processor.propertiesList = propertiesList;
        Processor.covidData = covidData;
    }

    /**
     * Back when we were using an instance version of the Processor, we needed this, but not really anymore. Leaving here for posterity
     *
     * @param logger
     * @param covidData
     * @param populationList
     * @param propertiesList
     */
    public Processor(Logger logger,
                     List<CovidData> covidData,
                     List<Population> populationList,
                     List<Properties> propertiesList) {

        Processor.logger = logger;
        Processor.covidData = covidData;
        Processor.populationList = populationList;
        Processor.propertiesList = propertiesList;
    }

    /**
     * Calculates the total population of all the zipCodes
     * @return
     */
    public static int calculateTotalPopulation() {
        int totalPopulation = 0;
        for (Population row : populationList) {
            totalPopulation += row.getPopulation();
        }
        int result = totalPopulation;
        return result;
    }

    /**
     * Calculates the vaccinations per capita given the zipCode
     * @param zipCode
     * @param date
     * @param vaccinationStatus
     * @return
     */
    public static double calculateVaccinationsPerCapita(int zipCode, String date, Boolean vaccinationStatus) {

        int population = fetchPopulationsUsingZipCode(zipCode);
        if (population == 0) {
            double result = 0;
            return result;
        }

        double vaccinations = getVaccinationCountForZipDate(String.valueOf(zipCode), date, vaccinationStatus);
        double finalReturn = vaccinations / population;
        return finalReturn;
    }

    /**
     * Calculates teh average MarketValue for a given zipCode
     * @param zipCode
     * @return
     */
    public static int calculateAverageMarketValue(int zipCode) {
        return avgMarketValue.calculate(propertiesList, zipCode, "marketValue");
    }

    /**
     * Calculates the average livable area of a given zipCode
     * @param zipCode
     * @return
     */
    public static int calculateAverageLivableArea(int zipCode) {
        return avgLivableArea.calculate(propertiesList, zipCode, "livableArea");
    }

    /**
     * Calculates the market value per capita using a zipCode
     * @param zipCode
     * @return
     */
    public static int calculateMarketValuePerCapita(int zipCode) {

        int population = fetchPopulationsUsingZipCode(zipCode);
        if (population == 0) {
            int result = 0;
            return result;
        }

        double totalMarketValue =
                getTotalMarketValueForZip(String.valueOf(zipCode));
        if (totalMarketValue == 0.0) {
            int result = 0;
            return result;
        }

        int result = (int) (totalMarketValue / population);
        return result;
    }

    /**
     * Relates deaths to property values by calculating death percentages first and then giving by market values
     * @param zipCode
     * @return
     */
    public static int calculateDeathToPropertyValueRatio(int zipCode) {

        int population = fetchPopulationsUsingZipCode(zipCode);
        if (population == 0) {
            int result = 0;
            return result;
        }

        double deathCount = 0.0;
        for (CovidData row : covidData) {
            if (String.valueOf(zipCode).equals(row.zipCode())) {
                deathCount = row.deathsCount();
                break;
            }
        }
        if (deathCount == 0.0) {
            int result = 0;
            return result;
        }

        double deathRate = deathCount / population;
        int averageMarketValue = calculateAverageMarketValue(zipCode);
        if (averageMarketValue == 0) {
            int result = 0;
            return result;
        }

        int result = (int) (deathRate / averageMarketValue);
        return result;
    }

    /**
     * Additional Method that retrieves the vaccination-Value ratio of a given zipCode
     * @param zipCode
     * @param date
     * @return
     */
    public static int getVaccinationValueRatio(int zipCode, String date) {

        if (VaccinationMarketValueByZipCode.containsKey(zipCode)) {
            int result = VaccinationMarketValueByZipCode.get(zipCode);
            return result;
        }

        int ratio = computeVaccinationValueRatio(zipCode, date);
        VaccinationMarketValueByZipCode.put(zipCode, ratio);
        int result = ratio;
        return result;
    }

    /**
     * Gets the population count using a zipCode
     * @param zipCode
     * @return
     */
    public static int fetchPopulationsUsingZipCode(int zipCode) {
        for (Population person : populationList) {
            if (person.getZipCode() == zipCode) {
                int result = (int) person.getPopulation();
                return result;
            }
        }
        int result = 0;
        return result;
    }

    /**
     * Uses a zipCode to get the total market value or properties in that area.
     * @param zipCode
     * @return
     */
    private static double getTotalMarketValueForZip(String zipCode) {
        double totalMarketValue = 0.0;
        for (Properties property : propertiesList) {
            if (zipCode.equals(property.zipCode())) {
                totalMarketValue += property.marketValue();
            }
        }
        double result = totalMarketValue;
        return result;
    }

    /**
     * Calculates the vaccination count primarily using a flag that determins what level of vaddination we want.
     * @param zipCode
     * @param date
     * @param vaccinationStatus
     * @return
     */
    private static double getVaccinationCountForZipDate(String zipCode, String date, Boolean vaccinationStatus) {

        double total = 0.0;
        for (CovidData row : covidData) {
            if (zipCode.equals(row.zipCode()) &&
                    row.timeStamp().startsWith(date)) {

                if (vaccinationStatus == null) {
                    total += row.partialVaxCount() + row.fullyVaxCount();
                } else if (vaccinationStatus == true) {
                    total += row.fullyVaxCount();
                } else {
                    total += row.partialVaxCount();
                }
            }
        }
        double result = total; // double check we are still using doubles
        return result;
    }

    /**
     * Additional private method that calculates and lazily populates the map of vaccination-Value ratios
     * @param zipCode
     * @param date
     * @return
     */
    private static int computeVaccinationValueRatio(int zipCode, String date) {

        int averageMarketValue = calculateAverageMarketValue(zipCode);
        if (averageMarketValue == 0) {
            int result = 0;
            return result;
        }

        int population = fetchPopulationsUsingZipCode(zipCode);
        if (population == 0) {
            int result = 0;
            return result;
        }

        double vaccinated =
                getVaccinationCountForZipDate(String.valueOf(zipCode),
                        date,
                        null);
        if (vaccinated == 0.0) {
            int result = 0;
            return result;
        }

        double vaccinationPercentage = (vaccinated / population) * 100.0;
        int result = (int) (averageMarketValue / vaccinationPercentage);
        return result;
    }

    /**
     * Gets the count of the properties by ZipCode
     * Note: didn't end up using this and just brought the logic/inspo up when needed.
     * @param zipCode
     * @return
     */
    private static int getPropertyCountByZip(int zipCode) {

        if (propertiesList == null || propertiesList.isEmpty()) {
            int result = 0;
            return result;
        }

        int propertyCounter = 0;
        String zipCodeString = String.valueOf(zipCode);

        for (Properties property : propertiesList) {
            if (zipCodeString.equals(property.zipCode())) {
                propertyCounter++;
            }
        }
        int result = propertyCounter;
        return result;
    }
}