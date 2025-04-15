package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CovidCsvReader implements COVIDDataReader {
    // HW: updated with override and abstract class
    @Override
    public List<CovidData> CovidCsvReader(String fileName) {
    List<CovidData> covidDataRaw = new ArrayList<>();

    try (BufferedReader buffReader = new BufferedReader(new FileReader(fileName)))
    {
        String header = buffReader.readLine(); // get the header
        if (header == null) { // need this check since the interface doesnt do any of this checking
            return covidDataRaw;
        }

        String line;
        while ((line = buffReader.readLine()) != null) {
            String[] rows = line.split(",");

            if (rows.length < 9 && rows != null) {
                continue; // exit the loop if we have an insufficient number of columns
            }

            // grab the right cell for zipcode, store it, clean it, and check if its valid
            // Note: this also uses the efficiency tactic of moving the code body into a single method call rather than using helper methods.
            String zipCodeCell = rows[0];
            String zipCode = zipCodeCell.trim();

            if (zipCode.length() != 5) {
                continue; // skip it since it has the incorrect number of digits
            }

            boolean allDigits = true; // create a falg to check if the zipcode is all digits
            for (char z : zipCode.toCharArray()) {
                if (Character.isDigit(z) == false) { // if one of the fields in the zip isnt a digit, update the flag
                    allDigits = false;
                    break;
                }
            }
            if (allDigits == false) {  // use the flag to determine whether or not the zipcode is valid
                continue;
            }

            String timeStampCell = rows[1];  // grab the right cell for timezone and store it
            String timeStamp = timeStampCell.trim();

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
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
        return covidDataRaw;
        }

        private double process(String cell) {
            try {
                if (cell == null || cell.isEmpty()) {
                    return 0.0;
                }
                return Double.parseDouble(cell);
            } catch (NumberFormatException n) {
                return 0.0;
            }
        }}
