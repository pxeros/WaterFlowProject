/**
 * Sample Test Program
 * This file will be replaced for final Grading! Do not submit it.
 * This file does NOT contains all the test cases and corner cases.
 *
 */

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Test {
    /**
     * It's a sample main, you can leave it blank.
     */

    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("smallGrid1.txt"));
        //You can put whatever grid or file you want here, but small grid is a good example of whats best. 
        WaterFlow WF = new WaterFlow();
        if (WF.visual) WF.visualize();

        WF.determineFlow();
        WF.earliestFlowPath();
        System.out.print("Earliest Path: ");
        /**for (Cell c : WF.earliestPath)
            System.out.print("["+c.row + "," + c.column+"] ");
        System.out.println();*/
        //NOT SURE WHAT isReached() is, I made an educated guess, see in WaterFlow.java
        System.out.println(WF.isReached());
        System.out.printf("rows:%d\ncolumns:%d\n",WF.rows, WF.columns);
        for (int i = 0; i < WF.reachTimeGrid.length; i++) {
            for (int j = 0; j < WF.reachTimeGrid[0].length; j++) {
                System.out.printf("%d\t", WF.reachTimeGrid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < WF.reachTimeGrid.length; i++) {
            for (int j = 0; j < WF.reachTimeGrid[0].length; j++) {
                System.out.printf("%d\t", WF.delayTimeGrid[i][j]);
            }
            System.out.println();
        }
    }
}

