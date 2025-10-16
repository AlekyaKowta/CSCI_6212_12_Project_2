import java.util.*;

/**
 * This class demonstrates measuring experimental and theoretical
 * run times of a cubic complexity function using arrays with random values.
 * It scales theoretical times to compare with experimental times.
 *
 * @author Alekya Kowta
 * @version 1.0
 * @reviewer Jay Parmar
 */

public class Main{

    /**
     * Generates a list of n random points.
     * @param n The number of points to generate.
     * @return A list of n Point objects.
     */
    public static List<Point> generateRandomPoints(int n) {
        List<Point> points = new ArrayList<>(n);
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            points.add(new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000));
        }
        return points;
    }

    // region Caluclate Theoretical Time

    /** Calculates n^2 (unitless complexity) */
    public static double theoreticalOn2(int n) {
        return (double) n * n;
    }

    /** Calculates n * log2(n) (unitless complexity) */
    public static double theoreticalOnlogn(int n) {
        if (n <= 1) return 0;
        // n * log_2(n)
        return (double) n * (Math.log(n) / Math.log(2));
    }

    /** Calculates n (unitless complexity) */
    public static double theoreticalOn(int n) {
        return (double) n;
    }

    // endregion

    // region Derive Scaling Constants
    /** These constants are calculated from the experimental data at N=300000.
    C = Experimental_Time / Raw_Theoretical_Value **/

    // C2 for O(n^2): Derived from N=20000: 0.9213 ms / 4.0e8 = 2.30325e-9
    private static final double C2 = 2.30325e-9;

    // C_LOGN for O(n log n): Recalculated for new max N=600000: 75.6707 ms / 1.15168e7 = 6.5705e-6
    private static final double C_LOGN = 6.5705e-6;

    // C1 for O(n): Derived from N=1000000: 3.7607 ms / 1.0e6 = 3.76070e-6
    private static final double C1 = 3.76070e-6;

    // endregion

    // region Main Calculation

    /**
     * Main driver method. Runs experiments for various input sizes n,
     * measuring the time taken by the Staircase Algorithm code and compares with
     * theoretical time complexity.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        System.out.println("Running Staircase Algorithms for Complexity Analysis\n");

        // Print the hardcoded scaling constants
        System.out.println("--- Derived Scaling Constants (C) ---");
        System.out.printf("C(n^2): %e\n", C2);
        System.out.printf("C(n log n): %e\n", C_LOGN);
        System.out.printf("C(n): %e\n", C1);
        System.out.println("-----------------------------------\n");


        // Define N values optimized for each complexity class

        // O(n^2) N values: Focus on smaller N where timing is practical (up to 20k)
        int[] N_values_On2 = {20, 40, 50, 80, 100, 200, 500, 1000, 2000, 5000, 8000, 10000, 15000, 20000};

        // O(n log n) N values: Broad range for comparison (up to 300k)
        int[] N_values_Onlogn = {5000, 10000, 15000, 30000, 50000, 100000, 200000, 300000, 400000, 500000, 600000};

        // O(n) N values: Focus on larger N for stable, linear timing (10k to 1M)
        int[] N_values_On = {5000, 8000, 10000, 20000, 50000, 100000, 200000, 300000, 500000, 750000, 1000000};

        int numRuns = 5; // Number of runs to average timing

        // --- O(n^2) Experiment ---
        System.out.println("\n\n#################################################################");
        System.out.println("### O(n^2) Experiment Data (N values: 80 to 20,000)           ###");
        System.out.println("#################################################################");
        System.out.printf("%-10s | %-8s | %-8s | %-8s\n",
                "N Value", "Exp(ms)", "Raw", "Scaled(ms)");
        System.out.println("------------------------------------------");

        for (int N : N_values_On2) {
            List<Point> points = generateRandomPoints(N);
            long timeOn2 = 0;

            // O(n^2) Timing
            for (int r = 0; r < numRuns; r++) {
                long startTime = System.nanoTime();
                StaircaseAlgorithms.computeStaircase_On2(points);
                timeOn2 += (System.nanoTime() - startTime);
            }

            double avgTimeOn2 = (double) timeOn2 / numRuns / 1_000_000.0;
            double theoRawOn2 = theoreticalOn2(N);
            double theoScaledOn2 = C2 * theoRawOn2;

            // Print the combined results: Exp | Theo Raw | Theo Scaled
            System.out.printf("%-10d | %-8.4f | %-8g | %-8.4f\n",
                    N, avgTimeOn2, theoRawOn2, theoScaledOn2);
        }

        // --- O(n log n) Experiment ---
        System.out.println("\n\n#################################################################");
        System.out.println("### O(n log n) Experiment Data (N values: 80 to 300,000)      ###");
        System.out.println("#################################################################");
        System.out.printf("%-10s | %-8s | %-8s | %-8s\n",
                "N Value", "Exp(ms)", "Raw", "Scaled(ms)");
        System.out.println("------------------------------------------");

        for (int N : N_values_Onlogn) {
            List<Point> points = generateRandomPoints(N);
            long timeOnlogn = 0;

            // O(n log n) Timing
            for (int r = 0; r < numRuns; r++) {
                long startTime = System.nanoTime();
                StaircaseAlgorithms.computeStaircase_Onlogn(points);
                timeOnlogn += (System.nanoTime() - startTime);
            }

            double avgTimeOnlogn = (double) timeOnlogn / numRuns / 1_000_000.0;
            double theoRawOnlogn = theoreticalOnlogn(N);
            double theoScaledOnlogn = C_LOGN * theoRawOnlogn;

            // Print the combined results: Exp | Theo Raw | Theo Scaled
            System.out.printf("%-10d | %-8.4f | %-8g | %-8.4f\n",
                    N, avgTimeOnlogn, theoRawOnlogn, theoScaledOnlogn);
        }

        // --- O(n) Experiment ---
        System.out.println("\n\n#################################################################");
        System.out.println("### O(n) Experiment Data (N values: 10,000 to 1,000,000)     ###");
        System.out.println("#################################################################");
        System.out.printf("%-10s | %-8s | %-8s | %-8s\n",
                "N Value", "Exp(ms)", "Raw", "Scaled(ms)");
        System.out.println("------------------------------------------");

        for (int N : N_values_On) {
            List<Point> points = generateRandomPoints(N);
            long timeOn = 0;

            // Prepare for O(n) (pre-sorting time is not included in the O(n) measurement)
            List<Point> preSortedPoints = new ArrayList<>(points);
            preSortedPoints.sort(new PointXComparator());

            for (int r = 0; r < numRuns; r++) {
                long startTime = System.nanoTime();
                StaircaseAlgorithms.computeStaircase_On(preSortedPoints);
                timeOn += (System.nanoTime() - startTime);
            }

            double avgTimeOn = (double) timeOn / numRuns / 1_000_000.0;
            double theoRawOn = theoreticalOn(N);
            double theoScaledOn = C1 * theoRawOn;

            // Print the combined results: Exp | Theo Raw | Theo Scaled
            System.out.printf("%-10d | %-8.4f | %-8g | %-8.4f\n",
                    N, avgTimeOn, theoRawOn, theoScaledOn);
        }
    }
    // endregion
}
