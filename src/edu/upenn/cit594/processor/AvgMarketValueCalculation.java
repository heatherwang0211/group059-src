package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Properties;
import java.util.List; //

public class AvgMarketValueCalculation implements strategyAverageCalculator {
    @Override
    public int calculate(List<Properties> propertiesList, int zipCode, String calculationType) {

        if (propertiesList == null || propertiesList.isEmpty() || calculationType == null || !calculationType.equals("marketValue")) {

            return 0;
        }

        double numerator = 0.0;
        int count = 0;
        String  zipCodeString = String.valueOf(zipCode);

        for (Properties property : propertiesList) {
            if (zipCodeString.equals(property.zipCode())) {
                numerator += property.marketValue();
                count++;
            }
        }

        if (count == 0) {
            return 0;
        }
        int finalResult = (int) (numerator / count);
        return finalResult;
    }
}