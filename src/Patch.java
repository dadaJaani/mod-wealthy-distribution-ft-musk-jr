/**
 *  Patch.java
 *
 *  This is the Patch class that represents a Patch in the simulator.
 *  It is a PROCESS as it extends Thread.
 *
 *  ============================================================================
 *  @authorName     : Waqas Rehmani, Angus Hudson, Jonathan Dunne
 *  @studentNumber  : 1035514
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
 *  This class has seven methods:
 *      1. run():
 *          This is the run method to run the process. It follows the procedure
 *          stated above.
 *
 *      2. grow():
 *          This public method removes the current Quest for the Knight.
 * 
 */


public class Turtle extends Thread{
    // =========================================================================
    // Class Properties
    // =========================================================================
    private int nGrain, maxGrain;
    private Field field;
 

    // =========================================================================
    // Constructor: Creates a new turtle for the given vision, lifeExpectancy
    // and metabolism.
    // =========================================================================
    Turtle (int maxGrain, Field field) {
        // Initialising all the properties here.
        this.nGrain   = 0;
        this.maxGrain = 0;
        this.field    = 0; 
    }

    // =========================================================================
    // Method: To RUN the process.
    // =========================================================================
    public void run() {
        while (!isInterrupted()) {
            // Procedure 1: Grow 
            grow();
 
            // Procedure 2: Die
        }
    }

    // =========================================================================
    // Method: To move to a new location.
    // =========================================================================
    public void grow() { 
        // I guess this updates the currLocation
        if (this.nGrain < this.maxGrain) { // ??
            this.nGrain ++; 
        }
    }
 
    // =========================================================================
    // Method: To die.
    // =========================================================================
    public void die() {
        this.nGrain = 0; // ??
    }

}