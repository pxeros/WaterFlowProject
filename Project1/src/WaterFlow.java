/**
 * WaterFlow
 * Water flow problem which start every empty cell in the first row.
 *
 * @author TODO: Put your name here.
 * @CS login ID: TODO: Put your login here
 * @PSO section: TODO: Put your PSO section here
 * @date TODO: Put the date of completion here.
 */

import sun.awt.image.ImageWatched;

import javax.swing.*;
import java.util.*;

public class WaterFlow {
    public int rows; //number of rows of the grid
    public int columns; //number of columns of grid
    public int[][] delayTimeGrid; //time of water in the grid, and 0 means the water is blocked
    public int[][] reachTimeGrid; //time of water flow reach that point
    public boolean[][] earliestPathGrid; //a boolean grid that identify the shortest path. For visualization purpose
    public List<Cell> earliestPath; //The earliest flow path NOTE: I did not end up using this
    public WaterFlowVisualization visualization;
    public Scanner s = new Scanner(System.in);
    public boolean visual = true;
    //This is the boolean value that is used to determine when the path is broken.
    public boolean breakLoop;
    //This is the timer, it is simply for keeping track of the time
    public int timer;
    //This is my data structure, it is explained in the analysis questions
    public LinkedList<LinkedList<Cell>> cellTime;

    //TODO: add variables that you need

    /* The default constructor 
     * Read Input from terminal, do not modify it
     * */
    public WaterFlow() {
        //get inputs
        rows = s.nextInt();
        columns = s.nextInt();
        delayTimeGrid = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                delayTimeGrid[i][j] = s.nextInt();
            }
        }

        //Initializes all the values in reach Time grid as -1 to indicate it has not been reached
        reachTimeGrid = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                reachTimeGrid[i][j] = -1;
            }
        }
        //earliest path variable
        earliestPath = new LinkedList<Cell>();
        //Since boolean initializes as false, there is no need to loop through the entire Grid
        earliestPathGrid = new boolean[rows][columns];
        //This is the timer, since i already initialized all the values for 0, i set it to one
        timer = 1;
        //Initialize the list, nothing special
        cellTime = new LinkedList<LinkedList<Cell>>();
        //This adds a value to the outer list for all the cells reached at time = 0
        cellTime.add(new LinkedList<Cell>());
        //This loop initializes all the cells reached at time = 0
        for (int i = 0; i < columns; i++) {
            if (delayTimeGrid[0][i] == 0) {
                continue;
            } else {
                cellTime.get(0).add(new Cell(0, i));
                reachTimeGrid[0][i] = 0;
            }
        }
        //since I do want to run determineFlow, I set my loop breaking variable to false
        breakLoop = false;

    }

    //This is the constructor for the random testing, ignore it
    public WaterFlow(int row, int column, int[][] delayTimeGrid) {
        this.rows = row;
        this.columns = column;
        this.delayTimeGrid = delayTimeGrid;
        //Initializes all the values in reach Time grid as -1 to indicate it has not been reached
        reachTimeGrid = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                reachTimeGrid[i][j] = -1;
            }
        }
        //earliest path variable
        earliestPath = new LinkedList<Cell>();
        //Since boolean initializes as false, there is no need to loop through the entire Grid
        earliestPathGrid = new boolean[rows][columns];
        //This is the timer, since i already initialized all the values for 0, i set it to one
        timer = 1;
        //Initialize the list, nothing special
        cellTime = new LinkedList<LinkedList<Cell>>();
        //This adds a value to the outer list for all the cells reached at time = 0
        cellTime.add(new LinkedList<Cell>());
        //This loop initializes all the cells reached at time = 0
        for (int i = 0; i < columns; i++) {
            if (delayTimeGrid[0][i] == 0) {
                continue;
            } else {
                cellTime.get(0).add(new Cell(0, i));
                reachTimeGrid[0][i] = 0;
            }
        }
        breakLoop = false;
    }

    /**
     * Update the water flow once.
     *
     * @return Null
     */
    public void flow() {
        // Don't Change this part, it's visualize Part
        if (visual) try {
            Thread.sleep(100);
            //count++;
            //if (count == 5) Thread.sleep(20000);
            visualization.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 0;//This is my looping variable for
        if (timer > 3) {
            cellTime.get(0).clear();
            cellTime.remove(0);
        }

        i = 0;
        /**S
         * Lines 103- 111 are necessary for checking if the flow is finished,
         * if the length of every ist in the outer queue is 0, then that means that there has been
         * no water flow for over 3 units of time, the way the grid is set up, this is impossible,
         * so if this is the case, it will break the function and the loop in determineFlow()
         * */
        while (i < cellTime.size()) { //line 103
            if (cellTime.get(i).size() != 0) {
                break;
            }
            i++;
        }
        if (i == cellTime.size()) {
            breakLoop = true;
            //now we exit the function
            return;
        }//line 111

        int currentRow;
        int currentColumn;
        // We add a new time to the data structure
        cellTime.add(new LinkedList<Cell>());
        while (i < cellTime.size()) {
            for (int j = 0; j < cellTime.get(i).size(); j++) {
                //These retrieve the row and column of the cell we are accessing in the structure
                currentRow = cellTime.get(i).get(j).row;
                currentColumn = cellTime.get(i).get(j).column;
                //This if statement checks if the delay on the flow of the water has been followed and it is ready to flow
                if (timer == reachTimeGrid[currentRow][currentColumn] +
                        delayTimeGrid[currentRow][currentColumn]) {
                    //This if statement checks if the square to the left of the current cell
                    if (currentColumn > 0 && reachTimeGrid[currentRow][currentColumn - 1] == -1 &&
                            delayTimeGrid[currentRow][currentColumn - 1] != 0) {
                        reachTimeGrid[currentRow][currentColumn - 1] = timer;
                        cellTime.get(cellTime.size() - 1).add(new Cell(currentRow, currentColumn - 1));
                    }//This if statement checks the cell to the right of the current cell
                    if (currentColumn < columns - 1 && reachTimeGrid[currentRow][currentColumn + 1] == -1 &&
                            delayTimeGrid[currentRow][currentColumn + 1] != 0) {
                        reachTimeGrid[currentRow][currentColumn + 1] = timer;
                        cellTime.get(cellTime.size() - 1).add(new Cell(currentRow, currentColumn + 1));
                    }//This if statement checks the cell directly below the current cell
                    if (currentRow < rows - 1 && reachTimeGrid[currentRow + 1][currentColumn] == -1 &&
                            delayTimeGrid[currentRow + 1][currentColumn] != 0) {
                        reachTimeGrid[currentRow + 1][currentColumn] = timer;
                        cellTime.get(cellTime.size() - 1).add(new Cell(currentRow + 1, currentColumn));
                    }
                }
            }
            i++;
        }

    }

    /**
     * Calculate the waterflow until it ends.
     */
    public void determineFlow() {
        //TODO: Fill in the condition of the while loop
        //This loop will break
        while (!breakLoop) {
            this.flow();
            //increment the time for building reachTimeGrid
            timer++;
        }
        cellTime.clear();
        cellTime = null;

    }


    /**
     * Create the Visualization of the Waterflow
     */
    public void visualize() {
        visualization = new WaterFlowVisualization(this);

        JFrame frame = new JFrame("Water Flow Visualization");
        frame.add(visualization);
        visualization.init();
        visualization.start();
        frame.setSize(visualization.getSize());
        frame.setVisible(true);
    }

    /**
     * Find one shortest path and update the shortestGrid.
     */
    public void earliestFlowPath() {
        //checks if end was even reached to determine whether we should run the rest of the function
        if (!isReached()) {
            return;
        }
        //variable for what column has the fastest end reach time
        int fastestColumn = 0;
        //loops through row n-1 to find the end of the quickest path
        for (int i = 0; i < columns; i++) {
            //first if statement makes sure that if the current fastest end is never reached, that is changed
            if (reachTimeGrid[rows - 1][fastestColumn] == -1 && reachTimeGrid[rows - 1][i] != -1) {
                fastestColumn = i;
            }//this if statement then checks if the current fastest cell has a longer reac time than the one being looked at
            if (reachTimeGrid[rows - 1][fastestColumn] + delayTimeGrid[rows - 1][fastestColumn] > reachTimeGrid[rows - 1][i] + delayTimeGrid[rows - 1][i] &&
                    reachTimeGrid[rows - 1][i] != -1) {
                fastestColumn = i;
            }
        }
        //Once we have our first cell, alter the earliestPathGrid, and then add the cell to the earliestPath list
        earliestPathGrid[rows - 1][fastestColumn] = true;
        earliestPath.add(0, new Cell(rows - 1, fastestColumn));
        //set variables for determining what the cell row is
        int fastestRow = rows - 1;
        //reach time for the cell above the current fastest cell
        int above = -1;
        //reach time for the cell to the left of the current cell
        int left = -1;
        //reach time for the cell to the right of the current cell
        int right = -1;
        while (fastestRow > 0) {
            //these next three if statements assign the vlaues for above, right, and left. It checks the values to stay in bounds
            if (fastestRow > 0) {
                above = reachTimeGrid[fastestRow - 1][fastestColumn] + delayTimeGrid[fastestRow - 1][fastestColumn];
            }
            if (fastestColumn > 0) {
                left = reachTimeGrid[fastestRow][fastestColumn - 1] + delayTimeGrid[fastestRow][fastestColumn - 1];
            }
            if (fastestColumn < columns - 1) {
                right = reachTimeGrid[fastestRow][fastestColumn + 1] + delayTimeGrid[fastestRow][fastestColumn + 1];
            }
            //These next three if statements increment or decrement the row and column of the next cell based on above, right, and left
            if (left != -1 && left == reachTimeGrid[fastestRow][fastestColumn]) {
                fastestColumn--;
            } else if (above != -1 && above == reachTimeGrid[fastestRow][fastestColumn]) {
                fastestRow--;
            } else if (right != -1 && right == reachTimeGrid[fastestRow][fastestColumn]) {
                fastestColumn++;
            }
            //once again, add the new cell to the earliestPathGrid and the earliestPath linked list
            earliestPath.add(0, new Cell(fastestRow, fastestColumn));
            earliestPathGrid[fastestRow][fastestColumn] = true;
            //sets values back to -1, since -1 implies that the cell is out of bounds
            above = -1;
            left = -1;
            right = -1;
        }

    }

    /**
     * This checks if the water reached the end row
     *
     * @return boolean value
     */
    public boolean isReached() {
        //-1 implies that said square was never reached
        for (int i = 0; i < columns; i++) {
            //if there exists a vaue on the last row that is not -1, then the last row was reached.
            if (reachTimeGrid[rows - 1][i] != -1) {
                return true;
            }
        }
        return false;
    }


}

