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
 *      - Running the simlation.
 *
 *  ============================================================================
 *  Detailed description of Methods:
 *  ============================================================================
 *  This class has 16 methods:
 *      1. main(String [] args):
 *          This is the main method used to run the simulation program.
 *      2. runSimulation(Turtle[] turtles, Field field):
 *           The main method to run the simulation.
 *      3. initialiseTurtles(Turtle[] turtles, Field field):
 *          To initialize the array of turtles
 *      4. initialiseTurtle(Patch patch):
 *          To initialize a single turtle.
 *      5. updateTurtle(Turtle t):
 *          To update turtles.
 *      6. updateTurtleLocation(Turtle t, Field field):
 *          To update a turtle's location
 *      7. recolorTurtles(Turtle[] turtles):
 *          To recolour the turtles.
 *      8. trackColors(Turtle [] turtles, int tick):
 *          To track the colors in the model. This method is used to countthe 
 *          different colors and return an array list, which would be used to 
 *          generate a row in the CSV.
 *      9. updateLorenzAndGini(Turtle [] turtles, int tick):
 *          To update the Lorenz points and Gini index. This method is used 
 *          to calculate the Lorenz Points and the Gini index  and return an 
 *          ArrayList, which would be used to generate a row in the CSV.
 *      10. createCSVColorsz(List<List<String>> data):
 *          To create the CSV file for the color percentage.
 *      11. createCSVLorenzz(List<List<String>> data):
 *          To create the CSV file for the Lorenz points and gini index.
 *      12. turnTowardsGrain(Turtle t, Field field):
 *          To turn the Turtle towards the grain.
 *      13. grainAhead(Turtle t, Field field):
 *          To check the grains in the Patches ahead.
 *      14. harvest(Turtle[] turtles):
 *          To harvesr grain.
 *      15. moveEatAgeDie(Turtle t, Field field) :
 *          To do the process of move -> eat -> die for the turtles.
 *      16. moveTurtle(Turtle t, Field field):
 *          To move the turtle to a random Patch in the Field.
 */

 // Import relevant libraries.
import java.util.*; 
import java.io.*;    
import java.text.DecimalFormat;

public class Main {
    public static void main(String [] args) throws InterruptedException {
        Turtle[] turtles = new Turtle[Params.NUM_TURTLES];
        Field field = new Field(Params.FIELD_WIDTH, Params.FIELD_HEIGHT);
        initialiseTurtles(turtles, field);
        runSimulation(turtles, field);
    }

    // ------------------------------------------------------------------------
    // Method: The main method to run the simulation.
    // ------------------------------------------------------------------------
    public static void runSimulation(Turtle[] turtles, Field field) {
        int ticks = 0;
        List<List<String>> dataRows = new ArrayList<List<String>>();
        List<List<String>> lorenzDataRows = new ArrayList<List<String>>(); 
        while (ticks < Params.N_TICKS) {
            for (Turtle t : turtles) {
                turnTowardsGrain(t, field);
            }
            harvest(turtles);
            for (Turtle t : turtles) {
                moveEatAgeDie(t, field);
            }
            recolorTurtles(turtles);

            if (ticks % Params.GRAIN_GROWTH_INTERVAL == 0) {
                field.growPatches();
            }
            dataRows.add(trackColors(turtles, ticks));
            lorenzDataRows.add(updateLorenzAndGini(turtles, ticks));
            ticks++;
        }
        createCSVColors("colorPercentage.csv", "ticks", "red%", "green%", "blue%", dataRows); 
        createCSVLorenz(lorenzDataRows); 
    }

    // ------------------------------------------------------------------------
    // Method: To initialize the array of turtles
    // ------------------------------------------------------------------------
    public static void initialiseTurtles(Turtle[] turtles, Field field) {
        for (int i = 0; i < Params.NUM_TURTLES; i++) {
            // set turtle initial location
            Patch patch = field.getRandomPatch();
            turtles[i] = initialiseTurtle(patch);
            patch.setNTurtles(patch.getNTurtles() + 1);
        }
        recolorTurtles(turtles);
    }
    // ------------------------------------------------------------------------
    // Method: To onitialize a sing turtle.
    // ------------------------------------------------------------------------
    public static Turtle initialiseTurtle(Patch patch) {
        // generate random parameters.
        int metabolism = Params.getRandomMetabolism();
        int wealth = Params.getRandomWealth(metabolism);
        int vision = Params.getRandomVision();
        int lifeExpectancy = Params.getRandomLifeExpectancy();
        int age = Params.getRandomAge(lifeExpectancy);
        return new Turtle(vision, wealth, lifeExpectancy, metabolism, age, patch);
    }

    // ------------------------------------------------------------------------
    // Method: To update turtles
    // ------------------------------------------------------------------------
    public static void updateTurtle(Turtle t) {
        // generate random parameters.
        int metabolism = Params.getRandomMetabolism();
        int wealth = Params.getRandomWealth(metabolism);
        int vision = Params.getRandomVision();
        int lifeExpectancy = Params.getRandomLifeExpectancy();
        int age = Params.getRandomAge(lifeExpectancy);
        t.setMetabolism(metabolism);
        t.setWealth(wealth);
        t.setVision(vision);
        t.setLifeExpectancy(lifeExpectancy);
        t.setAge(age);
    }

    // ------------------------------------------------------------------------
    // Method: To update a turtle's location
    // ------------------------------------------------------------------------
    public static void updateTurtleLocation(Turtle t, Field field) {
        // Randomly chosen Patch in whole grid
        if (Params.N_LOC_GRID == -1) {
            Patch currPatch = t.getCurrLocation();
            currPatch.setNTurtles(currPatch.getNTurtles() - 1);

            Patch newPatch = field.getRandomPatch();
            t.setCurrLocation(newPatch);
            newPatch.setNTurtles(newPatch.getNTurtles() + 1);
        }
        else {
            Patch currPatch = t.getCurrLocation();
            currPatch.setNTurtles(currPatch.getNTurtles() - 1);

            int currRow = currPatch.getRow();
            int currCol = currPatch.getCol();
            int newRow = Params.getRandomNewRow(currRow);
            int newCol = Params.getRandomNewCol(currCol);

            Patch newPatch = field.getPatch(newRow, newCol);
            t.setCurrLocation(field.getPatch(newRow, newCol));
            newPatch.setNTurtles(newPatch.getNTurtles() + 1);
        }
    }

    // ------------------------------------------------------------------------
    // Method: To recolour the turtles.
    // ------------------------------------------------------------------------
    public static void recolorTurtles(Turtle[] turtles) {
        int topWealth = 0;
        // Find out the maximum wealth any turtle has
        for (Turtle t : turtles) {
            if (t.getWealth() > topWealth) {
                topWealth = t.getWealth();
            }
        }
        // Calculate colors, we use doubles to ensure we don't get rounding
        // problems.
        for (Turtle t : turtles) {
            if (t.getWealth() <= topWealth/3.0) {
                t.setColor('r');
            }
            else if (t.getWealth() <= topWealth*2.0/3.0) {
                t.setColor('g');
            }
            else {
                t.setColor('b');
            }
        }
    }

    // ------------------------------------------------------------------------
    // Method: To track the colors in the model. This method is used to count
    // the different colors and return an array list, which would be used to 
    // generate a row in the CSV.
    // ------------------------------------------------------------------------
    public static List<String> trackColors(Turtle [] turtles, int tick) {
        int red = 0;
        int green = 0;
        int blue = 0;
        // Count the number of r, g and b.
        for (Turtle turtle : turtles) {
            if (turtle.getColor() == 'r') {
                red++;
            }
            if (turtle.getColor() == 'g') {
                green++;
            }
            if (turtle.getColor() == 'b') {
                blue++;
            }
        }
        double redPercentage = (double) red/turtles.length * 100;
        double greenPercentage = (double) green/turtles.length * 100;
        double bluePercentage = (double) blue/turtles.length * 100; 
        DecimalFormat df = new DecimalFormat("#.00");
        return Arrays.asList(String.valueOf(tick), 
            String.valueOf(df.format(redPercentage)), 
            String.valueOf(df.format(greenPercentage)),
            String.valueOf(df.format(bluePercentage)));
    }
 
    // ------------------------------------------------------------------------
    // Method: To update the Lorenz points and Gini index. This method is used 
    // to calculate the Lorenz Points and the Gini index  and return an array 
    // list, which would be used to generate a row in the CSV.
    // ------------------------------------------------------------------------
    public static List<String> updateLorenzAndGini(Turtle[] turtles, int tick) {
        Turtle[] sortedTurtles = turtles.clone();

        Arrays.sort(sortedTurtles, Turtle::compare);

        int totalWealth = 0;
        for (Turtle sortedTurtle : sortedTurtles) {
            totalWealth += sortedTurtle.getWealth();
        }

        int wealthSumSoFar = 0;
        double giniIndexReserve = 0;
        ArrayList<Double> lorenzPoint = new ArrayList<>();

        double giniIndex;

        for (int i=0; i<sortedTurtles.length; i++) {
            wealthSumSoFar = wealthSumSoFar + sortedTurtles[i].getWealth();
            lorenzPoint.add(((double) wealthSumSoFar / totalWealth) * 100);
            giniIndexReserve = giniIndexReserve + ((double)i/(double)sortedTurtles.length) - ((double) wealthSumSoFar / (double)totalWealth);
        }
        giniIndex = (giniIndexReserve / (double) sortedTurtles.length) / 0.5;
        System.out.println(giniIndex);

        double first20 = lorenzPoint.get((int)((lorenzPoint.size()-1)*0.2));
        double first40 = lorenzPoint.get((int)((lorenzPoint.size()-1)*0.4));
        double first60 = lorenzPoint.get((int)((lorenzPoint.size()-1)*0.6));
        double first80 = lorenzPoint.get((int)((lorenzPoint.size()-1)*0.8));
        double first100 = lorenzPoint.get(((lorenzPoint.size() - 1)));

        DecimalFormat df = new DecimalFormat("#.00");


        return Arrays.asList(String.valueOf(tick), String.valueOf(df.format(first20)),
                String.valueOf(df.format(first40)), String.valueOf(df.format(first60)),
                String.valueOf(df.format(first80)), String.valueOf(df.format(first100)),
                String.valueOf(df.format(giniIndex)));
    }

    // ------------------------------------------------------------------------
    // Method: To create the CSV file for the color percentage.
    // ------------------------------------------------------------------------
    public static void createCSVColors(String filename, String heading1, 
        String heading2, String heading3, 
        String heading4, List<List<String>> data)
    {
        try {
            FileWriter csvWriter = new FileWriter(filename);
            csvWriter.append(heading1);
            csvWriter.append(",");
            csvWriter.append(heading2);
            csvWriter.append(",");
            csvWriter.append(heading3);
            csvWriter.append(",");
            csvWriter.append(heading4);
            csvWriter.append("\n");

            for (List<String> rowData : data) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (Exception ignored) {

        }
    }

    // ------------------------------------------------------------------------
    // Method: To create the CSV file for the Lorenz points and gini index.
    // ------------------------------------------------------------------------
    public static void createCSVLorenz(List<List<String>> data) {
        try {
            FileWriter csvWriter = new FileWriter("lorenz.csv");
            csvWriter.append("tick");
            csvWriter.append(",");
            csvWriter.append("bottom 20%");
            csvWriter.append(",");
            csvWriter.append("bottom 40%");
            csvWriter.append(",");
            csvWriter.append("bottom 60%");
            csvWriter.append(",");
            csvWriter.append("bottom 80%");
            csvWriter.append(",");
            csvWriter.append("bottom 100%");
            csvWriter.append(",");
            csvWriter.append("Gini");
            csvWriter.append("\n");
 
            for (List<String> rowData : data) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (Exception ignored) {

        }
    }
 
    // ------------------------------------------------------------------------
    // Method: To turn the Turtle towards the grain.
    // ------------------------------------------------------------------------
    public static void turnTowardsGrain(Turtle t, Field field) {
        t.setHeading(0);
        int bestDirection = 0;
        int bestAmount = grainAhead(t, field);

        t.setHeading(90);
        if (grainAhead(t, field) > bestAmount) {
            bestDirection = 90;
            bestAmount = grainAhead(t, field);
        }

        t.setHeading(180);
        if (grainAhead(t, field) > bestAmount) {
            bestDirection = 180;
            bestAmount = grainAhead(t, field);
        }

        t.setHeading(270);
        if (grainAhead(t, field) > bestAmount) {
            bestDirection = 270;
        }

        t.setHeading(bestDirection);
    }
    
    // ------------------------------------------------------------------------
    // Method: To check the grains in the Patches ahead.
    // ------------------------------------------------------------------------
    public static int grainAhead(Turtle t, Field field) {
        int tPatchRow = t.getCurrLocation().getRow();
        int tPatchCol = t.getCurrLocation().getCol(); 
        int tHeading = t.getHeading();
        int grainTotal = 0;
        for (int i = 0; i < t.getVision(); i++) {
            switch (tHeading) {
                case 0 -> tPatchCol--;
                case 90 -> tPatchRow++;
                case 180 -> tPatchCol++;
                case 270 -> tPatchRow--;
            }
            if (tPatchCol < 0) {
                tPatchCol = Params.FIELD_HEIGHT - 1;
            }
            if (tPatchCol == Params.FIELD_HEIGHT) {
                tPatchCol = 0;
            }
            if (tPatchRow < 0) {
                tPatchRow = Params.FIELD_WIDTH - 1;
            }
            if (tPatchRow == Params.FIELD_WIDTH) {
                tPatchRow = 0;
            }
            grainTotal += field.getPatch(tPatchRow, tPatchCol).getCurrGrain();
        }
        return grainTotal;
    }

    // ------------------------------------------------------------------------
    // Method: To harvesr grain.
    // ------------------------------------------------------------------------
     public static void harvest(Turtle[] turtles) {
         int newWealth;
         Patch patch;
         for (Turtle t : turtles) {
             patch = t.getCurrLocation();
             newWealth = (int) (t.getWealth() + 
                         (patch.getCurrGrain()/patch.getNTurtles()));
             t.setWealth(newWealth);
         }
         for (Turtle t : turtles) {
             patch = t.getCurrLocation();
             patch.setCurrGrain(0);
         }
     }

    // ------------------------------------------------------------------------
    // Method: To do the process of move -> eat -> die for the turtles.
    // ------------------------------------------------------------------------
    public static void moveEatAgeDie(Turtle t, Field field) {
        moveTurtle(t, field);
        // consume some grain according to metabolism
        t.setWealth(t.getWealth() - t.getMetabolism());
        // grow older
        t.setAge(t.getAge() + 1);
        // check for death conditions: if you have no grain or
        // you're older than the life expectancy or if some random factor
        // holds, then you "die" and are "reborn" (in fact, your variables
        // are just reset to new random values
        if ((t.getWealth() < 0) || (t.getAge() >= t.getLifeExpectancy())) {
            updateTurtle(t);
            updateTurtleLocation(t, field);
        }
    }

    // ------------------------------------------------------------------------
    // Method: To move the turtle to a random Patch in the Field.
    // ------------------------------------------------------------------------
    public static void moveTurtle(Turtle t, Field field) {
        int tPatchRow = t.getCurrLocation().getRow();
        int tPatchCol = t.getCurrLocation().getCol();
        int tHeading = t.getHeading();

        Patch oldPatch = field.getPatch(tPatchRow, tPatchCol);

        oldPatch.setNTurtles(oldPatch.getNTurtles() - 1);

        switch (tHeading) {
            case 0 -> {
                tPatchCol++;
            }
            case 90 -> {
                tPatchRow++;
            }
            case 180 -> {
                tPatchCol--;
            }
            case 270 -> {
                tPatchRow--;
            }
        }
        if (tPatchCol < 0) {
            tPatchCol = Params.FIELD_HEIGHT - 1;
        }
        if (tPatchCol == Params.FIELD_HEIGHT) {
            tPatchCol = 0;
        }
        if (tPatchRow < 0) {
            tPatchRow = Params.FIELD_WIDTH - 1;
        }
        if (tPatchRow == Params.FIELD_WIDTH) {
            tPatchRow = 0;
        }

        Patch newPatch = field.getPatch(tPatchRow, tPatchCol);
        // move turtle
        t.setCurrLocation(field.getPatch(tPatchRow, tPatchCol));
        newPatch.setNTurtles(newPatch.getNTurtles() + 1);

    }
 
}