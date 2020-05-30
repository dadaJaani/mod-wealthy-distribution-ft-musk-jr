/**
 *  Turtle.java
 *
 *  This is the Turtle class that represents a Turtle in the simulator.
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
 *      - field:
 *          This is a "Field" variable that represents the field the turtle is
 *          on. 
 *
 *  ============================================================================
 *  Detailed description of Methods:
 *  ============================================================================
 *  This class has getters and setter methods.
 *
 */


public class Turtle {
    // =========================================================================
    // Class Properties
    // =========================================================================
    private Patch currLocation;
    private int vision, lifeExpectancy, age, metabolism, heading;
    private char color;
    int currWealth;
    // =========================================================================
    // Constructor: Creates a new turtle for the given vision, currWealth, 
    // lifeExpectancy, and metabolism.
    // =========================================================================
    Turtle(int vision, int currWealth, int lifeExpectancy, int metabolism, int age, Patch location) {
        // Initialising all the properties here.
        this.currLocation   = location;
        this.vision         = vision; 
        this.currWealth     = currWealth;
        this.lifeExpectancy = lifeExpectancy;
        this.age            = age;
        this.metabolism     = metabolism;
        this.color          = 'n';
        this.heading        = 0;
    }

    public void setCurrLocation(Patch patch) {
        this.currLocation = patch;
    }

    public Patch getCurrLocation() {
        return this.currLocation;
    }

    public void setWealth(int wealth) {
        this.currWealth = wealth;
    }

    public int getWealth() {
        return this.currWealth;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public char getColor() {
        return this.color;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public int getVision() {
        return this.vision;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public int getHeading() {
        return this.heading;
    }

    public void setMetabolism(int metabolism) {
        this.metabolism = metabolism;
    }
    
    public int getMetabolism() {
        return this.metabolism;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void setLifeExpectancy(int lifeExpectancy) { this.lifeExpectancy = lifeExpectancy; }

    public int getLifeExpectancy() {
        return this.lifeExpectancy;
    }

    public int compare( Turtle t) { 
        return this.currWealth - t.getWealth(); 
    } 

}