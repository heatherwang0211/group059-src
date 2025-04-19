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
    @SuppressWarnings("unchecked")
    public double calculate(List<Data> data, String columnName) {
        if (data == null || data.isEmpty()) {
            return 0;
        }

        double numerator   = 0.0;
        int    denominator = 0;
        String zipCodeString = columnName;   // ZIP to filter on

        for (Data generic : data) {          // iterate over caller‑supplied list
            Properties property = (Properties) generic;   // down‑cast
            if (zipCodeString.equals(property.zipCode())) {
                numerator   += property.livableArea();
                denominator++;
            }
        }

        if (denominator == 0) {
            return 0;
        }
        return numerator / denominator;
    }
}