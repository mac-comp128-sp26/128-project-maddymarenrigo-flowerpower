package FlowerPower.model;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import FlowerPower.model.Datatypes.Graph;
import edu.macalester.graphics.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Gameboard {

    Graph graph;
    GraphicsGroup board;
    List<List<CellType>> cells; // get a cell with cells.get(y).get(x)

    // grid gameboard dimensions
    private int row; // number of rows (height)
    private int col; // number of cols (width)

    // grid cell dimensions
    private Double cellWid;
    private Double cellLen;

    // camera position
    private Point cameraPosition;

    public Gameboard(int row, int col) {
        board = new GraphicsGroup();
        cells = new ArrayList<>();
        
        /* set up cells to have the right number of entries (all empty for now; will be generated later) */
        for (int i = 0; i < row; i++) {
            List<CellType> tempList = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                tempList.add(CellType.EMPTY);
            }
            cells.add(tempList);
        }

        this.row = row;
        this.col = col;

        cellLen = 32.0; // 32 x 32 pixels
        cellWid = 32.0; // 32 x 32 pixels
    }

    /**
     * Sets up the visual gameboard in board
     */
    public void setup() {
        for (int r = 0; r < row; r++){
            for (int c = 0; c < col; c++){
                Rectangle cell = new Rectangle(c*cellLen, r*cellWid, cellWid, cellLen);
                board.add(cell);
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
        Point position = new Point(x * cellLen, y * cellWid);
        position.subtract(cameraPosition);
        return position;
    }

    /**
     * Takes in the explorer's current position onscreen and updates the camera accordingly to follow it.
     * @param explorerOnscreenPosition
     */
    public void updateCamera(Point explorerOnscreenPosition) {
        cameraPosition.add(explorerOnscreenPosition.subtract(cameraPosition).scale(1.0/5.0)); // moves the camera 1/5 of the way to centering the explorer each frame (this is actually kinda fast)
    }

    /**
     * Returns the CellType of the cell at position (x, y) or CellType.OBSTACLE if that's outside the world
     * 
     * @param x
     * @param y
     * @return CellType
     */
    public CellType getCellAt(int x, int y) {
        if (x < 0 || y < 0 || x >= col || y >= row) {
            return CellType.OBSTACLE;
        }
        return cells.get(y).get(x);
    }

    /**
     * Sets the cell at position (x, y) to CellType type
     * 
     * @param x
     * @param y
     * @param type
     */
    public void setCellAt(int x, int y, CellType type) {
        if (x < 0 || y < 0 || x >= col || y >= row) {
            return;
        }
        cells.get(y).set(x, type);
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
     * Returns the constructed visual board
     *
     * @return the GraphicsGroup board object
     */
    public GraphicsGroup getBoard() {
        return board;
    }

}
