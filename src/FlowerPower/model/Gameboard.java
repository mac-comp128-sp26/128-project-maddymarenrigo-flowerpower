package FlowerPower.model;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Gameboard {

    GraphicsGroup board;

    // grid gameboard dimensions
    private int row;
    private int col;

    // grid cell dimensions
    private Double cellWid;
    private Double cellLen;

    public Gameboard(int row, int col){
        board = new GraphicsGroup();

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

    /**
     * Returns the constructed board
     *
     * @return the GraphicsGroup board object
     */
    public GraphicsGroup getBoard(){
        return board;
    }

}
