package FlowerPower.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import FlowerPower.model.Datatypes.Graph;
import FlowerPower.model.Datatypes.SpacedRandom;
import FlowerPower.model.Datatypes.aStar;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Image;

public class Gameboard {

    Graph graph;
    Map<Point, GraphicsObject> board; // maps world map coordinates to the GraphicsObject for the tile
    List<List<CellType>> cells; // get a cell with cells.get(y).get(x)

    Map<CellType, String> cellDisplayColors; // temporary for temp graphics; will replace this with a map from cell types to graphics to display
    
    // grid gameboard dimensions
    private int row; // number of rows (height)
    private int col; // number of cols (width)

    // grid cell dimensions
    private Double cellWid;
    private Double cellLen;

    // canvas to draw onto
    private CanvasWindow canvas;

    // camera position
    private Point cameraPosition;
    private Point wishCameraPosition; // where the camera's easing to

    public Gameboard(int row, int col, CanvasWindow canvas) {
        board = new HashMap<>();
        cells = new ArrayList<>();
        
        /* set up cells to have the right number of entries (all empty for now; will be generated later) */
        for (int i = 0; i < row; i++) {
            List<CellType> tempList = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                tempList.add(CellType.EMPTY);
            }
            cells.add(tempList);
        }

        /* set up cellDisplayColors */
        cellDisplayColors = new HashMap<CellType, String>();

        cellDisplayColors.put(CellType.BUSH, "catBush.png"); // Color(21, 115, 46)
        cellDisplayColors.put(CellType.ROCK, "rock.png"); // Color(92, 92, 92)
        cellDisplayColors.put(CellType.TREE, "tree.png"); // Color(94, 36, 8)
        cellDisplayColors.put(CellType.WALL, "brik.png"); // Color(212, 126, 114)
        cellDisplayColors.put(CellType.FLOWER, "flower.png"); // Color(255, 0, 212)
        cellDisplayColors.put(CellType.GEM, "gem-export.png"); // Color(0, 102, 255)
        cellDisplayColors.put(CellType.MUSHROOM, "mushroom1.png"); // Color(181, 139, 98)
        cellDisplayColors.put(CellType.PATH, "path.png");


        this.row = row;
        this.col = col;

        cellLen = 32.0; // 32 x 32 pixels
        cellWid = 32.0; // 32 x 32 pixels

        this.canvas = canvas;

        cameraPosition = new Point(0, 0);
    }

    /**
     * Everything that needs to happen every single frame.
     */
    public void oneFrame() {
        
    }

    private void setTile(int x, int y, CellType CellType) {
        //System.out.println("set tile reached");
        if(x < 0 || x > 256) {
            return;
        } else if(y < 0 || y > 256) {
            return;
        }
        cells.get(y).set( x, CellType);
        //System.out.println("set the tile");
    }

    private void setObstaclesInLine(Point top, CellType CellType) {
        if(Math.random() < 0.5) {
            setTile((int) top.getX(), (int) top.getY(), CellType);
            setTile((int) top.getX(), (int) top.getY() + 1, CellType);
            setTile((int) top.getX(), (int) top.getY() + 2, CellType);
            setTile((int) top.getX(), (int) top.getY() + 3, CellType);
            setTile((int) top.getX(), (int) top.getY() + 4, CellType);
            // cells.get((int) top.getY()).set((int) top.getX(), CellType);
            // cells.get((int) top.getY()).set((int) top.getX() + 1, CellType);
            // cells.get((int) top.getY()).set((int) top.getX() + 2, CellType);
            // cells.get((int) top.getY()).set((int) top.getX() + 3, CellType);
            // cells.get((int) top.getY()).set((int) top.getX() + 4, CellType);
        } else {
            setTile((int) top.getX(), (int) top.getY(), CellType);
            setTile((int) top.getX() + 1, (int) top.getY(), CellType);
            setTile((int) top.getX() + 2, (int) top.getY(), CellType);
            setTile((int) top.getX() + 3, (int) top.getY(), CellType);
            setTile((int) top.getX() + 4, (int) top.getY(), CellType);
            // cells.get((int) top.getY()).set((int) top.getX(), CellType);
            // cells.get((int) top.getY() + 1).set((int) top.getX(), CellType);
            // cells.get((int) top.getY() + 2).set((int) top.getX(), CellType);
            // cells.get((int) top.getY() + 3).set((int) top.getX(), CellType);
            // cells.get((int) top.getY() + 4).set((int) top.getX(), CellType);
        }
    }
    
    private void setTrees(Point top) {
        if(Math.random() < 0.5) {
            // cells.get((int) top.getY()).set((int) top.getX(), CellType.TREE);
            // cells.get((int) top.getY()).set((int) top.getX() - 1, CellType.TREE);
            // cells.get((int) top.getY()).set((int) top.getX() + 2, CellType.TREE);
            // cells.get((int) top.getY() - 1).set((int) top.getX() + 3, CellType.TREE);
            // cells.get((int) top.getY() + 3).set((int) top.getX() - 4, CellType.TREE);
            // cells.get((int) top.getY() - 5).set((int) top.getX() + 4, CellType.TREE);
            // cells.get((int) top.getY() + 2).set((int) top.getX() - 2, CellType.TREE);
            // cells.get((int) top.getY() + 4).set((int) top.getX(), CellType.TREE);
            // cells.get((int) top.getY() - 3).set((int) top.getX(), CellType.TREE);
            // cells.get((int) top.getY() - 1).set((int) top.getX(), CellType.TREE);
            setTile((int) top.getX(), (int) top.getY(), CellType.TREE);
            setTile((int) top.getX() - 1, (int) top.getY(), CellType.TREE);
            setTile((int) top.getX() + 2, (int) top.getY(), CellType.TREE);
            setTile((int) top.getX() + 3, (int) top.getY() - 1, CellType.TREE);
            setTile((int) top.getX() - 4, (int) top.getY() + 3, CellType.TREE);
            setTile((int) top.getX() + 4, (int) top.getY() - 5, CellType.TREE);
            setTile((int) top.getX() - 2, (int) top.getY() + 2, CellType.TREE);
            setTile((int) top.getX(), (int) top.getY() + 4, CellType.TREE);
            setTile((int) top.getX(), (int) top.getY() - 3, CellType.TREE);
            setTile((int) top.getX(), (int) top.getY() - 1, CellType.TREE);
            
        } else {
            // cells.get((int) top.getY()).set((int) top.getX(), CellType.TREE);
            // cells.get((int) top.getY()).set((int) top.getX() - 2, CellType.TREE);
            // cells.get((int) top.getY()).set((int) top.getX() + 4, CellType.TREE);
            // cells.get((int) top.getY() - 1).set((int) top.getX() + 1, CellType.TREE);
            // cells.get((int) top.getY() + 2).set((int) top.getX() - 1, CellType.TREE);
            // cells.get((int) top.getY() - 3).set((int) top.getX() + 5, CellType.TREE);
            // cells.get((int) top.getY() + 3).set((int) top.getX() - 1, CellType.TREE);
            // cells.get((int) top.getY() + 1).set((int) top.getX() + 4, CellType.TREE);
            // cells.get((int) top.getY() - 4).set((int) top.getX(), CellType.TREE);
            // cells.get((int) top.getY() - 5).set((int) top.getX(), CellType.TREE);
            setTile((int) top.getX(), (int) top.getY(), CellType.TREE);
            setTile((int) top.getX() - 2, (int) top.getY(), CellType.TREE);
            setTile((int) top.getX() + 4, (int) top.getY(), CellType.TREE);
            setTile((int) top.getX() + 1, (int) top.getY() - 1, CellType.TREE);
            setTile((int) top.getX() - 1, (int) top.getY() + 2, CellType.TREE);
            setTile((int) top.getX() - 3, (int) top.getY() + 5, CellType.TREE);
            setTile((int) top.getX() - 1, (int) top.getY() + 3, CellType.TREE);
            setTile((int) top.getX() + 4, (int) top.getY() + 1, CellType.TREE);
            setTile((int) top.getX(), (int) top.getY() - 4, CellType.TREE);
            setTile((int) top.getX(), (int) top.getY() - 5, CellType.TREE);
        }
    }

    //update cells
    public void generateBoard() {
        List<Point> obstaclRandomPoints = SpacedRandom.roundPoints(SpacedRandom.yieldPoints(90, 20, 256));
        for(int i = 0; i < 90; i++) {
            Point top = obstaclRandomPoints.get(i);
            if(i < 30) {
                setObstaclesInLine(top, CellType.BUSH);
            } else if(i < 60) {
                //setObstaclesInLine(top, CellType.TREE);
                setTrees(top);
            } else {
                setObstaclesInLine(top, CellType.ROCK);
            }
        }
        List<Point> randomPoints = SpacedRandom.roundPoints(SpacedRandom.yieldPoints(24, 20, 256));
        for(int i = 0; i < 24; i++) {
            Point topPoint = randomPoints.get(i);
            if(i < 10) {
                cells.get((int) topPoint.getY()).set((int) topPoint.getX(), CellType.FLOWER);
            } else if(i < 18) {
                cells.get((int) topPoint.getY()).set((int) topPoint.getX(), CellType.MUSHROOM);
            } else {
                cells.get((int) topPoint.getY()).set((int) topPoint.getX(), CellType.GEM);
            }
        }
    }

    /**
     * Sets up the visual gameboard in board (assuming the level has already been generated) and adds it to the canvas
     */
    public void setup() {
        for (int r = 0; r < row; r++){
            for (int c = 0; c < col; c++) {
                if (getCellAt(c, r) != CellType.EMPTY) {
                    Image cell = new Image(c*cellLen, r*cellWid);
                    cell.setImagePath(cellDisplayColors.get(getCellAt(c, r)));
                    board.put(new Point(c, r), cell);
                    canvas.add(cell);
                }
            }
        }
    }

    /**
     * Gets the visual position onscreen of the center of the cell at map coordinates (x, y).
     * @param x
     * @param y
     * @return Point
     */
    public Point getOnscreenPosition(int x, int y) {
        Point position = getRawPosition(x, y);
        position = position.subtract(cameraPosition);
        return position;
    }

    // gets the visual position of where the cell would be if the camera was at the origin
    private Point getRawPosition(int x, int y) {
        return new Point(x * cellLen, y * cellWid);
    }

    /**
     * Takes in the explorer's current position in the world map and updates the camera accordingly to follow it.
     * @param explorerOnscreenPosition
     */
    public void updateCamera(int x, int y) {
        wishCameraPosition = getRawPosition(x, y).subtract(new Point(canvas.getWidth() / 2.0, canvas.getHeight() / 2.0));
        Point delta = wishCameraPosition.subtract(cameraPosition).scale(1.0/20.0);
        cameraPosition = cameraPosition.add(delta);
        for (Entry<Point, GraphicsObject> entry : board.entrySet()) {
            entry.getValue().moveBy(delta.scale(-1));
        }
    }

    /**
     * Returns the CellType of the cell at position (x, y) or CellType.WALL if that's outside the world
     * 
     * @param x
     * @param y
     * @return CellType
     */
    public CellType getCellAt(int x, int y) {
        if (x < 0 || y < 0 || x >= col || y >= row) {
            return CellType.WALL;
        }
        return cells.get(y).get(x);
    }

    /**
     * Empties the cell at position (x, y)
     * 
     * @param x
     * @param y
     */
    public void clearCellAt(int x, int y) {
        if (x < 0 || y < 0 || x >= col || y >= row) {
            return;
        }
        cells.get(y).set(x, CellType.EMPTY);
        if (board.get(new Point(x, y)) != null) {
            canvas.remove(board.get(new Point(x, y)));
            board.remove(new Point(x, y));
        }
    }
    
    /**
     * Marks the cell at (x, y) on the world map as part of the current path
     * @param x
     * @param y
     */
    public void markCellAt(int x, int y) {
        if (x < 0 || y < 0 || x >= col || y >= row) {
            return;
        }
        cells.get(y).set(x, CellType.PATH);
        if (board.get(new Point(x, y)) != null) {
            canvas.remove(board.get(new Point(x, y)));
            board.remove(new Point(x, y));
            Image cell = new Image(x*cellLen, y*cellWid);
            cell.setImagePath(cellDisplayColors.get(CellType.PATH));
            board.put(new Point(x, y), cell);
            canvas.add(cell);
        }
    }

    /**
     * Unmarks the cell at (x, y) so it's empty again
     * @param x
     * @param y
     */
    public void unmarkCellAt(int x, int y) {
        if (x < 0 || y < 0 || x >= col || y >= row) {
            return;
        }
        if (cells.get(y).get(x) == CellType.PATH) {
            clearCellAt(x, y);
        }
    }

    /**
     * Returns a list of the CellTypes of all of the neighbors of the cell at position (x, y) in mathematical order from 0° to 360°
     * 
     * @param x
     * @param y
     * @return List<CellType>
     */
    public List<CellType> getNeighbors(int x, int y) {
        List<CellType> neighbors = new ArrayList<>();
        /* go through neighbors in mathematical order from 0° to 360° */
        neighbors.add(getCellAt(x + 1, y));
        neighbors.add(getCellAt(x, y + 1));
        neighbors.add(getCellAt(x - 1, y));
        neighbors.add(getCellAt(x, y - 1));
        return neighbors;
    }

    /**
     * Returns the entire array of cells
     * 
     * @return List<List<CellType>>
     */
    public List<List<CellType>> getCells() {
        return cells;
    }


    public Graph buildGraph() {
        Graph g = new Graph(row * col);

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                g.setPosition(toIndex(c, r), c * cellWid, r * cellLen); // create node for each grid cell in gameboard
            }
        }

        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}}; // 4 neighbors

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (!isWalkable(getCellAt(c, r))) continue; // skip unwalkable cells (obstacles)

                for (int[] d : directions) {
                    int nieghborcCol = c + d[0];
                    int nieghborcRow = r + d[1];

                    if (nieghborcRow < 0 || nieghborcRow >= row || nieghborcCol < 0 || nieghborcCol >= col) continue; // skip out-of-bounds cells
                    if (!isWalkable(getCellAt(nieghborcCol, nieghborcRow))) continue; // skip unwalkable neighbor cells (obstacles)

                    g.addEdge(toIndex(c, r), toIndex(nieghborcCol, nieghborcRow), 1.0);
                }
            }
        }
        return g;
    }

    private boolean isWalkable(CellType cell) {
        return cell == CellType.EMPTY || cell == CellType.FLOWER
            || cell == CellType.GEM   || cell == CellType.MUSHROOM;
    }

    public int toIndex(int x, int y) {
        return y * col + x; 
    }

    public int[] toCoords(int index) { 
        return new int[]{ index % col, index / col }; 
    }

    /**
     * 
     * Shows up the path to the collecitble
     * 
     */
    public void showPath(int startX, int startY, int goalX, int goalY) {
        Graph g = buildGraph();
        aStar astar = new aStar(g);
        List<Integer> path = astar.path(toIndex(startX, startY), toIndex(goalX, goalY));

        for (int i = 1; i < path.size() - 1; i++) {
            int[] coords = toCoords(path.get(i));
            int x = coords[0];
            int y = coords[1];
            // however we are visually displaying the path
            // ex:
            //markCellAt(coords[0], coords[1]);
            if (board.get(new Point(x, y)) != null) {
                canvas.remove(board.get(new Point(x, y)));
                board.remove(new Point(x, y));
            }

            cells.get(y).set(x, CellType.PATH);

            Point screenPos = getOnscreenPosition(x, y);
            Image cell = new Image(x*cellLen, y*cellWid);
            cell.setImagePath(cellDisplayColors.get(CellType.WALL));
            cell.setPosition(screenPos.getX(), screenPos.getY());
            board.put(new Point(x, y), cell);
            canvas.add(cell);

            cells.get(y).set(x, CellType.WALL);
        }
        
    }

    public int[] findClosest(String name, double fromX, double fromY) {
        CellType target;
        switch (name) {
            case "flower":   target = CellType.FLOWER;   break;
            case "mushroom": target = CellType.MUSHROOM; break;
            case "gem":      target = CellType.GEM;      break;
            default: return null;
        }

        int[] closest = null;
        double bestDist = Double.MAX_VALUE;

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (cells.get(r).get(c) == target) {
                    double dist = Math.sqrt(Math.pow(c - fromX, 2) + Math.pow(r - fromY, 2));
                    if (dist < bestDist) {
                        bestDist = dist;
                        closest = new int[]{c, r};
                    }
                }
            }
        }

        return closest;
    }

}
