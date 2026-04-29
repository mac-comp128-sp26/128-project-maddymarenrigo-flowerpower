package FlowerPower.model.Datatypes;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

/**
 * Static. Gives randomly generated roughly-evenly-spaced points in a 2D plane.
 */
public class SpacedRandom {
    private static final double DEFAULT_BIAS = 0.5;

    /**
     * Returns as many roughly-evenly-spaced points as will fit in the world.
     * bias is how strongly the points resist ending up near each other by chance. 0: not at all; 1: enough to always result in a perfect grid; 0.5: default
     * @param spreadDistance
     * @param worldSize
     * @param bias
     * @return List<Point>
     */
    public static List<Point> yieldAllPoints(double spreadDistance, double worldSize, double bias) {
        List<Point> results = new ArrayList<>();

        bias = 1 - Math.max(Math.min(bias, 1), 0);

        for (double x = 0; x < worldSize; x += spreadDistance) {
            for (double y = 0; y < worldSize; y += spreadDistance) {
                results.add(new Point(x + (Math.random() * spreadDistance * bias),
                                      y + (Math.random() * spreadDistance * bias)));
            }
        }

        return results;
    }

    /**
     * Returns as many roughly-evenly-spaced points as will fit in the world.
     * @param spreadDistance
     * @param worldSize
     * @return List<Point>
     */
    public static List<Point> yieldAllPoints(double spreadDistance, double worldSize) {
        return yieldAllPoints(spreadDistance, worldSize, DEFAULT_BIAS);
    }

    /**
     * Returns a random selection of numPoints points out of the set of as many roughly-evenly-spaced points as will fit in the world.
     * That is, returns numPoints points that are highly likely not to be particularly close to each other and to be well spread out.
     * bias is how strongly the points resist ending up near each other by chance. 0: not at all; 1: enough to always result in a perfect grid; 0.5: default
     * Points are *guaranteed* to be no closer than spreadDistance * bias units apart.
     * @param numPoints
     * @param spreadDistance
     * @param worldSize
     * @param bias
     * @return List<Point>
     */
    public static List<Point> yieldPoints(int numPoints, double spreadDistance, double worldSize, double bias) {
        List<Point> allResults = yieldAllPoints(spreadDistance, worldSize, bias);
        List<Point> results = new ArrayList<>();

        if (numPoints >= allResults.size()) {
            return allResults;
        }

        for (int i = 0; i < numPoints; i++) {
            int index = (int) (Math.random() * allResults.size());
            results.add(allResults.remove(index));
        }

        return results;
    }

    /**
     * Returns a random selection of numPoints points out of the set of as many roughly-evenly-spaced points as will fit in the world.
     * That is, returns numPoints points that are highly likely not to be particularly close to each other and to be well spread out.
     * @param numPoints
     * @param spreadDistance
     * @param worldSize
     * @return List<Point>
     */
    public static List<Point> yieldPoints(int numPoints, double spreadDistance, double worldSize) {
        return yieldPoints(numPoints, spreadDistance, worldSize, DEFAULT_BIAS);
    }

    /**
     * Utility method to round the coordinates of every point in a list of them to ints.
     * @param points
     * @return List<Point>
     */
    public static List<Point> roundPoints(List<Point> points) {
        List<Point> roundedPoints = new ArrayList<>();

        for (Point point : points) {
            roundedPoints.add(new Point((int) point.getX(), (int) point.getY()));
        }

        return roundedPoints;
    }

    /* for testing if SpacedRandom is functioning properly */
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("SpacedRandom", 256, 256);
        
        int numPoints = 300;
        double minDistance = 20;
        double bias = 0.3;

        List<Point> spacedPoints = yieldPoints(numPoints, minDistance, 256, bias);
        
        int x = 0;
        int y = 0;
        while (x < canvas.getWidth()) {
            while (y < canvas.getHeight()) {
                canvas.add(new Rectangle(new Point(x, y), new Point(minDistance, minDistance)));
                y += minDistance;
            }
            x += minDistance;
            y = 0;
        }

        for (Point point : spacedPoints) {
            canvas.add(new Rectangle(point, Point.ONE_ONE));
        }
        System.out.println(spacedPoints);
    }
}
