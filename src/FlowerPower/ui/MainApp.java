package FlowerPower.ui;

import java.awt.Color;
import FlowerPower.model.Collectible;
import FlowerPower.model.Obstacle;
import FlowerPower.model.Explorer;
import FlowerPower.model.Gameboard;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

//Maren is doing ui
/**
 * backround
 * shapes for player, collect and obstical
 */
public class MainApp {

    //variables
    Gameboard game;
    public static final double CANVAS_SIZE = 256;
    private CanvasWindow canvas;
    private Color backgroundColor = new Color(34, 125, 73);


    private MainApp(){
        //constructor
        // add obstacles, explorer, collectables OR have Gameboard.java implement those and then just add a new Gameboard object
        canvas = new CanvasWindow("FlowerPower", (int) CANVAS_SIZE, (int) CANVAS_SIZE);
        canvas.setBackground(backgroundColor);
        game = new Gameboard(16, 16); // 256 by 256 for full gameboard
        //canvas.add(game.getBoard());
    }

    public static void main(String[] args) {
        new MainApp().play();
    }

    private void play(){
        // idk if we need this but just setting things up; we do
        setUpGame();
    }

    private void setUpGame() {
        //this will set up and reset the layout for the beginning of a round.

    }

}
