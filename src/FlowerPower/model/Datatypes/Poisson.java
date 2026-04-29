package FlowerPower.model.Datatypes;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Poisson {
    // implements the Poisson disc sampling algorithm

    public static List<Point> yieldPoints(int numPoints, double minDistance, int worldSize, int samples) {

        double cellSize = minDistance / Math.sqrt(2); // size of cells in the grid this algorithm will use

        int[][] grid = new int[(int) Math.ceil(worldSize/minDistance)][(int) Math.ceil(worldSize/minDistance)]; // define the grid so that it fills up the entire world (though not at the same resolution as the world map)
        System.out.println(grid.length);
        System.out.println(grid[0].length);
        List<Point> unroundedPoints = new ArrayList<>(); // will hold all of the resultant points before rounding to the neareast world map space
        List<Point> spawnerPoints = new ArrayList<>();

        int pointsGenerated = 0;

        spawnerPoints.add(Point.ONE_ONE); // add an initial point for other points to try to spawn around

        while (!spawnerPoints.isEmpty() && pointsGenerated < numPoints) { // as long as *both* we haven't ran out of possible points to even spawn and we haven't generated enough points already
            int chosenSpawnerIndex = (int) (Math.random() * spawnerPoints.size());
            Point chosenSpawner = spawnerPoints.get(chosenSpawnerIndex); // set up spawner to start spawning points

            boolean candidateWasAccepted = false;

            for (int i = 0; i < samples; i++) { // try this samples times
                double spawnAngle = Math.random() * Math.PI * 2; // choose a random angle
                Point direction = new Point(Math.cos(spawnAngle), Math.sin(spawnAngle));
                Point candidatePoint = chosenSpawner.add(direction.scale(minDistance * (Math.random() + 1))); // put a new point in that direction from the current spawner
                System.out.println(candidatePoint.getX());
                System.out.println(candidatePoint.getY());
                if (pointIsValid(candidatePoint, worldSize, cellSize, minDistance, unroundedPoints, grid)) { // if it works
                    unroundedPoints.add(candidatePoint); // add the point in the places
                    spawnerPoints.add(candidatePoint);
                    grid[(int) (candidatePoint.getX() / minDistance)][(int) (candidatePoint.getY() / minDistance)] = unroundedPoints.size();
                    candidateWasAccepted = true;
                    System.out.println("candidate accepted");
                    pointsGenerated++;
                    break; // stop trying to spawn from this spawner and go back to choose another one
                }
            }

            if (!candidateWasAccepted) { // if nothing worked
                spawnerPoints.remove(chosenSpawnerIndex); // stop spawning from this spawner
            }
        }

        // points have now been generated

        List<Point> points = new ArrayList<>();
        for (Point point : unroundedPoints) { // populate a new list of all of the newly generated points with their positions rounded to a world space
            points.add(new Point((int) Math.round(point.getX()), (int) Math.round(point.getY())));
        }
        return points;
    }

    public static List<Point> yieldPoints(int numPoints, double minDistance, int worldSize) {
        return yieldPoints(numPoints, minDistance, worldSize, 40);
    }

    private static boolean pointIsValid(Point candidatePoint, int worldSize, double cellSize, double minDistance, List<Point> unroundedPoints, int[][] grid) {
        
        if (candidatePoint.getX() < 0 || candidatePoint.getX() >= worldSize || candidatePoint.getY() < 0 || candidatePoint.getY() >= worldSize) return false; // immediately not valid if it's outside of the world

        System.out.println("Cell size:");
        System.out.println(cellSize);

        System.out.println(candidatePoint.getX() / cellSize);
        System.out.println(candidatePoint.getY() / cellSize);

        int cellX = (int) (candidatePoint.getX() / cellSize); // find the cell in grid[][] this point is in
        int cellY = (int) (candidatePoint.getY() / cellSize);

        System.out.println(cellX);
        System.out.println(cellY);

        System.out.println("Grid size:");
        System.out.println(grid.length);
        System.out.println(grid[0].length);

        int searchStartX = Math.max(cellX - 3, 0); // set up bounds of search to confirm that no other points are too close
        int searchEndX = Math.min(cellX + 3, grid[0].length - 1); // and make sure these bounds don't exceed the bounds of the world map itself
        int searchStartY = Math.max(cellY - 3, 0);
        int searchEndY = Math.min(cellY + 3, grid.length - 1);

        System.out.println(new Point(searchStartX, searchStartY));
        System.out.println(new Point(searchEndX, searchEndY));

        for (int x = searchStartX; x <= searchEndX; x++) {
            for (int y = searchStartY; y <= searchEndY; y++) {
                int pointIndex = grid[x][y] - 1; // find the index into unroundedPoints at which the point in the grid space we're looking at is
                if (pointIndex != -1) { // as long as there's actually a point here
                    double distance = candidatePoint.subtract(unroundedPoints.get(pointIndex)).magnitude(); // calculate the distance from the point we're checking to the point we found
                    System.out.println("Distance:");
                    System.out.println(distance);
                    if (distance < minDistance) {
                        System.out.println("Returning");
                        return false; // if this point we're checking is too close to the candidatePoint then our point isn't valid
                    } 
                }
            }
        }
        return true; // if nothing came too close then we're good
    }

    /* for testing if Poisson is functioning properly */
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("FlowerPower", 256, 256);
        
        int numPoints = 300;
        double minDistance = 20;

        List<Point> poissonPoints = yieldPoints(numPoints, minDistance, 256);
        
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

        for (Point point : poissonPoints) {
            canvas.add(new Rectangle(point, Point.ONE_ONE));
        }
        System.out.println(poissonPoints);
    }
}
