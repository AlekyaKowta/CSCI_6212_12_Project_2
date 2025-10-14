import java.util.Comparator;

/**
 * Comparator for sorting: Primary key X (ascending), Secondary key Y (descending)
 * This handles tie-breaking where points share the same X-coordinate.
 */
class PointXComparator implements Comparator<Point> {
    @Override
    public int compare(Point p1, Point p2) {
        if (p1.x != p2.x) {
            // X ascending
            return Double.compare(p1.x, p2.x);
        } else {
            // Y descending (we want to keep the one with the higher Y if X is the same)
            return Double.compare(p2.y, p1.y);
        }
    }
}
