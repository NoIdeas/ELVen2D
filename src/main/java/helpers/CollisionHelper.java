package helpers;

import javafx.geometry.Rectangle2D;

/**
 * Created by NoIdeas.
 */

public class CollisionHelper
{
    /**
     * Returns a rectangle that represents the intersection between two other rectangles.
     * Returns an empty rectangle, if the rectangles are not intersecting
     *
     * @param a A rectangle
     * @param b Another rectangle
     * @return A rectangle that represents the intersection of the two rectangles, that is, the area that they are
     * colliding on
     */
    public static Rectangle2D intersect(Rectangle2D a, Rectangle2D b)
    {
        if (!a.intersects(b))
            return Rectangle2D.EMPTY;

        double x = Math.max(a.getMinX(), b.getMinX());
        double y = Math.max(a.getMinY(), b.getMinY());
        double w = Math.min(a.getMaxX(), b.getMaxX()) - x;
        double h = Math.min(a.getMaxY(), b.getMaxY()) - y;

        return new Rectangle2D(x, y, w, h);
    }
}
