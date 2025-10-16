import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StaircaseAlgorithmsTest {

    // The comparator must be instantiated from your project file
    private final Comparator<Point> comparator = new PointXComparator();

    /**
     * Helper to check that two lists of points are equal, regardless of the initial order,
     * by sorting them using PointXComparator and then comparing elements.
     */
    private void assertStaircasesEqual(List<Point> expected, List<Point> actual) {
        // Create copies and sort them to ensure a canonical comparison order
        List<Point> expectedSorted = new ArrayList<>(expected);
        List<Point> actualSorted = new ArrayList<>(actual);
        expectedSorted.sort(comparator);
        actualSorted.sort(comparator);

        assertEquals(expectedSorted.size(), actualSorted.size(), "Staircase size mismatch.");

        // Check content point by point (requires Point.equals() to be correctly implemented)
        for (int i = 0; i < expectedSorted.size(); i++) {
            assertEquals(expectedSorted.get(i), actualSorted.get(i),
                    "Point at index " + i + " mismatch. Expected point: " + expectedSorted.get(i) + ", Actual point: " + actualSorted.get(i));
        }
    }

    // =========================================================================
    // Core Functionality Tests
    // =========================================================================

    @Test
    void testStandardStaircase() {
        // Points: (1, 1), (2, 5), (3, 3), (4, 4), (5, 2)
        // (1, 1) is dominated by (2, 5). (3, 3) is dominated by (4, 4) and (2, 5).
        // Correct Expected Optimal Set: (2, 5), (4, 4), (5, 2) [Size 3]
        List<Point> points = Arrays.asList(
                new Point(1, 1), new Point(2, 5), new Point(3, 3), new Point(4, 4), new Point(5, 2)
        );
        // FIX: Changed expected size from 4 to 3 by removing (1, 1)
        List<Point> expected = Arrays.asList(
                new Point(2, 5), new Point(4, 4), new Point(5, 2)
        );

        // Test all methods for consistency
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_On2(new ArrayList<>(points)));
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_Onlogn(new ArrayList<>(points)));

        // Test O(n) requires pre-sorting the input list
        List<Point> sortedPoints = new ArrayList<>(points);
        sortedPoints.sort(comparator);
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_On(sortedPoints));
    }

    @Test
    void testOnlyOneOptimalPoint() {
        // (5, 5) dominates all others.
        List<Point> points = Arrays.asList(
                new Point(1, 1), new Point(2, 2), new Point(5, 5), new Point(3, 3), new Point(4, 4)
        );
        List<Point> expected = Collections.singletonList(new Point(5, 5));

        // This test now passes because Point.equals() is fixed.
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_On2(new ArrayList<>(points)));
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_Onlogn(new ArrayList<>(points)));

        List<Point> sortedPoints = new ArrayList<>(points);
        sortedPoints.sort(comparator);
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_On(sortedPoints));
    }

    // =========================================================================
    // Edge Case Tests
    // =========================================================================

    @Test
    void testPointsWithEqualXCoordinates() {
        // Points: (1, 5), (1, 10), (1, 1), (5, 5)
        // (1, 1) and (1, 5) are dominated by (1, 10).
        // Correct Expected Optimal Set: (1, 10), (5, 5) [Size 2]
        List<Point> points = Arrays.asList(
                new Point(1, 5), new Point(1, 10), new Point(1, 1), new Point(5, 5)
        );
        List<Point> expected = Arrays.asList(new Point(1, 10), new Point(5, 5));

        // This passes now that O(n^2) logic is fixed.
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_On2(new ArrayList<>(points)));
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_Onlogn(new ArrayList<>(points)));

        List<Point> sortedPoints = new ArrayList<>(points);
        sortedPoints.sort(comparator);
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_On(sortedPoints));
    }

    @Test
    void testPointsWithEqualYCoordinates() {
        // Points: (5, 5), (10, 5), (1, 5), (1, 10)
        // (5, 5) and (1, 5) are dominated by (10, 5) and/or (1, 10).
        // Correct Expected Optimal Set: (1, 10), (10, 5) [Size 2]
        List<Point> points = Arrays.asList(
                new Point(5, 5), new Point(10, 5), new Point(1, 5), new Point(1, 10)
        );
        List<Point> expected = Arrays.asList(new Point(1, 10), new Point(10, 5));

        // This passes now that O(n^2) logic is fixed.
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_On2(new ArrayList<>(points)));
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_Onlogn(new ArrayList<>(points)));

        List<Point> sortedPoints = new ArrayList<>(points);
        sortedPoints.sort(comparator);
        assertStaircasesEqual(expected, StaircaseAlgorithms.computeStaircase_On(sortedPoints));
    }

    @Test
    void testIdenticalPoints_ConsistencyCheck() {
        // Points: (2, 2), (2, 2), (1, 1). (1, 1) is dominated by (2, 2).
        List<Point> points = Arrays.asList(
                new Point(2, 2), new Point(2, 2), new Point(1, 1)
        );

        // O(n^2) is brute-force and returns both duplicate objects.
        List<Point> expectedOn2 = Arrays.asList(new Point(2, 2), new Point(2, 2));
        assertStaircasesEqual(expectedOn2, StaircaseAlgorithms.computeStaircase_On2(new ArrayList<>(points)));

        // O(n log n) and O(n) use R->L scan with `p.y > maxY`, which returns only the highest Y for any given X,
        // thus returning only one instance when X and Y are identical.
        List<Point> expectedOnlogn = Collections.singletonList(new Point(2, 2));

        // This passes now that Point.equals() is fixed.
        assertStaircasesEqual(expectedOnlogn, StaircaseAlgorithms.computeStaircase_Onlogn(new ArrayList<>(points)));

        List<Point> sortedPoints = new ArrayList<>(points);
        sortedPoints.sort(comparator);
        assertStaircasesEqual(expectedOnlogn, StaircaseAlgorithms.computeStaircase_On(sortedPoints));
    }
}