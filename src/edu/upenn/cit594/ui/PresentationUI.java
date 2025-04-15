package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.Processor;
import java.util.*;

public class PresentationUI {
    private Scanner scanner = new Scanner(System.in);

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
        switch(input) {
            case "0":
                System.out.println("Exited program. Goodbye!");
                // not valid then exit
                isValidAction = false;
                break;

            case "1":
                displayActions();
                break;

            case "2":
                Processor.calculateTotalPopulation(population, zipCode);
                break;

            case "3":
                Processor.calculateVaccinationsPerCapita(partialVaccination, fullVaccination, zipCode, timeStamp);
                break;

            case "4":
                Processor.calculateAverageMarketValue(marketValue, zipCode);
                break;

            case "5":
                Processor.calculateAverageLivableArea(livableArea, zipCode);
                break;

            case "6":
                Processor.calculateMarketValuePerCapita(population, marketValue, zipCode);
                break;

            case "7":
                Processor.calculateCustomFeature();
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

    }
