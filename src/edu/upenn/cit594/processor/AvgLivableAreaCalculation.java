package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Properties;
import java.util.List; //

public class AvgLiveableAreaCalculation implements strategyAverageCalculator{

    private List<Properties> propertiesList;

    /**
     * This computes the average market value for a given set of properties ingested (and cleaned) in the reader files
     * @param propertiesList
     */
    public AvgLiveableAreaCalculation(List<Properties> propertiesList) {
        this.propertiesList = propertiesList;
    }

    @Override
    public double crunchNumbers(int zipCode) {
        if (propertiesList == null) {
            return 0;
        }
        double numerator = 0.0;
        int denominator = 0;
        for (Properties prop : propertiesList) {
            if (prop.zipCode().equals(zipCode)) {
                numerator += prop.livableArea();
                denominator++;
            }
        }
        if (count == 0) {
            return 0;
        }
        double result = (double) (numerator / denominator);
        return result;
    }
}