/**
 *  Patch.java
 *
 *  This is the Patch class that represents a Patch in the simulator.
 *  It is a PROCESS as it extends Thread.
 *
 *  ============================================================================
 *  @authorName     : Waqas Rehmani, Angus Hudson, Jonathan Dunne
 *  @studentNumber  : 1035514, 835808, 836748
 *  ============================================================================
 *
 *  A Patch has the following procedures:
 *      1. Grow.
 *      2. Die??
 *
 *  ============================================================================
 *  Description of the object properties:
 *  ============================================================================
 *
 *      - nGrain:
 *          This is a "int" that holds the current number of grain in the Patch.
 *      - maxGrain:
 *          This is an "int" variable that holds the max number of grains the
 *          Patch can hold.
 *
 *  ============================================================================
 *  Detailed description of Methods:
 *  ============================================================================
 *  This class has two methods:
 *      1. run():
 *          This is the run method to run the process. It follows the procedure
 *          stated above.
 *
 *      2. grow():
 *          This public method removes the current Quest for the Knight.
 * 
 */


public class Patch extends Thread {
    // =========================================================================
    // Class Properties
    // =========================================================================
    private int nGrain, maxGrain;
    private Field field;
 

    // =========================================================================
    // Constructor: Creates a new turtle for the given vision, lifeExpectancy
    // and metabolism.
    // =========================================================================
    Patch (int nGrain, int maxGrain, Field field) {
        // Initialising all the properties here.
        this.nGrain   = nGrain;
        this.maxGrain = maxGrain;
        this.field    = field; 
    }

    // =========================================================================
    // Method: To RUN the process.
    // =========================================================================
    public void run() {
        while (!isInterrupted()) {
            // Procedure 1: Grow 
            grow();

            // Procedure 2: Harvest
            harvest();
        }
    }

    // =========================================================================
    // Method: To move to a new location.
    // =========================================================================
    public void grow() { 
        // I guess this updates the currLocation
        if (this.nGrain < this.maxGrain) {
            this.nGrain += Params.NUM_GRAIN_GROWN; 
            if (this.nGrain > this.maxGrain) {
                this.nGrain = this.maxGrain;
            }
        }
    }
 
    // =========================================================================
    // Method: The patch is harvested by a turtle.
    // =========================================================================
    public void harvest() {
        this.nGrain = 0;
    }

}