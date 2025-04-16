package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PopulationCsvReader {

    public List<Population> readPopulation(String fileName) {
        List<Population> population = new ArrayList<>();

        try (BufferedReader buffReader = new BufferedReader(new FileReader(fileName))) {
            String header = buffReader.readLine(); // get the header
            if (header == null) { // need this check since the interface doesnt do any of this checking
                return population;
            }

            String line;
            while ((line = buffReader.readLine()) != null) {
                String[] rows = line.split(",");

                if (rows.length < 2 && rows != null) {
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

                int zipCodeInt = Integer.parseInt(zipCode);

                String populationCell = rows[1];  // grab the right cell for timezone and store it
                int populationValue = Integer.parseInt(populationCell.trim());

                Population populationRecord = new Population(zipCodeInt, populationValue);
                population.add(populationRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return population;
    }

        private double process (String cell){
            try {
                if (cell == null || cell.isEmpty()) {
                    return 0.0;
                }
                return Double.parseDouble(cell);
            } catch (NumberFormatException n) {
                return 0.0;
            }
        }
    }

