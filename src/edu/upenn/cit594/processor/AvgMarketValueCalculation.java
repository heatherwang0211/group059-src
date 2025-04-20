package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Properties;

import javax.xml.crypto.Data;
import java.util.List; //

public class AvgMarketValueCalculation implements strategyAverageCalculator {

    private List<Properties> propertiesList;

    /**
     * This computes the average market value for a given set of properties ingested (and cleaned) in the reader files
     * @param propertiesList
     */
    public AvgMarketValueCalculation(List<Properties> propertiesList) {
        this.propertiesList = propertiesList;
    }

   /* @Override
    public double crunchNumbers(int zipCode) {
        return 0;
    }*/

    @Override
    public int calculate(List<Properties> propertiesList, int zipCode, String columnName) {

        if (propertiesList == null || propertiesList.isEmpty()) {
            return 0;
        }

        double numerator   = 0.0;
        int    denominator = 0;
        String zipCodeString = columnName;

        for (Properties property : propertiesList) {
            //Properties property = (Properties) generic;
            if (zipCodeString.equals(property.zipCode())) {
                numerator   += property.marketValue();
                denominator++;
            }
        }

        if (denominator == 0) {
            return 0;
        }

        return (int) numerator / denominator;
    }
}