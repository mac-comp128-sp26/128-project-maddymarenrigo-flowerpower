package FlowerPower.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import FlowerPower.model.Datatypes.Dijkstra;
import FlowerPower.model.Datatypes.Graph;
import FlowerPower.model.Datatypes.SpacedRandom;
import FlowerPower.model.Datatypes.aStar;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Image;

public class Gameboard {

    Graph graph;
    Map<Point, GraphicsObject> board; // maps world map coordinates to the GraphicsObject for the tile
    List<List<CellType>> cells; // get a cell with cells.get(y).get(x)

    Map<CellType, String> cellDisplayColors; 

    // grid gameboard dimensions
    private int row; // number of rows (height)
    private int col; // number of cols (width)

    // grid cell dimensions
    private Double cellWid;
    private Double cellLen;

    // canvas to draw onto
    private CanvasWindow canvas;
    private GraphicsGroup worldLayer;

    // camera position
    private Point cameraPosition;
    private Point wishCameraPosition; // where the camera's easing to

    private List<Integer> path;

    // saved obstacles and collectibles
    private List<Point> obstaclRandomPoints;
    private List<Point> randomPoints;

    public Gameboard(int row, int col, CanvasWindow canvas, GraphicsGroup worldLayer) {
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

        cellDisplayColors.put(CellType.BUSH, "catBush.png"); 
        cellDisplayColors.put(CellType.ROCK, "rock.png"); 
        cellDisplayColors.put(CellType.TREE, "tree.png"); 
        cellDisplayColors.put(CellType.WALL, "brik.png"); 
        cellDisplayColors.put(CellType.FLOWER, "flower.png"); 
        cellDisplayColors.put(CellType.GEM, "gem-export.png"); 
        cellDisplayColors.put(CellType.MUSHROOM, "mushroom1.png"); 
        cellDisplayColors.put(CellType.PATH, "path.png");


        this.row = row;
        this.col = col;

        cellLen = 32.0; // 32 x 32 pixels
        cellWid = 32.0; // 32 x 32 pixels

        this.canvas = canvas;
        this.worldLayer = worldLayer;
        this.obstaclRandomPoints = new ArrayList<>();
        this.randomPoints = new ArrayList<>();

        cameraPosition = new Point(0, 0);

        this.path = new ArrayList<>();
    }

    /**
     * Everything that needs to happen every single frame.
     */
    public void oneFrame() {
        
    }

    private void setTile(int x, int y, CellType CellType) {
        if(x < 0 || x > 256) {
            return;
        } else if(y < 0 || y > 256) {
            return;
        }
        cells.get(y).set( x, CellType);
    }

    private void setObstaclesInLine(Point top, CellType CellType) {
        if(Math.random() < 0.5) {
            setTile((int) top.getX(), (int) top.getY(), CellType);
            setTile((int) top.getX(), (int) top.getY() + 1, CellType);
            setTile((int) top.getX(), (int) top.getY() + 2, CellType);
            setTile((int) top.getX(), (int) top.getY() + 3, CellType);
            setTile((int) top.getX(), (int) top.getY() + 4, CellType);

        } else {
            setTile((int) top.getX(), (int) top.getY(), CellType);
            setTile((int) top.getX() + 1, (int) top.getY(), CellType);
            setTile((int) top.getX() + 2, (int) top.getY(), CellType);
            setTile((int) top.getX() + 3, (int) top.getY(), CellType);
            setTile((int) top.getX() + 4, (int) top.getY(), CellType);
        }
    }
    
    private void setTrees(Point top) {
        if(Math.random() < 0.5) {
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
        obstaclRandomPoints = SpacedRandom.roundPoints(SpacedRandom.yieldPoints(150, 20, 256));
        for(int i = 0; i < 150; i++) {
            Point top = obstaclRandomPoints.get(i);
            if(i < 50) {
                setObstaclesInLine(top, CellType.BUSH);
            } else if(i < 100) {
                setTrees(top);
            } else {
                setObstaclesInLine(top, CellType.ROCK);
            }
        }
        randomPoints = SpacedRandom.roundPoints(SpacedRandom.yieldPoints(45, 20, 256));
        for(int i = 0; i < 45; i++) {
            Point topPoint = randomPoints.get(i);
            if(i < 25) {
                cells.get((int) topPoint.getY()).set((int) topPoint.getX(), CellType.FLOWER);
            } else if(i < 40) {
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
                    worldLayer.add(cell);
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
            worldLayer.remove(board.get(new Point(x, y)));
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
            worldLayer.remove(board.get(new Point(x, y)));
            board.remove(new Point(x, y));
            Image cell = new Image(x*cellLen, y*cellWid);
            cell.setImagePath(cellDisplayColors.get(CellType.PATH));
            board.put(new Point(x, y), cell);
            worldLayer.add(cell);
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


    /**
     * Returns a Graph object of the gameboard
     * 
     * @return a Graph of the gameboard
     */
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

    /**
     * Returns true if the player can walk on top of that cell
     * 
     * @return a boolean
     */
    private boolean isWalkable(CellType cell) {
        return cell == CellType.EMPTY || cell == CellType.FLOWER
            || cell == CellType.GEM   || cell == CellType.MUSHROOM;
    }

    /**
     * Returns an int for the node of the graph the corresponds to the (x,y) coordinates
     * 
     * @return an int representing a graph node index number 
     */
    public int toIndex(int x, int y) {
        return y * col + x; 
    }

    /**
     * Returns an int [] of the graph's coordinates from a graph node index
     * 
     * @return an int[] representing a graph node in (x,y) format
     */
    public int[] toCoords(int index) { 
        return new int[]{ index % col, index / col }; 
    }

    /**
     * 
     * Shows up the path to the collecitble
     * 
     */
    public void showPath(int startX, int startY, int goalX, int goalY, PathMode name) {
        if (!path.isEmpty()){
            for (int i = 1; i < path.size() - 1; i++) {
                int[] coords = toCoords(path.get(i));
                int x = coords[0];
                int y = coords[1];
                
                clearCellAt(x, y);
            }
            path.clear();
        }

        Graph g = buildGraph();

        // selects correct path mode
        if (name == PathMode.ASTAR){
            aStar astar = new aStar(g);
            path = astar.path(toIndex(startX, startY), toIndex(goalX, goalY));
        } else if (name == PathMode.DJ) {
            Dijkstra dj = new Dijkstra(g);
            path = dj.dijkstra(toIndex(startX, startY), toIndex(goalX, goalY));
        } else {
            for (int i = 1; i < path.size() - 1; i++) {
                int[] coords = toCoords(path.get(i));
                int x = coords[0];
                int y = coords[1];
                
                clearCellAt(x, y);
            }
            path.clear();
        }

        for (int i = 1; i < path.size() - 1; i++) {
            int[] coords = toCoords(path.get(i));
            int x = coords[0];
            int y = coords[1];

            clearCellAt(x, y);

            cells.get(y).set(x, CellType.PATH);

            Point screenPos = getOnscreenPosition(x, y);
            Image cell = new Image(x*cellLen, y*cellWid);
            cell.setImagePath(cellDisplayColors.get(CellType.PATH));
            cell.setPosition(screenPos.getX(), screenPos.getY());
            board.put(new Point(x, y), cell);
            worldLayer.add(cell);

            cells.get(y).set(x, CellType.PATH);
        }
        
    }

    /**
     * 
     * Finds the closest collectible
     * 
     */
    public int[] findClosest(CellType target, double fromX, double fromY) {
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
