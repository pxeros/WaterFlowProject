import java.util.Scanner;

/**
 * Created by Peter on 9/14/2016.
 * This is the random Grid tester, it is completely independent of WaterFlow, but since I created it and
 * used it for the Exeprimental portion of the project, I found it valid to submit it.
 */
public class RandomGridTester {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.printf("Rows: ");
        int rows = s.nextInt();
        System.out.printf("Columns: ");
        int columns = s.nextInt();
        int endReached = 0;
        for (double k = 0.2; k < 0.8; k += 0.1) {
            for (int i = 0; i < 100; i++) {
                WaterFlow WF = new WaterFlow(rows, columns, GenerateRandomGrid.generateGrid(rows, columns, k));
                WF.visual = false;
                WF.determineFlow();
                if (WF.isReached()) {
                    endReached++;
                }
            }
            System.out.printf("Probability: %.1f\nAverage: %d/100\n",k, endReached);
            endReached = 0;
        }
    }
}
