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
 *  ============================================================================
 *  Description of the object properties:
 *  ============================================================================
 *
 *      - nGrain:
 *          This is a "int" that holds the current number of grain in the Patch.
 *      - maxGrain:
 *          This is an "int" variable that holds the max number of grains the
 *          Patch can hold.
 *      - row, col:
 *          These are "int" variables that holds the position of the Patch in 
 *          the Field.
 *       - nTurtles:
 *          This is an "int" variable that holds the number of turtles on the
 *          Patch.
 *
 *  ============================================================================
 *  Detailed description of Methods:
 *  ============================================================================
 *  This class has two methods:
 *      1. grow():
 *          Replenish the amount of grain on the patch.
 *      2. harvest():
 *           The patch is harvested by a turtle.
 * 
 *  This class has getters and setter methods.
 */


public class Patch {
    // =========================================================================
    // Class Properties
    // =========================================================================
    private double nGrain;
    private int maxGrain;
    private int row, col;
    private int nTurtles;
 

    // =========================================================================
    // Constructor: Creates a new patch for the given values for grain
    // and max grain.
    // =========================================================================
    Patch (double nGrain, int maxGrain, int row, int col) {
        // Initialising all the properties here.
        this.nGrain   = nGrain;
        this.maxGrain = maxGrain;
        this.row      = row;
        this.col      = col;
        this.nTurtles = 0;
    }

    // =========================================================================
    // Method: Replenish the amount of grain on the patch
    // =========================================================================
    public void grow() {
        if (this.nGrain < this.maxGrain) {
            this.nGrain += Params.NUM_GRAIN_GROWN;
        } else {
            this.nGrain = this.maxGrain;
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

    public int getRow() { return this.row; }

    public int getCol() { return this.col; }

    public int getNTurtles() { return this.nTurtles; }

    public void setCurrGrain(double nGrain) {
        this.nGrain = nGrain;
    }

    public void setMaxGrain(int maxGrain) {
        this.maxGrain = maxGrain;
    }

    public void setRow(int row) { this.row = row; }

    public void setCol(int col) { this.col = col; }

    public void setNTurtles(int nTurtles) { this.nTurtles = nTurtles; }
}