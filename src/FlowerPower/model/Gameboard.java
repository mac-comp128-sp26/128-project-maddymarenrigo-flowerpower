package FlowerPower.model;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import FlowerPower.model.Datatypes.Graph;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Gameboard {

    Graph graph;
    Map<Point, GraphicsObject> board; // maps world map coordinates to the GraphicsObject for the tile
    List<List<CellType>> cells; // get a cell with cells.get(y).get(x)

    Map<CellType, Color> cellDisplayColors; // temporary for temp graphics; will replace this with a map from cell types to graphics to display

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
        cellDisplayColors = new HashMap<CellType, Color>();
        cellDisplayColors.put(CellType.EMPTY, new Color(0, 0, 0, 0));
        cellDisplayColors.put(CellType.BUSH, new Color(21, 115, 46));
        cellDisplayColors.put(CellType.ROCK, new Color(92, 92, 92));
        cellDisplayColors.put(CellType.TREE, new Color(94, 36, 8));
        cellDisplayColors.put(CellType.WALL, new Color(212, 126, 114));
        cellDisplayColors.put(CellType.FLOWER, new Color(255, 0, 212));
        cellDisplayColors.put(CellType.GEM, new Color(0, 102, 255));
        cellDisplayColors.put(CellType.MUSHROOM, new Color(181, 139, 98));


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

    /**
     * Sets up the visual gameboard in board (assuming the level has already been generated) and adds it to the canvas
     */
    public void setup() {
        for (int r = 0; r < row; r++){
            for (int c = 0; c < col; c++) {
                if (getCellAt(c, r) != CellType.EMPTY) {
                    Rectangle cell = new Rectangle(c*cellLen, r*cellWid, cellWid, cellLen);
                    cell.setFillColor(cellDisplayColors.get(getCellAt(c, r)));
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
}
