/**
 *  Field.java
 *
 *  This is the Field class where the simulation happens.
 *  It is a Monitor responsible for managing the simulation.
 *
 *  ============================================================================
 *  @authorName     : Waqas Rehmani, Angus Hudson, Jonathan Dunne
 *  @studentNumber  : 1035514, 835808, 836748
 *  ============================================================================
 *
 *  ============================================================================
 *  Description of the object properties:
 *  ============================================================================
 *      - name:
 *          This is a "String" that holds the name of the Field.
 *      - height:
 *          This is an "int" that holds the height of the Field.
 *      - width:
 *          This is an "int" that holds the width of the Field.
 *      - wealthiestTurtleBalance:
 *          This is an "int".
 *      - poorestTurtleBalance:
 *          This is an "int". 
 *
 *  ============================================================================
 *  Detailed description of Methods:
 *  ============================================================================
 *  This class has - synchronized methods:
 *      
 *
 */

import java.util.*;

public class Field {
    // =========================================================================
    // Class Properties
    // =========================================================================
    private String name;
    private int    height, width, wealthiestTurtleBalance, poorestTurtleBalance; 

    // =========================================================================
    // Constructor: Creates a new field for the given name, height and width
    // =========================================================================
    Field (String name, int height, int width) {
        // Initialising all the properties here.
        this.name                    = name;
        this.height                  = height;
        this.width                   = width;
        this.wealthiestTurtleBalance = 0;
        this.poorestTurtleBalance    = 0;
    }

   

}