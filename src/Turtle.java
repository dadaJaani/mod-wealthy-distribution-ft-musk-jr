/**
 *  Turtle.java
 *
 *  This is the Turtle class that represents a Turtle in the simulator.
 *  It is a PROCESS as it extends Thread.
 *
 *  ============================================================================
 *  @authorName     : Waqas Rehmani, Angus Hudson, Jonathan Dunne
 *  @studentNumber  : 1035514
 *  ============================================================================
 *
 *  A Turtle has the following procedures:
 *      1. Move.
 *      2. Eat.
 *      3. Age.
 *      4. Die.
 *
 *  ============================================================================
 *  Description of the object properties:
 *  ============================================================================
 *
 *      - currLocation:
 *          This is a "Patch" that holds the current location of the Turtle.
 *      - vision:
 *          This is an "int" variable that holds the 'strength' of the vision
 *          for the Turtle.
 *      - currWealth:
 *          This is an "int" variable that holds the current wealth for the 
 *           Turtle.
 *      - lifeExpectancy:
 *          This is a "int" variable that holds the extpected lifespan for the 
 *          Turtle.
 *      - age:
 *          This is a "int" variable that holds the age of the Turtle.
 *      - metabolism:
 *          This is a "int" variable that holds the metabolism rate for the 
 *          Turtle.
 *      - ticksToNextEat:
 *          This is a "int" variable that holds the number of ticks remaining 
 *          until the Turtle can eat again.
 *      - field:
 *          This is a "Field" variable that represents the field the turtle is
 *          on.
 *
 *  ============================================================================
 *  Detailed description of Methods:
 *  ============================================================================
 *  This class has seven methods:
 *      1. run():
 *          This is the run method to run the process. It follows the procedure
 *          stated above.
 *
 *      2. moveToLocation():
 *          This public method removes the current Quest for the Knight.
 *
 *      3. harvest(Patch):
 *          This is a public get method to access the Knight's Quest.
 *
 *      4. produceOffspring():
 *          This public method is used to check if the Knight has acquired a new
 *          Quest the current Quest for the Knight.
 *
 *      5. die(Quest newQuest):
 *          This public method is used to assign a Quest to the Knight.
 *
 */


public class Turtle extends Thread{
    // =========================================================================
    // Class Properties
    // =========================================================================
    private Patch currLocation;
    private int vision, currWealth, lifeExpectancy, age, metabolism, ticksToNextEat;
    private Field field;
 

    // =========================================================================
    // Constructor: Creates a new turtle for the given vision, lifeExpectancy
    // and metabolism.
    // =========================================================================
    Turtle (int vision, int lifeExpectancy, int metabolism, int ticksToNextEat, Field field) {
        // Initialising all the properties here.
        this.currLocation   = new Patch();
        this.vision         = vision; 
        this.currWealth     = 0;
        this.lifeExpectancy = lifeExpectancy;
        this.age            = 0;
        this.metabolism     = metabolism
        this.ticksToNextEat = ticksToNextEat;
        this.field          = field;
    }

    // =========================================================================
    // Method: To RUN the process.
    // =========================================================================
    public void run() {
         

        while (!isInterrupted()) {
            // Procedure 1: Move 
            moveToLocation();

            // Procedure 2: Eat  
            harvest(this.currLocation);

            // Procedure 3: Age 

            // Procedure 4: Die

        }
    }

    // =========================================================================
    // Method: To move to a new location.
    // =========================================================================
    public void moveToLocation() { 
        // I guess this updates the currLocation

    }

    // =========================================================================
    // Method: To harvest the currentPatch.
    // =========================================================================
    public void harvest() {
        // Turtle harvests the currLocation?
        // and then updates wealth

    }

    // =========================================================================
    // Method: To produceOffspring.
    // =========================================================================
    public Turtle produceOffspring() {
        // Hmmmm

    }

    // =========================================================================
    // Method: To die.
    // =========================================================================
    public void die() {
        // how to die?
    }

}