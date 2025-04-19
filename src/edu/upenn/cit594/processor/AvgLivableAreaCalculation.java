package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Properties;
import java.util.List;

public class AvgLiveableAreaCalculation implements strategyAverageCalculator {
    @Override
    public int calculate(List<Properties> propertiesList, int zipCode, String calculationType) {

        if (propertiesList == null || propertiesList.isEmpty() || calculationType == null || !calculationType.equals("livableArea")) {
            return 0;
        }

        double numerator   = 0.0;
        int denominator = 0;
        String zipcodeString = String.valueOf(zipCode);

        for (Properties property : propertiesList) {
            if (zipCodeString.equals(property.zipCode())) {
                numerator += property.livableArea();
                denominator += 1;
            }
        }

        if (denominator == 0) {
            return 0;
        }
        int result = (int) (numerator / denominator);
        return result;
    }
}