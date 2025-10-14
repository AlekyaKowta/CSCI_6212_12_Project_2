import java.util.*;

public class Main{
    // --- Helper function to generate test data ---
    /**
     * Generates a list of n random points.
     * @param n The number of points to generate.
     * @return A list of n Point objects.
     */
    public static List<Point> generateRandomPoints(int n) {
        List<Point> points = new ArrayList<>(n);
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            // Generate X and Y coordinates between 0 and 1000
            points.add(new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000));
        }
        return points;
    }

    // --- Theoretical Time Calculation Functions (Raw f(n) without scaling) ---

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

    // --- Derived Scaling Constants ---
    // These constants are calculated from the experimental data at N=300000.
    // C = Experimental_Time / Raw_Theoretical_Value

    // C2 for O(n^2): 25.6687 ms / 9.0e10 = 2.852077e-10
    private static final double C2 = 2.852077e-10;

    // C_LOGN for O(n log n): 34.5536 ms / 5.45838e6 = 6.330896e-6
    private static final double C_LOGN = 6.330896e-6;

    // C1 for O(n): 0.3262 ms / 300000 = 1.087333e-6
    private static final double C1 = 1.087333e-6;


    // --- Main Method for Demonstration and Plot Data Generation ---

    public static void main(String[] args) {

        System.out.println("Running Staircase Algorithms for Complexity Analysis\n");

        // Print the hardcoded scaling constants
        System.out.println("--- Derived Scaling Constants (C) ---");
        System.out.printf("C(n^2): %e\n", C2);
        System.out.printf("C(n log n): %e\n", C_LOGN);
        System.out.printf("C(n): %e\n", C1);
        System.out.println("-----------------------------------\n");

        // Define N values for plotting the graph
        int[] N_values = {80, 100, 200, 500, 800, 1000, 5000, 10000, 15000, 20000, 40000, 80000, 100000, 300000};
        int numRuns = 5; // Number of runs to average timing

        // Print header for the table
        System.out.printf("%-10s | %-31s | %-36s | %-31s\n",
                "N Value", "O(n^2) Exp/Raw/Scaled", "O(n log n) Exp/Raw/Scaled", "O(n) Exp/Raw/Scaled");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        for (int N : N_values) {
            List<Point> points = generateRandomPoints(N);

            long timeOn2 = 0;
            long timeOnlogn = 0;
            long timeOn = 0;

            // --- Experimental Timing Loops ---

            // O(n^2) Timing
            for (int r = 0; r < numRuns; r++) {
                long startTime = System.nanoTime();
                StaircaseAlgorithms.computeStaircase_On2(points);
                timeOn2 += (System.nanoTime() - startTime);
            }

            // O(n log n) Timing
            for (int r = 0; r < numRuns; r++) {
                long startTime = System.nanoTime();
                StaircaseAlgorithms.computeStaircase_Onlogn(points);
                timeOnlogn += (System.nanoTime() - startTime);
            }

            // O(n) Timing (Requires pre-sorting)
            List<Point> preSortedPoints = new ArrayList<>(points);
            preSortedPoints.sort(new PointXComparator());

            for (int r = 0; r < numRuns; r++) {
                long startTime = System.nanoTime();
                StaircaseAlgorithms.computeStaircase_On(preSortedPoints);
                timeOn += (System.nanoTime() - startTime);
            }

            // --- Calculate Average Experimental Times (ms) ---
            double avgTimeOn2 = (double) timeOn2 / numRuns / 1_000_000.0;
            double avgTimeOnlogn = (double) timeOnlogn / numRuns / 1_000_000.0;
            double avgTimeOn = (double) timeOn / numRuns / 1_000_000.0;

            // --- Calculate Theoretical Values ---
            double theoRawOn2 = theoreticalOn2(N);
            double theoRawOnlogn = theoreticalOnlogn(N);
            double theoRawOn = theoreticalOn(N);

            // --- Calculate Scaled Theoretical Times (ms) ---
            double theoScaledOn2 = C2 * theoRawOn2;
            double theoScaledOnlogn = C_LOGN * theoRawOnlogn;
            double theoScaledOn = C1 * theoRawOn;


            // Print the combined results: Exp / Theo Raw / Theo Scaled
            System.out.printf("%-10d | %-8.4f / %-8g / %-8.4f | %-12.4f / %-8g / %-8.4f | %-8.4f / %-8g / %-8.4f\n",
                    N,
                    avgTimeOn2, theoRawOn2, theoScaledOn2,
                    avgTimeOnlogn, theoRawOnlogn, theoScaledOnlogn,
                    avgTimeOn, theoRawOn, theoScaledOn);
        }
    }
}
