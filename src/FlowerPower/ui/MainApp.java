package FlowerPower.ui;

import java.awt.Color;
import java.awt.RenderingHints.Key;
import java.util.Set;

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
    public static final double TILE_SIZE = 32;
    private CanvasWindow canvas;
    private Color backgroundColor = new Color(34, 125, 73);
    Explorer explorer;
   
 
    private MainApp() {
        //constructor
        // add obstacles, explorer, collectables OR have Gameboard.java implement those and then just add a new Gameboard object
        canvas = new CanvasWindow("FlowerPower", (int) CANVAS_SIZE, (int) CANVAS_SIZE);
        canvas.setBackground(backgroundColor);
        game = new Gameboard(256, 256, canvas); // 256 by 256 for full gameboard
        explorer = new Explorer(new GraphicsGroup(), game);
        
        //explorer.icon.add(explorerIcon); 
    }

    public static void main(String[] args) {
        new MainApp().play();
    }

    private void play() {
        // idk if we need this but just setting things up; we do
        setUpGame();
    }

    /**
     * Does everything that needs to happen on a particular frame.
     */
    private void oneFrame() {
        // Move player
        Set<edu.macalester.graphics.events.Key> keys = canvas.getKeysPressed();
        if (keys.contains(edu.macalester.graphics.events.Key.UP_ARROW)) explorer.moveUp();
        if (keys.contains(edu.macalester.graphics.events.Key.DOWN_ARROW)) explorer.moveDown();
        if (keys.contains(edu.macalester.graphics.events.Key.RIGHT_ARROW)) explorer.moveRight();
        if (keys.contains(edu.macalester.graphics.events.Key.LEFT_ARROW)) explorer.moveLeft();

        game.oneFrame();
        explorer.oneFrame();
    }

    private void setUpGame() {
        //this will set up and reset the layout for the beginning of a round.
        canvas.removeAll();
        //adding to canvas
        game.generateBoard(); //(putting the stuff in the cells array)
        game.setup();
        canvas.add(explorer.icon);
        

        // explorer movement
        canvas.animate(this::oneFrame);
    }

}
