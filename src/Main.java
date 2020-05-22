/**
 *  Main.java
 *
 *  The top-level component of the wealth distribution simulator.
 *
 *  ============================================================================
 *  @authorName     : Waqas Rehmani, Angus Hudson, Jonathan Dunne
 *  @studentNumber  : 1035514, 835808, 836748
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

        Field field = new Field("field", Params.FIELD_HEIGHT, Params.FIELD_WIDTH);
        Turtle[] turtles = new Turtle[Params.NUM_TURTLES];
        
        for (int i = 0; i < Params.NUM_TURTLES; i++) {
            int metabolism = Params.getRandomMetabolism();
            int wealth = Params.getRandomWealth(metabolism);
            int vision = Params.getRandomVision();
            int lifeExpectancy = Params.getRandomLifeExpectancy();
            turtles[i] = new Turtle(vision, wealth, lifeExpectancy, metabolism, field);
            turtles[i].start();
            
            // 1. Move to a random starting patch
            // 2. Randomly assign an age between 0 and life-expectancy
        }
        

        Patch[][] patches = new Patch[Params.FIELD_WIDTH][Params.FIELD_HEIGHT];

        for (int i = 0; i < Params.FIELD_WIDTH; i++) {
            for (int j = 0; j < Params.FIELD_HEIGHT; j++) {
                int maxGrain = 0;
                int nGrain = 0;
                if (Params.getRandomPercentage() <= Params.PERCENT_BEST_LAND) {
                    maxGrain = nGrain = Params.MAX_GRAIN; // 'Rich' patches
                }
                
                // 1. Give all 8 neighbouring patches of patches with grain on them, 25% of the grain present on the initial patch,
                //          the initial patch keeps any grain not allocated to neighbouring patches (sum = 200%), 
                //          we re-max all 'rich' patches at start of every cycle SEE: http://ccl.northwestern.edu/netlogo/docs/dict/diffuse.html
                // 2. Do this 5 times 
                // 3. Do this a further 10 times, except we don't replenish the 'rich' patches 
                // 4. After, round all grain values for each patch down to nearest int, and set max for that patch to be that value

                // This is probably best done not here, initialise patch variables elsewhere

                patches[i][j] = new Patch(nGrain, maxGrain, field);
                patches[i][j].start();
            }
        }

        for (int i = 0; i < Params.NUM_TURTLES; i++) {
            turtles[i].join();
        }

        for (int i = 0; i < Params.FIELD_WIDTH; i++) {
            for (int j = 0; j < Params.FIELD_HEIGHT; j++) {
                patches[i][j].join();
            }
        }

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