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
 *      2. grow():
 *          This public method removes the current Quest for the Knight.
 * 
 */


public class Patch {
    // =========================================================================
    // Class Properties
    // =========================================================================
    private double nGrain;
    private int maxGrain;
    private Field field;
 

    // =========================================================================
    // Constructor: Creates a new patch for the given values for grain
    // and max grain.
    // =========================================================================
    Patch (double nGrain, int maxGrain, Field field) {
        // Initialising all the properties here.
        this.nGrain   = nGrain;
        this.maxGrain = maxGrain;
        this.field    = field; 
    }

    // =========================================================================
    // Method: Replenish the amount of grain on the patch
    // =========================================================================
    public void grow() { 
        if (this.nGrain < this.maxGrain) {
            this.nGrain += Params.NUM_GRAIN_GROWN; 
            if (this.nGrain > this.maxGrain) {
                this.nGrain = this.maxGrain;
            }
        }
    }
 
    // =========================================================================
    // Method: The patch is harvested by a turtle.
    // not sure we need this (see above)
    // =========================================================================
    public void harvest() {
        this.nGrain = 0;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public double getCurrGrain() {
        return this.nGrain;
    }

    public int getMaxGrain() {
        return this.maxGrain;
    }

    public void setCurrGrain(double nGrain) {
        this.nGrain = nGrain;
    }

    public void setMaxGrain(int maxGrain) {
        this.maxGrain = maxGrain;
    }
}