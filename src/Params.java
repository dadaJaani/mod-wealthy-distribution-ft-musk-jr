/**
 *  Params.java
 *
 *  Parameters for the questing knights simulator.
 *
 *  ============================================================================
 *  @authorName     : Waqas Rehmani
 *  @studentNumber  : 1035514
 *  ============================================================================
 *
 */

import java.util.Random;
import java.lang.Math;

class Params {
	
    static Random rnd = new Random();
    
    // The height of the field in patches
    static final int FIELD_HEIGHT = 51;

    // The width of the field in patches
    static final int FIELD_WIDTH = 51;
	
	// The number of turtles in the simulator
    static final int NUM_TURTLES = 250;

    // The distance a turtle can see when looking for grain
    static final int MAX_VISION = 5;

    // The maximum metabolism for a turtle
    static final int MAX_METABOLISM = 15;

    // The minimum life expectancy of a turtle
    static final int MIN_LIFE_EXPECTANCY = 1;

    // Maximum life expectancy of a turtle
    static final int MAX_LIFE_EXPECTANCY = 83; 

    // The percentage of best land
    static final int PERCENT_BEST_LAND = 10;

    // The time interval for grain growth
    static final int GRAIN_GROWTH_INTERVAL = 1;

    // The amount of grain grown per growth tick
    static final int NUM_GRAIN_GROWN = 4;

    // The maximum starting wealth
    static final int MAX_WEALTH = 50;
    
    // Generate a random life expectancy
    static int getLifeExpectancy() {
        return (int) (MIN_LIFE_EXPECTANCY + 
            (Math.random() * (MAX_LIFE_EXPECTANCY - MIN_LIFE_EXPECTANCY)));
    }

    // Generate a random life expectancy
    static int getRandomLifeExpectancy() {
        return (int) (MIN_LIFE_EXPECTANCY + 
            (Math.random() * (MAX_LIFE_EXPECTANCY - MIN_LIFE_EXPECTANCY + 1)));
    }

    // Generate a random metabolism
    static int getRandomMetabolism() {
        return (int) (1 + (Math.random() * MAX_METABOLISM));
    }

    // Generate a random vision
    static int getRandomVision() {
        return (int) (1 + (Math.random() * MAX_VISION));
    }

    static int getRandomWealth(int metabolism) {
        return (int) (metabolism + (Math.random() * MAX_WEALTH));
    }
}
