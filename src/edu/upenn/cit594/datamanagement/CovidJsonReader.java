package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.CovidData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CovidJsonReader implements COVIDDataReader{
    //HW: updated w override and abstract class
    @Override
    public List<CovidData> readData(String fileName) {
        List<CovidData> covidDataRaw = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            FileReader flieReader = new FileReader(fileName);
            JSONArray covidArray = (JSONArray) parser.parse(fileName);

            for (Object row : covidArray) {
                JSONObject instanceOfJson = (JSONObject) row;
                String zipCodeCell = (String) instanceOfJson.get("zip_code");
                if (zipCodeCell == null) { // make sure there is a valid zipCode
                    continue;
                }
                String zipCode = zipCodeCell.trim();

                // check the zipcode format and size and make sure its valid before proceeding
                if (zipCode.length() != 5) {
                    continue;
                }
                boolean allDigits = true;
                for (char c : zipCode.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        allDigits = false;
                        break;
                    }
                }
                if (allDigits == false) {
                    continue; // skip if the zipcode is poorly formatted
                }
                //HW: updated zip code to integer
                int zipCodeInt = Integer.parseInt(zipCode);

                // ZH need to confirm that this works. DOUBLE CHECK
                String timeStamp = (String) instanceOfJson.get("etl_timestamp");

                if (timeStamp == null) {
                    timeStamp = "";
                } else {
                    timeStamp = timeStamp.trim();
                }

                double partialVaccinatedCount = process((String) instanceOfJson.get("partially_vaccinated"));
                double fullyVaccinatedCount = process((String) instanceOfJson.get("fully_vaccinated"));
                double negativeCasesCount = process((String) instanceOfJson.get("NEG"));
                double positiveCasesCount = process((String) instanceOfJson.get("POS"));
                double hospitalizationCount = process((String) instanceOfJson.get("hospitalized"));
                double deathCount = process((String) instanceOfJson.get("deaths"));
                double boosterShotCount = process((String) instanceOfJson.get("boosted"));


                CovidData covidInstance = new CovidData(
                        zipCodeInt, negativeCasesCount, positiveCasesCount, deathCount, hospitalizationCount, partialVaccinatedCount, fullyVaccinatedCount, boosterShotCount, timeStamp);
                covidDataRaw.add(covidInstance);
            }
        }
        catch (IOException | ParseException e) {
                System.err.println("There was an issue either parsing the file or with the I/O");
            }

            return covidDataRaw;

        }

    private double process(Object JSONField) {
        if (JSONField == null) {
        return 0.0;
        }
        if (JSONField instanceof Number) {
        Number original = (Number) JSONField;
        double updated = original.doubleValue();
                return updated;
    } if (JSONField instanceof String) {
            String originalString = (String) JSONField;
        try {
            return Double.parseDouble(originalString);
        } catch (NumberFormatException e) {
            return 0.0;
        }


    }
        // fallback in case of invalid data, treating it as 0.0
        return 0.0;
    }
}

