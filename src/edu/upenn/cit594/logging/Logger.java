package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    private static final Logger instance = new Logger();
    private PrintWriter writer;

    private Logger() {}

    public static Logger getInstance() {
        return instance;
    }

    /**
     * configures the logger to write to a new log file
     * if already configured, closes the old file and switches
     * @param logFileName the name of the new log file
     */
    public void configure(String logFileName) {
        // if there is already a writer then close it
        if (writer != null) {
            writer.close();
        }

        try {
            // set up new writer that automatically appends to the existing file if there is
            writer = new PrintWriter(new FileWriter(logFileName, true),true);
        }

        // exception handling
        catch (IOException e) {
            System.err.println("Could not open log file: " + e.getMessage());
            writer = null;
        }
    }

    /**
     * logs a single event
     * @param event the event to log
     */
    public void log(String event) {
        if (writer != null) {
            writer.println(event);
            writer.flush();
        }
    }

    /**
     * this method closes the logger when done writing
     */
    public void close() {
        if (writer != null) {
            writer.close();
            // set it to null after closing
            writer = null;
        }
    }
}