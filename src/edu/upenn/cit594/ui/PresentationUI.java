package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.Processor;
import java.util.*;

public class PresentationUI {
    private Scanner scanner;

    public PresentationUI() {
        scanner = new Scanner(System.in);
    }

    /**
     * specifies all the 8 actions users can take based on their input number and displays corresponding results
     * also handles when users input invalid action inputs
     */
    public void run() {
        boolean running = true;

        // as long as user chooses to continue the main menu will be displayed
        while (running) {
            displayMainMenu();

            try {
                // get the input and check whether it is valid
                String input = scanner.nextLine().trim();
                if (input.equals("0")) {
                    System.out.println("Exited program. Goodbye!");
                    running = false;
                } else if (input.equals("1")) {
                    handleActionsMenu();
                }
                // if not valid option, then prompt to re-input
                else {
                    System.out.println("Invalid input. Please enter either 0 or 1.");
                }
            }
            // handling of other exceptions
            catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * this method handles the actions main menu if user decides to proceed
     */
    private void handleActionsMenu() {
        boolean isValidAction = true;
        System.out.flush();

        // go to display if valid
        while (isValidAction) {
            displayActions();
        }

        // try to get the input and clear
        try {
            String input = scanner.nextLine().trim();

            // switch between all 8 cases based on input
            // do the calculations using the methods from processor
            switch (input) {
                case "0":
                    System.out.println("Exited program. Goodbye!");
                    // not valid then exit
                    isValidAction = false;
                    break;

                case "1":
                    displayActions();
                    break;

                case "2":
                    // it should just display total population for ALL the zip codes together
                    System.out.println("BEGIN OUTPUT");
                    int totalPopulation = Processor.calculateTotalPopulation(population, zipCode);
                    System.out.println(totalPopulation);
                    System.out.println("END OUTPUT");
                    break;

                case "3":
                    //prompt user to input "partial" or "full", then input a YYYY-MM-DD date
                    System.out.println("Please enter vaccination type ('partial' or 'full'):");
                    System.out.flush();
                    String vaccinationType = scanner.nextLine().trim().toLowerCase();

                    // validate vaccination type input
                    while (!vaccinationType.equals("partial") && !vaccinationType.equals("full")) {
                        System.out.println("Invalid input. Please enter exactly either 'partial' or 'full':");
                        System.out.flush();
                        vaccinationType = scanner.nextLine().trim().toLowerCase();
                    }
                    System.out.println("Thank you. You have requested for: " + vaccinationType + " data.");
                    System.out.println("Please enter date in format YYYY-MM-DD:");
                    System.out.flush();
                    String dateInput = scanner.nextLine().trim();

                    // validate date format
                    while (!isValidDateFormat(dateInput)) {
                        System.out.println("Invalid date format. Please enter date in format YYYY-MM-DD:");
                        System.out.flush();
                        dateInput = scanner.nextLine().trim();
                    }

                    System.out.println("BEGIN OUTPUT");
                    int vacPerCapita = Processor.calculateVaccinationsPerCapita(vaccination, zipCode, timeStamp);
                    System.out.println(vacPerCapita);
                    System.out.println("END OUTPUT");
                    break;

                case "4":
                    //prompt user to enter 5 digit zip code
                    int zipCodeAvgMarketVal = promptForZipCode();
                    // result is truncated to an int, call processor method
                    System.out.println("BEGIN OUTPUT");
                    int averageMarketValue = (int) Processor.calculateAverageMarketValue(marketValue, zipCodeAvgMarketVal);
                    System.out.println(averageMarketValue);
                    System.out.println("END OUTPUT");
                    break;

                case "5":
                    //prompt user to enter 5 digit zip code
                    int zipCodeLivableArea = promptForZipCode();
                    // result is truncated to an int, call processor method
                    System.out.println("BEGIN OUTPUT");
                    int averageLivableArea = (int) Processor.calculateAverageLivableArea(livableArea, zipCodeLivableArea);
                    System.out.println(averageLivableArea);
                    System.out.println("END OUTPUT");
                    break;

                case "6":
                    //prompt user to enter 5 digit zip code
                    int zipCodeMarketValPerCap = promptForZipCode();
                    // result is truncated to an int
                    System.out.println("BEGIN OUTPUT");
                    int marketValuePerCap = (int) Processor.calculateMarketValuePerCapita(population, marketValue, zipCodeMarketValPerCap);
                    System.out.println(marketValuePerCap);
                    System.out.println("END OUTPUT");
                    break;

                case "7":
                    System.out.println("BEGIN OUTPUT");
                    double correlation = Processor.calculateVaccinationPropertyValueCorrelation();
                    System.out.println(correlation);
                    System.out.println("END OUTPUT");
                    break;

                // case where user inputs something invalid
                default:
                    System.out.println("Invalid input. Please try again with a valid integer between 0 and 7.");
                    System.out.flush();
                    break;
            }
        }
        // catch exceptions and display
        catch (Exception e) {
            System.out.println("Invalid input. Please try again with a valid integer between 0 and 7.");
            System.out.flush();
            scanner.nextLine();
        }
    }


    /**
     * prints out the main menu for users to choose to continue to explore or exit
     */
    public void displayMainMenu() {
        System.out.println("Welcome to the main menu. This program calculates some COVID-related data.");
        System.out.println("Enter the corresponding number to navigate through this program and review these numbers:");
        System.out.println("Press 1 to show the available actions and calculations.");
        System.out.println("Press 0 to exit the program.");
        System.out.flush();
    }

    /**
     * prints out the actions menu for users to choose which data to look at
     */
    public void displayActions() {
        System.out.println("Here are some of the available calculations to check:");
        System.out.println("Press 2 to show the total population for all ZIP codes.");
        System.out.println("Press 3 to show the total vaccinations per capita for each ZIP code for the specified date.");
        System.out.println("Press 4 to show the average market value for properties in a specified ZIP Code.");
        System.out.println("Press 5 to show the average total livable area for properties in a specified ZIP Code.");
        System.out.println("Press 6 to show the total market value of properties, per capita, for a specified ZIP Code.");
        System.out.println("Press 7 to show the results of our customer feature.");
        System.out.flush();
    }

    /**
     * helper method that validates if the input is in the right format
     */
    private boolean isValidZipCode(String zipCode) {
        return zipCode.matches("\\d{5}");
    }

    /**
     * helper method to prompt the user for a valid 5-digit zip code
     *
     * @return a validated 5-digit Philadelphia zip code as int
     */
    private int promptForZipCode() {
        System.out.println("Please enter a 5-digit Philadelphia zip code.");
        System.out.flush();
        String zipInput = scanner.nextLine().trim();

        // validate format and reprompt if not valid
        while (!isValidZipCode(zipInput)) {
            System.out.println("Invalid. Please enter a valid 5-digit Philadelphia zip code:");
            System.out.flush();
            zipInput = scanner.nextLine().trim();
        }
        int zipCode = Integer.parseInt(zipInput);

        return zipCode;
    }

    /**
     * helper method that validates if the input follows the YYYY MM DD date format
     */
    private boolean isValidDateFormat(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        String[] parts = date.split("-");
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        if (month < 1 || month > 12) {
            return false;
        }

        if (day < 1 || day > 31) {
            return false;
        }

        return true;

    }
}
