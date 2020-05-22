/**
 *  Main.java
 *
 *  The top-level component of the questing knights simulator.
 *
 *  ============================================================================
 *  @authorName     : Waqas Rehmani, Angus Hudson, Jonathan Dunne
 *  @studentNumber  : 1035514
 *  ============================================================================
 *
 *  It is responsible for:
 *      - Creating all the components of the system.
 *      - Starting all of the processes.
 *
 *  ============================================================================
 *  Detailed description of Methods:
 *  ============================================================================
 *  This class has one method:
 *      1. main(String [] args):
 *          This is the main method used to run the simulation program.
 *
 */
import java.util.*; // Import ArrayList
import java.io.*;   // Import FileWriter

public class Main {

    public static void main(String [] args) throws InterruptedException {

        /*
        List<List<String>> rows = Arrays.asList(
            Arrays.asList("Jack", "Sailor", "0340138128"),
            Arrays.asList("Bond", "Spy", "0467263982"),
            Arrays.asList("Harry", "Wizard", "11111100232")
        );

        createCSV("Name", "Occupation", "Phone Number", rows); 
        */
    }

    public static void createCSV(String heading1, String heading2, String heading3, List<List<String>> data) {
        try {
            FileWriter csvWriter = new FileWriter("fileName.csv");
            csvWriter.append(heading1);
            csvWriter.append(",");
            csvWriter.append(heading2);
            csvWriter.append(",");
            csvWriter.append(heading3);
            csvWriter.append("\n");

            for (List<String> rowData : data) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {

        }
    }
    

}