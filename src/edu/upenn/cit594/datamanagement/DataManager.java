package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.CovidData;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Properties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataManager {

    /**
     * Loads COVID data in json file format
     * @param fileName
     * @return
     */
    //****** I JUST COPY PASTED FROM My solo assignment for now, didnt take a suepr close look yet
    // putting it here for reference for json
    public List<CovidData> loadCovidDataJson(String fileName) {
        // initiate
        List<CovidData> covidData = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            // try to parse the json file
            JSONArray CovidArray = (JSONArray) parser.parse(new FileReader(fileName));

            for (Object obj : CovidArray) {

                // create json object, and parse each item into the json object
                JSONObject covidJson = (JSONObject) obj;

                // add the parsed tweet into a new tweet object with the 3 pieces of info
                String text = (String) covidJson.get("text");
                JSONArray locationArray = (JSONArray) covidJson.get("location");
                // if they are valid then proceed to update the object
                if (locationArray != null && locationArray.size() >= 2) {
                    double latitude = ((Number) locationArray.get(0)).doubleValue();
                    double longitude = ((Number) locationArray.get(1)).doubleValue();

                    covidData.add(new CovidData(text, latitude, longitude));

                }

                else {
                    System.err.println("Error parsing json file: " + covidJson.get("text"));
                }
            }

        }
        // exception handling
        catch (IOException | ParseException e) {
            System.err.println("Error reading or parsing JSON file: " + e.getMessage());
        }
        return covidData;
    }

    /**
     * Loads COVID data in csv format
     * @param fileName
     * @return
     */
    public List<CovidData> loadCovidDataCsv(String fileName) {
        List<CovidData> covidData = new ArrayList<>();
        return null;
    }

    /**
     * Loads downsampled properties data in csv format
     * @param fileName
     * @return
     */
    public List<Properties> loadPropertiesDataCsv(String fileName) {
        List<Properties> properties = new ArrayList<>();
        return null;
    }

    /**
     * Loads populations data in csv format
     * @param fileName
     * @return
     */
    // DO WE NEED A POPULATION CLASS? .... NOT SURE .. LMK
    public List<Population> loadPopulationsDataCsv(String fileName) {
        List<Population> population = new ArrayList<>();
        return population;
    }
}
