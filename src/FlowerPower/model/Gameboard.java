package FlowerPower.model;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Gameboard {

    GraphicsGroup board;
    List<List<Integer>> cells;

    // grid gameboard dimensions
    private int row;
    private int col;

    // grid cell dimensions
    private Double cellWid;
    private Double cellLen;

    public Gameboard(int row, int col){
        board = new GraphicsGroup();
        cells = new ArrayList<>();

        row = this.row;
        col = this.col;

        cellLen = 32.0; // 32 x 32 pixels
        cellWid = 32.0; // 32 x 32 pixels
    }

    /**
     * Sets up the grid gameboard in board
     */
    public void setup(){
        for (int r = 0; r < row; r++){
            for (int c = 0; c < col; c++){
                Rectangle cell = new Rectangle(c*32, r*32, cellWid, cellLen);
                board.add(cell);
            }
        }
    }

    public Integer getCellAt(int x, int y) {
        return null;
    }

    public void setCellAt(int x, int y, Integer type) {
        
    }

    public List<Integer> getNeighbors(int x, int y) {
        return null;
    }


    public List<List<Integer>> getCells() {
        return cells;
    }

    /**
     * Returns the constructed board
     *
     * @return the GraphicsGroup board object
     */
    public GraphicsGroup getBoard(){
        return board;
    }

}
