/**
 *  Turtle.java
 *
 *  This is the Turtle class that represents a Turtle in the simulator.
 *  It is a PROCESS as it extends Thread.
 *
 *  ============================================================================
 *  @authorName     : Waqas Rehmani, Angus Hudson, Jonathan Dunne
 *  @studentNumber  : 1035514, 835808, 836748
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
 *          (could implement harvest(location) instead?)
 * 
 *      4. produceOffspring():
 *          This public method is used to check if the Knight has acquired a new
 *          Quest the current Quest for the Knight.
 *
 *      5. die(Quest newQuest):
 *          This public method is used to assign a Quest to the Knight.
 *
 */


public class Turtle extends Thread {
    // =========================================================================
    // Class Properties
    // =========================================================================
    private Patch currLocation;
    private int vision, currWealth, lifeExpectancy, age, metabolism;
    private Field field;
    // =========================================================================
    // Constructor: Creates a new turtle for the given vision, currWealth, 
    // lifeExpectancy, and metabolism.
    // =========================================================================
    Turtle(int vision, int currWealth, int lifeExpectancy, int metabolism, int age, Field field) {
        // Initialising all the properties here.
        this.currLocation   = null;
        this.vision         = vision; 
        this.currWealth     = currWealth;
        this.lifeExpectancy = lifeExpectancy;
        this.age            = age;
        this.metabolism     = metabolism;
        this.field          = field;
    }

    // =========================================================================
    // Method: To RUN the process.
    // =========================================================================
    public void run() {
         

        while (!isInterrupted()) {
            try{
                // Procedure 1: Move 
                this.field.moveToLocation(this);
    
                // Procedure 2: Eat
                this.field.harvest(this);
    
                // Procedure 3: Age 
                this.age();
    
                // Procedure 4: Die
                this.die();

            } catch (InterruptedException e) {
                this.interrupt();
            }
        
    }
    }

    // =========================================================================
    // Method: To move to a new location.
    // (should this function live in the monitor, Field.java?)
    // =========================================================================
    public void moveToLocation() { 
        // I guess this updates the currLocation
    }

    // =========================================================================
    // Method: To harvest the currentPatch.
    // (should this function live in the monitor, Field.java?)
    // =========================================================================
    public void harvest() {
        // Turtle harvests the currLocation?
        // and then updates wealth
    }

    // =========================================================================
    // Method: To age.
    // =========================================================================
    public void age() {
        this.age++;
    }

    // =========================================================================
    // Method: To produceOffspring.
    // =========================================================================
    public void produceOffspring() { //This must return Turtle. Kept it to void for now for compilation.
        // Hmmmm
        // Big hmmmmmmmmmmm
    }

    // =========================================================================
    // Method: To die.
    // =========================================================================
    public void die() {
        // how to die?

        // could use a flag as described here, named "alive" or sth:
        // https://stackoverflow.com/questions/2491588/how-a-thread-should-close-itself-in-java
    }

    public void setCurrLocation(Patch patch) {
        this.currLocation = patch;
    }

    public Patch getCurrLocation() {
        return this.currLocation;
    }
}