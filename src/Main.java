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

        Patch[][] patches = new Patch[Params.FIELD_HEIGHT][Params.FIELD_WIDTH];
        Turtle[] turtles = new Turtle[Params.NUM_TURTLES];

        initialisePatches(patches);

        initialiseTurtles(turtles, patches);

        runSimulation(patches, turtles);

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

    public static void initialisePatches(Patch[][] patches) {
        // Initialise patches to be empty with 0 maxin, unless they are 'rich'
        int maxGrain;
        int nGrain;
        for (int i = 0; i < Params.FIELD_HEIGHT; i++) {
            for (int j = 0; j < Params.FIELD_WIDTH; j++) {
                maxGrain = nGrain = 0;
                if (Params.getRandomPercentage() <= Params.PERCENT_BEST_LAND) {
                    maxGrain = nGrain = Params.MAX_GRAIN; // 'Rich' patches
                }
                patches[i][j] = new Patch(nGrain, maxGrain, i, j);
            }
        }

        printGrain(patches);

        for (int i = 0; i < Params.N_FIRST_DIFFUSIONS; i++) {
            printIterationHeader(i+1);
            fillRichPatches(patches);
            printGrain(patches);
            diffusePatches(patches);
            printGrain(patches);
        }

        for (int i = 0; i < Params.N_SECOND_DIFFUSIONS; i++) {
            printIterationHeader(Params.N_FIRST_DIFFUSIONS + i + 1);
            diffusePatches(patches);
            printGrain(patches);
        }

        for (int row = 0; row < Params.FIELD_HEIGHT; row++) {
            for (int col = 0; col < Params.FIELD_WIDTH; col++) {
                patches[row][col].setCurrGrain((int) patches[row][col].getCurrGrain());
                patches[row][col].setMaxGrain((int) patches[row][col].getCurrGrain());
                printPatch(patches[row][col], row+1, col+1);
            }
        }
    }

    public static void initialiseTurtles(Turtle[] turtles, Patch[][] patches) {
        for (int i = 0; i < Params.NUM_TURTLES; i++) {
            
            // set turtle initial location
            int patchRow = Params.getRandomRow();
            int patchWidth = Params.getRandomCol();
            
            turtles[i] = initialiseTurtle(patches[patchRow][patchWidth]);
        }

        recolorTurtles(turtles);
    }

    public static Turtle initialiseTurtle(Patch patch ){

        // generate random parameters.
        int metabolism = Params.getRandomMetabolism();
        int wealth = Params.getRandomWealth(metabolism);
        int vision = Params.getRandomVision();
        int lifeExpectancy = Params.getRandomLifeExpectancy();
        int age = Params.getRandomAge(lifeExpectancy);
        
        System.out.print("turtle b i r t h e d");
        return new Turtle(vision, wealth, lifeExpectancy, metabolism, age, patch);

    }

    // 1. Give all 8 neighbouring patches of patches with grain on them, 1/8th of 25% of the grain present on the initial patch,
    //          the initial patch keeps any grain not allocated to neighbouring patches (sum = 200%),
    //          we re-max all 'rich' patches at start of every cycle SEE: http://ccl.northwestern.edu/netlogo/docs/dict/diffuse.html
    // 2. Do this 5 times
    // 3. Do this a further 10 times, except we don't replenish the 'rich' patches
    // 4. After, round all grain values for each patch down to nearest int, and set max for that patch to be that value
    public static void diffusePatches(Patch[][] patches) {
        // We use a separate grid so the patches aren't updated until ALL have been processed for diffusion
        double[][] transitionGrid = new double[Params.FIELD_HEIGHT][Params.FIELD_WIDTH];
        int left, right, up, down;
        for (int row = 0; row < Params.FIELD_HEIGHT; row++) {
            for (int col = 0; col < Params.FIELD_WIDTH; col++) {
                up = row - 1;
                down = row + 1;
                left = col - 1;
                right = col + 1;
                // The board wraps horizontally and vertically
                if (up < 0) {
                    up = Params.FIELD_HEIGHT - 1;
                }
                if (down == Params.FIELD_HEIGHT) {
                    down = 0;
                }
                if (left < 0) {
                    left = Params.FIELD_WIDTH - 1;
                }
                if (right == Params.FIELD_WIDTH) {
                    right = 0;
                }
                // Now update all transitions
                double diffuse = Params.DIFFUSION_RATE * (1.0/8) * patches[row][col].getCurrGrain();

                transitionGrid[up][left] += diffuse;
                transitionGrid[up][col] += diffuse;
                transitionGrid[up][right] += diffuse;

                transitionGrid[row][left] += diffuse;
                transitionGrid[row][right] += diffuse;

                transitionGrid[down][left] += diffuse;
                transitionGrid[down][col] += diffuse;
                transitionGrid[down][right] += diffuse;

                transitionGrid[row][col] -= 8 * diffuse;
            }
        }

        for (int row = 0; row < Params.FIELD_HEIGHT; row++) {
            for (int col = 0; col < Params.FIELD_WIDTH; col++) {
                patches[row][col].setCurrGrain(patches[row][col].getCurrGrain() + transitionGrid[row][col]);
            }
        }
    }

    public static void fillRichPatches(Patch[][] patches) {
        for (int row = 0; row < Params.FIELD_WIDTH; row++) {
            for (int col = 0; col < Params.FIELD_HEIGHT; col++) {
                if (patches[row][col].getMaxGrain() != 0) {
                    patches[row][col].setCurrGrain(patches[row][col].getMaxGrain());;
                }
            }
        }
    }

    public static void printGrain(Patch[][] patches) {
        String s;
        for (int row = 0; row < Params.FIELD_HEIGHT; row++) {
            for (int col = 0; col < Params.FIELD_WIDTH; col++) {
                s = String.format("%5.2f ", patches[row][col].getCurrGrain());
                System.out.print(s);
                if (col == Params.FIELD_WIDTH-1) {
                    System.out.print("\n");
                }
            }
        }
        System.out.print("\n");
    }

    public static void printIterationHeader(int x) {
        String s;
        s = String.format("=== ITERATION %d ===\n\n", x);
        System.out.print(s);
    }

    public static void printPatch(Patch p, int row, int col) {
        String s;
        s = String.format("PATCH Row: %2d Col: %2d\n" +
                "Curr Grain:\t\t%3.1f\nMax Grain:\t\t%3d\n\n", row, col,
                p.getCurrGrain(), p.getMaxGrain());
        System.out.print(s);
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

    public static void runSimulation(Patch[][] patches, Turtle[] turtles) {
        int ticks = 0;
        while (true) {
            for (Turtle t : turtles) {
                turnTowardsGrain(t);
            }
            harvest();
            for (Turtle t : turtles) {
                moveEatAgeDie(t);
            }
            recolorTurtles(turtles);

            if (ticks % Params.GRAIN_GROWTH_INTERVAL == 0) {
                for (Patch[] patchRow : patches) {
                    for (Patch p : patchRow) {
                        growGrain(p);
                    }
                }
            }
            updateLorenzAndGini();
            ticks++;
        }
    }

    // =========================================================================
    // Method: To turn the Turtle towards the grain.
    // =========================================================================
    public static void turnTowardsGrain(Turtle t) {
        t.setHeading(0);
        int bestDirection = 0;
        int bestAmount = grainAhead(t);

        t.setHeading(90);
        if (grainAhead(t) > bestAmount) {
            bestDirection = 90;
            bestAmount = grainAhead(t);
        }

        t.setHeading(180);
        if (grainAhead(t) > bestAmount) {
            bestDirection = 180;
            bestAmount = grainAhead(t);
        }

        t.setHeading(270);
        if (grainAhead(t) > bestAmount) {
            bestDirection = 270;
        }

        t.setHeading(bestDirection);
    }


    // =========================================================================
    // Method: To check the grains in the Patches ahead.
    // =========================================================================
    public static int grainAhead(Turtle t) {
        int total = 0;
        for (int i=0; i<t.getVision(); i++) {
            // total increment with nextPatch
            total = total + 1;
        }
        return total;
    }

    public static void harvest() {
        return;
    }

    public static void moveEatAgeDie(Turtle t) {
        // consume some grain according to metabolism
        t.setWealth(t.getWealth() - t.getMetabolism());
        System.out.print("turtle eat");

        // grow older
        t.setAge(t.getAge() + 1);
        System.out.print("turtle age");

        //   check for death conditions: if you have no grain or
        //   you're older than the life expectancy or if some random factor
        //   holds, then you "die" and are "reborn" (in fact, your variables
        //   are just reset to new random values
        if ((t.getWealth() < 0) || (t.getAge() >= t.getLifeExpectancy())){
            System.out.print("turtle d e d");
            t = initialiseTurtle(t.getCurrLocation());
        }

        return;
    }

// to move-eat-age-die  ;; turtle procedure
//   fd 1
//   ;; consume some grain according to metabolism
//   set wealth (wealth - metabolism)
//   ;; grow older
//   set age (age + 1)
//   ;; check for death conditions: if you have no grain or
//   ;; you're older than the life expectancy or if some random factor
//   ;; holds, then you "die" and are "reborn" (in fact, your variables
//   ;; are just reset to new random values)
//   if (wealth < 0) or (age >= life-expectancy)
//     [ set-initial-turtle-vars ]
// end

    public static void growGrain(Patch p) {
        return;
    }

    public static void updateLorenzAndGini() {
        return;
    }
}