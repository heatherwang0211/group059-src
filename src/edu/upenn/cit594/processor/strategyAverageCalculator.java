package edu.upenn.cit594.processor;

/**
 * This interface will be used to simplify all the calculations we run in the processor class.
 */
interface strategyAverageCalculator {

    double calculate(List<Data> data, String columnName);//


    class average implements strategyAverageCalculator {
        @Override
        public double calculate(List<Data> data, String columnName) {
            if (data == null || data.isEmpty()) {
                throw new IllegalArgumentException("Data cannot be empty");
            }

            double sum = 0;
            for (DataRecord record : data) {
                sum += record.getDoubleValue(columnName);
            }

            return sum / data.size();
        }
        }
    }
