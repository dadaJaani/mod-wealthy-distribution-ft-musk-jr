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
	
	// Number of knights in the simulator.
    static final int NUM_TURTLES = 4;

    // Average duration that knights spend mingling before and after meetings.
    static final int LIFE_EXPECTENCY = 50; 
    
    // Generate a random mingling duration.
    static int getLifeExpectency() {
        return (int) Math.max(0.0, rnd.nextGaussian() * 
        		LIFE_EXPECTENCY / 6 + LIFE_EXPECTENCY);
    }


}
