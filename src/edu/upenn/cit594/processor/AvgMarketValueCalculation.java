package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Properties;
import java.util.List; //

public class AvgMarketValueCalculation implements ComputationStrategy {

    private List<Properties> propertiesList;

    /**
     * This computes the average market value for a given set of properties ingested (and cleaned) in the reader files
     * @param propertiesList
     */
    public AvgMarketValueCalculation(List<Properties> propertiesList) {
        this.propertiesList = propertiesList;
    }

    @Override
    public int crunchNumbers(String zipCode) {
        if (propertiesList == null) {
            return 0;
        }
        double numerator = 0.0;
        int denominator = 0;

        // Loop through the properties in the list and accumulate the numerators and denominators
        for (Properties property : propertiesList) {
            if (property.zipCode().equals(zipCode)) {
                numerator += property.marketValue();
                denominator++;
            }
        }
        if (denominator == 0) {
            return 0;
        }
        double result = numerator / denominator;
        return result;
    }
}