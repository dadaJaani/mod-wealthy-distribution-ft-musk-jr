/**
 *  Field.java
 *
 *  This is the Field class that represents the Field in the simulator.
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
 *      - width: 
 *          This is an "int" variable that holds the 'width' of the Field.
 *      - height:
 *          This is an "int" variable that holds the 'height' of the Field.
 *      - patches:
 *          This is an Patch[][] variable that holds the grid of patches.
 *
 *  ============================================================================
 *  Detailed description of Methods:
 *  ============================================================================
 *  This class has one method:
 *      1. initialisePatches():
 *          To initialise Patches.
 *      2. diffusePatches():
 *          To diffuse the Patches.
 *      3. fillRichPatches():
 *          To fill the rich Patches.
 *      4. growPatches():
 *          To grow Patches. 
 *
 *  Then we also have getters and setter methods.
 */


public class Field {
    // =========================================================================
    // Class Properties
    // =========================================================================
    private int width, height;
    Patch[][] patches;
 
    // ------------------------------------------------------------------------
    // Constructor: Creates a new patch for the given values for grain
    // and max grain.
    // ------------------------------------------------------------------------
    Field (int width, int height) {
        // Initialising all the properties here.
        this.width  = width;
        this.height = height; 
        this.patches = new Patch[Params.FIELD_HEIGHT][Params.FIELD_WIDTH];
        initialisePatches();
    }
 
    // ------------------------------------------------------------------------
    // Method: 
    // ------------------------------------------------------------------------
    private void initialisePatches() {
        // Initialise patches to be empty with 0 maxin, unless they are 'rich'
        int maxGrain;
        int nGrain;
        for (int i = 0; i < Params.FIELD_HEIGHT; i++) {
            for (int j = 0; j < Params.FIELD_WIDTH; j++) {
                maxGrain = nGrain = 0;
                if (Params.getRandomPercentage() <= Params.PERCENT_BEST_LAND) {
                    maxGrain = nGrain = Params.MAX_GRAIN; // 'Rich' patches
                }
                this.patches[i][j] = new Patch(nGrain, maxGrain, i, j);
            }
        }

        

        for (int i = 0; i < Params.N_FIRST_DIFFUSIONS; i++) {
            fillRichPatches();
            diffusePatches();
        }

        for (int i = 0; i < Params.N_SECOND_DIFFUSIONS; i++) {
            diffusePatches();
        }

        for (int row = 0; row < Params.FIELD_HEIGHT; row++) {
            for (int col = 0; col < Params.FIELD_WIDTH; col++) {
                this.patches[row][col].setCurrGrain((int) this.patches[row][col].getCurrGrain());
                this.patches[row][col].setMaxGrain((int) this.patches[row][col].getCurrGrain());
            }
        }
    }

    // ------------------------------------------------------------------------
    // Method: To diffuse the patches.
    // ------------------------------------------------------------------------
    public void diffusePatches() {
        // We use a separate grid so the patches aren't updated until ALL have been processed for diffusion
        double[][] transitionGrid = new double[Params.FIELD_HEIGHT][Params.FIELD_WIDTH];
        int left, right, up, down;
        for (int row = 0; row < Params.FIELD_HEIGHT; row++) {
            for (int col = 0; col < Params.FIELD_WIDTH; col++) {
                up = row - 1;
                down = row + 1;
                left = col - 1;
                right = col + 1;
                // The board wraps horizontally and vertically
                if (up < 0) {
                    up = Params.FIELD_HEIGHT - 1;
                }
                if (down == Params.FIELD_HEIGHT) {
                    down = 0;
                }
                if (left < 0) {
                    left = Params.FIELD_WIDTH - 1;
                }
                if (right == Params.FIELD_WIDTH) {
                    right = 0;
                }
                // Now update all transitions
                double diffuse = Params.DIFFUSION_RATE * (1.0/8) * this.patches[row][col].getCurrGrain();

                transitionGrid[up][left] += diffuse;
                transitionGrid[up][col] += diffuse;
                transitionGrid[up][right] += diffuse;

                transitionGrid[row][left] += diffuse;
                transitionGrid[row][right] += diffuse;

                transitionGrid[down][left] += diffuse;
                transitionGrid[down][col] += diffuse;
                transitionGrid[down][right] += diffuse;

                transitionGrid[row][col] -= 8 * diffuse;
            }
        }

        for (int row = 0; row < Params.FIELD_HEIGHT; row++) {
            for (int col = 0; col < Params.FIELD_WIDTH; col++) {
                this.patches[row][col].setCurrGrain(this.patches[row][col].getCurrGrain() + transitionGrid[row][col]);
            }
        }
    }

    // ------------------------------------------------------------------------
    // Method: To fill the rich patches.
    // ------------------------------------------------------------------------
    public void fillRichPatches() {
        for (int row = 0; row < Params.FIELD_WIDTH; row++) {
            for (int col = 0; col < Params.FIELD_HEIGHT; col++) {
                if (this.patches[row][col].getMaxGrain() != 0) {
                    this.patches[row][col].setCurrGrain(this.patches[row][col].getMaxGrain());;
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Method: To grow the patches.
    // ------------------------------------------------------------------------
    public void growPatches() {
        for (Patch[] patchRow : this.patches) {
            for (Patch p : patchRow) {
                p.grow();
            }
        }
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    public Patch getPatch(int row, int col) {
        // not sure if I exchanged row and col??
        return this.patches[row][col];
    }

    public Patch getRandomPatch() {
        int patchRow = Params.getRandomRow();
        int patchCol = Params.getRandomCol();
        return this.patches[patchRow][patchCol];
    }


}