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
import java.text.DecimalFormat;

public class Main {

    public static void main(String [] args) throws InterruptedException {
        Turtle[] turtles = new Turtle[Params.NUM_TURTLES];
        Field field = new Field(Params.FIELD_WIDTH, Params.FIELD_HEIGHT);
        initialiseTurtles(turtles, field);
        runSimulation(turtles, field);
    }

    public static void initialiseTurtles(Turtle[] turtles, Field field) {
        for (int i = 0; i < Params.NUM_TURTLES; i++) {
            // set turtle initial location
            Patch patch = field.getRandomPatch();
            turtles[i] = initialiseTurtle(patch);
            patch.setNTurtles(patch.getNTurtles() + 1);
        }
        recolorTurtles(turtles);
    }

    public static Turtle initialiseTurtle(Patch patch) {
        // generate random parameters.
        int metabolism = Params.getRandomMetabolism();
        int wealth = Params.getRandomWealth(metabolism);
        int vision = Params.getRandomVision();
        int lifeExpectancy = Params.getRandomLifeExpectancy();
        int age = Params.getRandomAge(lifeExpectancy);

        return new Turtle(vision, wealth, lifeExpectancy, metabolism, age, patch);

    }

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

    public static void printDistribution(Turtle[] turtles) {
        String s;
        for (Turtle t : turtles) {
            s = String.format("%c", t.getColor());
            System.out.print(s);
        }
        System.out.print("\n");
    }

    public static void recolorTurtles(Turtle[] turtles) {
        int topWealth = 0;
        // Find out the maximum wealth any turtle has
        for (Turtle t : turtles) {
            if (t.getWealth() > topWealth) {
                topWealth = t.getWealth();
            }
        }
        // Calculate colors, we use doubles to ensure we don't get rounding problems
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

    public static void runSimulation(Turtle[] turtles, Field field) {
        int ticks = 0;

        
        List<List<String>> dataRows = new ArrayList<List<String>>();
        // Arrays.asList(
            // Arrays.asList("Jack", "Sailor", "0340138128"),
            // Arrays.asList("Bond", "Spy", "0467263982"),
            // Arrays.asList("Harry", "Wizard", "11111100232")
        // );

        
        while (ticks < Params.N_TICKS) {
            for (Turtle t : turtles) {
                turnTowardsGrain(t, field);
            }
            harvest(turtles);
            for (Turtle t : turtles) {
                moveEatAgeDie(t, field);
            }
            recolorTurtles(turtles);
            printDistribution(turtles);

            if (ticks % Params.GRAIN_GROWTH_INTERVAL == 0) {
                field.growPatches();
            }
            // updateLorenzAndGini();
            
            // dataRows.add(Arrays.asList("ticks", "Jack", "Sailor", "0340138128"));
            dataRows.add(trackColors(turtles, ticks));
            ticks++;
        }

        createCSV("colorPercentage.csv", "ticks", "red%", "green%", "blue%", dataRows); 

    }

    public static List<String> trackColors(Turtle [] turtles, int tick) {
        int red = 0;
        int green = 0;
        int blue = 0;
        // Count the number of r, g and b.
        for (int i=0; i<turtles.length; i++) {
            if(turtles[i].getColor() == 'r'){
                red++;
            }
            if(turtles[i].getColor() == 'g'){
                green++;
            }
            if(turtles[i].getColor() == 'b'){
                blue++;
            }
        }
        double redPercentage = (double) red/turtles.length * 100;
        double greenPercentage = (double) green/turtles.length * 100;
        double bluePercentage = (double) blue/turtles.length * 100; 

        DecimalFormat df = new DecimalFormat("#.00");
        // String angleFormated = df.format(angle);

        return Arrays.asList(String.valueOf(tick), String.valueOf(df.format(redPercentage)), String.valueOf(df.format(greenPercentage)), String.valueOf(df.format(bluePercentage)));
    }

    public static void createCSV(String filename, String heading1, String heading2, String heading3, String heading4, List<List<String>> data) {
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
        } catch (Exception e) {

        }
    }

    // =========================================================================
    // Method: To turn the Turtle towards the grain.
    // =========================================================================
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


    // =========================================================================
    // Method: To check the grains in the Patches ahead.
    // =========================================================================
    public static int grainAhead(Turtle t, Field field) {
        int tPatchRow = t.getCurrLocation().getRow();
        int tPatchCol = t.getCurrLocation().getCol(); 
        int tHeading = t.getHeading();
        int grainTotal = 0;
        for (int i = 0; i < t.getVision(); i++) {
            switch (tHeading) {
                case 0:
                    tPatchCol--;
                    break;
                case 90:
                    tPatchRow++;
                    break;
                case 180:
                    tPatchCol++;
                    break;
                case 270:
                    tPatchRow--;
                    break;
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

     public static void harvest(Turtle[] turtles) {
         int newWealth;
         Patch patch;
         for (Turtle t : turtles) {
             patch = t.getCurrLocation();
             newWealth = (int) (t.getWealth() + (patch.getCurrGrain()/patch.getNTurtles()));
             t.setWealth(newWealth);
         }
         for (Turtle t : turtles) {
             patch = t.getCurrLocation();
             patch.setCurrGrain(0);
         }
     }

    public static void moveEatAgeDie(Turtle t, Field field) {
        moveTurtle(t, field);

        // consume some grain according to metabolism
        t.setWealth(t.getWealth() - t.getMetabolism());

        // grow older
        t.setAge(t.getAge() + 1);

        //   check for death conditions: if you have no grain or
        //   you're older than the life expectancy or if some random factor
        //   holds, then you "die" and are "reborn" (in fact, your variables
        //   are just reset to new random values
        if ((t.getWealth() < 0) || (t.getAge() >= t.getLifeExpectancy())) {
            updateTurtle(t);
        }
        return;
    }

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

    public static void updateLorenzAndGini() {
        return;
    }
}