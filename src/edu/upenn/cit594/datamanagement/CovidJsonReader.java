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

public class CovidJsonReader implements CovidDataReader{

    public List<CovidData> readData(String filename) {
        List<CovidData> covidDataRaw = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            FileReader flieReader = new FileReader(fileName);
            JSONArray covidArray = (JSONArray) parser.parse(reader);

            for (Object row : covidArray) {
                JSONObject instanceOfJson = (JsonObject) row;
                String zipCodeCell = (String) instanceOfJson.get("zip_code");
                if (zipCodeCell = null) { // make sure there is a valid zipCode
                continue;
                }
                String zipCode = zipCodeCell.trim()

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

                //////String timeStamp = (String) instanceOfJson.get("");
                /// //// NEED TO FIGURE OUT WHAT TO CHECK FOR WITHIN THE JSON BODY

                if (timeStamp == null) {
                timeStamp = "";
                } else {
                    timeStamp = timeStamp.trim();
                }

                double partialVaccinatedCount = process(rows[2]);
                double fullyVaccinatedCount = process(rows[3]);
                double negativeCasesCount = process(rows[4]);
                double positiveCasesCount = process(rows[5]);
                double hospitalizationCount = process(rows[6]);
                double deathCount = process(rows[7]);
                double boosterShotCount = process(rows[8]);

                CovidData covidInstance = new CovidData(
                        zipCode, negativeCasesCount, positiveCasesCount, deathCount, hospitalizationCount, partialVaccinatedCount, fullyVaccinatedCount, boosterShotCount, timeStamp);
                covidDataRaw.add(covidInstance);
            } catch (IOException | ParseException e) {
                System.err.println("There was an issue either parsing the file or with the I/O");
            }

            return covidDataRaw;
            }


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

