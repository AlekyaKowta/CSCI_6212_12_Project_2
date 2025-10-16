import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StaircaseAlgorithms {

    /**
     * Computes the Pareto-optimal staircase using a simple brute-force check.
     * Time Complexity: O(n^2)
     *
     * @param points The unsorted list of input points.
     * @return The list of Pareto-optimal points.
     */
    public static List<Point> computeStaircase_On2(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return new ArrayList<>();
        }

        List<Point> staircase = new ArrayList<>();
        int n = points.size();

        // Check every point p against every other point q
        for (int i = 0; i < n; i++) {
            Point p = points.get(i);
            boolean isParetoOptimal = true;

            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                Point q = points.get(j);

                // Check for domination: q is both above AND to the right of p
                if (q.x >= p.x && q.y >= p.y && (q.x > p.x || q.y > p.y)) {
                    isParetoOptimal = false;
                    break;
                }
            }

            if (isParetoOptimal) {
                staircase.add(p);
            }
        }

        // Final step: Sort the optimal points by X to form the actual staircase sequence
        staircase.sort(new PointXComparator());
        return staircase;
    }

    /**
     * Computes the Pareto-optimal staircase using sorting followed by a linear scan.
     * This is the standard efficient solution for general unsorted input.
     * Time Complexity: O(n log n)
     *
     * @param points The unsorted list of input points.
     * @return The list of Pareto-optimal points.
     */
    public static List<Point> computeStaircase_Onlogn(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return new ArrayList<>();
        }

        // Step 1: Sort the points O(n log n)
        List<Point> sortedPoints = new ArrayList<>(points);
        sortedPoints.sort(new PointXComparator());

        // Step 2: O(n) right-to-left scan
        // Use a Stack (ArrayDeque) to store the points; they will be retrieved in the correct x-order.
        Deque<Point> staircaseStack = new ArrayDeque<>();
        double maxY = Double.NEGATIVE_INFINITY;

        for (int i = sortedPoints.size() - 1; i >= 0; i--) {
            Point p = sortedPoints.get(i);

            // If p.y is greater than the max Y seen to the right, p is Pareto-optimal.
            if (p.y > maxY) {
                staircaseStack.push(p);
                maxY = p.y;
            }
        }

        // Convert the stack (which holds the points in the correct sequence) to a list
        return new ArrayList<>(staircaseStack);
    }

    /**
     * Computes the Pareto-optimal staircase assuming the input is already sorted by X.
     * Used for demonstrating the linear time complexity curve.
     * Time Complexity: O(n)
     *
     * @param sortedPoints The list of input points, which MUST be pre-sorted by X ascending.
     * @return The list of Pareto-optimal points.
     */
    public static List<Point> computeStaircase_On(List<Point> sortedPoints) {
        if (sortedPoints == null || sortedPoints.isEmpty()) {
            return new ArrayList<>();
        }

        // O(n) right-to-left scan
        Deque<Point> staircaseStack = new ArrayDeque<>();
        double maxY = Double.NEGATIVE_INFINITY;

        for (int i = sortedPoints.size() - 1; i >= 0; i--) {
            Point p = sortedPoints.get(i);

            if (p.y > maxY) {
                staircaseStack.push(p);
                maxY = p.y;
            }
        }
        return new ArrayList<>(staircaseStack);
    }
}
