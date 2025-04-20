package edu.upenn.cit594;

import java.io.File;
import java.util.*;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.PresentationUI;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Properties;

public class Main {
    public static void main(String[] args) {
        // check validity of arguments length - covid file, properties file, population file, logger
        if (args.length != 4) {
            System.err.println("Not the right amount of file inputs" + "Number of args:" + args.length );
            return;
        }

        // match the file names to the args
        String fileNameCOVID = args[0];
        String fileNameProperties = args[1];
        String fileNamePopulation = args[2];
        String fileNameLogger = args[3];

        // LOGGER: initialize loggers
        Logger logger = Logger.getInstance();
        logger.configure(fileNameLogger);

        // check if log file can be created and opened for writing
        File loggerFile = new File(fileNameLogger);
        if (loggerFile.exists() && !loggerFile.canWrite()) {
            System.err.println("Error: cannot write to the specified log file.");
            return;
        }

        // COVID: check covid file extension and load
        if (!fileNameCOVID.toLowerCase().endsWith(".json") && !fileNameCOVID.toLowerCase().endsWith(".csv")) {
            System.err.println("Error: COVID file must have a .json or .csv extension.");
            return;
        }
        List<CovidData> covidData = new ArrayList<>();
        if (fileNameCOVID.toLowerCase().endsWith(".json")) {
            COVIDDataReader reader = new CovidJsonReader();
            List<CovidData> jsonCovid = reader.CovidReader(fileNameCOVID);
            covidData.addAll(jsonCovid);
        }
        if (fileNameCOVID.toLowerCase().endsWith(".csv")) {
            COVIDDataReader reader = new CovidCsvReader();
            List<CovidData> txtCovid = reader.CovidReader(fileNameCOVID);
            covidData.addAll(txtCovid);
        }

        //PROPERTIES: read file
        List<Properties> properties = new ArrayList<>();
        List<edu.upenn.cit594.util.Properties> propertiesList = PropertiesCsvReader.readPropertiesCsv(fileNameProperties);
        properties.addAll(propertiesList);

        // POPULATION: read file
        List<Population> population = new ArrayList<>();
        List<Population> populationList = PopulationCsvReader.readPopulation(fileNamePopulation);
        population.addAll(populationList);

        // initialize relevant data readers, processor and UI
        Processor processor = new Processor(logger, covidData, populationList, propertiesList);
        PresentationUI ui = new PresentationUI();

        // call the display menu to prompt user for actions
        ui.run();

        // closing logger
        logger.close();
    }
}
