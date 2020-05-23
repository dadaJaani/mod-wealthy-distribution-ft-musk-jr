/**
 *  Params.java
 *
 *  Parameters for the wealth distribution simulator.
 *
 *  ============================================================================
 *  @authorName     : Waqas Rehmani, Angus Hudson, Jonathan Dunne
 *  @studentNumber  : 1035514, 835808, 836748
 *  ============================================================================
 *
 */

import java.util.Random;
import java.lang.Math;

class Params {
	
    static Random rnd = new Random();

    // First diffusion round
    static final int N_FIRST_DIFFUSIONS = 5;

    // Second diffusion round
    static final int N_SECOND_DIFFUSIONS = 10;

    // Diffusion rate
    static final double DIFFUSION_RATE = 0.25;
    
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

    // The maximum amount of grain per patch
    static final int MAX_GRAIN = 50;

    // The maximum starting wealth
    static final int MAX_WEALTH = 50;

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

    // Generate a random wealth
    static int getRandomWealth(int metabolism) {
        return (int) (metabolism + (Math.random() * MAX_WEALTH));
    }

    // Generate an integer between 1 and 100
    static int getRandomPercentage() {
        return (int) (1 + (Math.random() * 100.0));
    }
}
