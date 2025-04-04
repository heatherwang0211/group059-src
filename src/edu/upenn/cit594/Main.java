package edu.upenn.cit594;

import java.io.File;
import java.util.*;
import edu.upenn.cit594.datamanagement.DataManager;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.PresentationUI;
import edu.upenn.cit594.util.CovidData;

public class Main {
    public static void main(String[] args) {
        // check validity of arguments length - file name tweet, states, and log file
        if (args.length != 3) {
            System.err.println("Not the right amount of file inputs" + "Number of args:" + args.length );
            return;
        }

        // match the file names to the args
        String fileNameCOVID = args[0];
        String fileNameProperties = args[1];
        String fileNameLogger = args[2];

        // initialize logger
        Logger logger = Logger.getInstance();
        logger.configure(fileNameLogger);

        // check tweet file extension
        if (!fileNameCOVID.toLowerCase().endsWith(".json") && !fileNameCOVID.toLowerCase().endsWith(".txt")) {
            System.err.println("Error: tweets file must have a .json or .txt extension.");
            return;
        }

        // assume tweet and state file exist and can be open for reading

        // check if log file can be created and opened for writing
        File loggerFile = new File(fileNameLogger);

        if (loggerFile.exists() && !loggerFile.canWrite()) {
            System.err.println("Error: cannot write to the specified log file.");
            return;
        }

        // initialize data manager, processor and UI
        DataManager dataManager = new DataManager();
        Processor processor = new Processor();
        PresentationUI ui = new PresentationUI();

        //load tweet and states files
        List<CovidData> covidData = new ArrayList<>();
        if (fileNameCOVID.toLowerCase().endsWith(".json")) {
            List<CovidData> jsonCovid = dataManager.loadCovidJson(fileNameCOVID);
            covidData.addAll(jsonCovid);
        }
        if (fileNameCOVID.toLowerCase().endsWith(".txt")) {
            List<CovidData> txtCovid = dataManager.loadCovidTxt(fileNameCOVID);
            covidData.addAll(txtCovid);
        }

        Map<String, PropertyValue> propertyValueMap = dataManager.loadPropertiesData(fileNameProperties);

        // send results to processor to count and display results using the UI file
        TreeMap<String, Integer> result = processor.countCasesByProperty(covidData, propertyValueMap);

        // call the display menu to prompt user for actions
        ui.displayMainMenu();
        ui.displayActions();


        // closing logger
        logger.close();
    }
}
