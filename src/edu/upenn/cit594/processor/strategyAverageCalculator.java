package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Properties;
import java.util.List;

/**
 * This interface will be used to simplify all the calculations we run in the processor class.
 */
interface strategyAverageCalculator {

    int calculate(List<Properties> propertiesList, int zipCode, String calculationType);

    class average implements strategyAverageCalculator {

        @Override
        public int calculate(List<Properties> propertiesList, int zipCode, String calculationType) {

            if (propertiesList == null || propertiesList.isEmpty()) {
                return 0;
            }

            double numerator = 0.0;
            int denominator = 0;
            String zipStr = String.valueOf(zipCode);

            for (Properties property : propertiesList) {
                if (zipStr.equals(property.zipCode())) {

                    if ("marketValue".equals(calculationType)) {
                        numerator += property.marketValue();
                    } else if ("livableArea".equals(calculationType)) {
                        numerator += property.livableArea();
                    } else {
                        continue;
                    }
                    denominator++;
                }
            }

            if (denominator == 0) {
                return 0;
            }
            int finalOutput = (int) (numerator / denominator);

            return finalOutput;
        }
    }
}

