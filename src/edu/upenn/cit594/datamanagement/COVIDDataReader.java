package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.*;

/**
 * ConstructorInterface that the json+csv concrete classes will implement to process specific types of data.
 */

public interface COVIDDataReader {

    List<CovidData> readData(String filename); // we might want to make this an ArrayList since we are only adding to the back


}



// interface for json and csv versions of covid data

