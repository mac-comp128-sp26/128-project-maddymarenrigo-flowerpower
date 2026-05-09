package FlowerPower.ui;

import java.awt.Color;
import java.util.Set;

import FlowerPower.model.Explorer;
import FlowerPower.model.Gameboard;
import FlowerPower.model.Scoreboard;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;

/**
 * Runs the game
 */
public class MainApp {

    //variables
    Gameboard game;
    public static final double CANVAS_SIZE = 256;
    public static final double TILE_SIZE = 32;
    private CanvasWindow canvas;
    private Color backgroundColor = new Color(34, 125, 73);
    Explorer explorer;
    Scoreboard scoreboard;
    GraphicsGroup worldLayer = new GraphicsGroup();   // tiles + path
    GraphicsGroup entityLayer = new GraphicsGroup();  // explorer
    GraphicsGroup uiLayer = new GraphicsGroup();      // scoreboard
   
 
    private MainApp() {
        canvas = new CanvasWindow("FlowerPower", (int) CANVAS_SIZE*5, (int) CANVAS_SIZE*5);
        canvas.setBackground(backgroundColor);
        game = new Gameboard(256, 256, canvas, worldLayer);
        explorer = new Explorer(new GraphicsGroup(), game);
        scoreboard = new Scoreboard(canvas, game);

    }

    public static void main(String[] args) {
        new MainApp().play();
    }

    private void play() {
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
        
        // Reset movement timers if the player isn't moving
        if (! (keys.contains(edu.macalester.graphics.events.Key.UP_ARROW)
            || keys.contains(edu.macalester.graphics.events.Key.DOWN_ARROW)
            || keys.contains(edu.macalester.graphics.events.Key.RIGHT_ARROW)
            || keys.contains(edu.macalester.graphics.events.Key.LEFT_ARROW))) explorer.resetSpeed();
        game.oneFrame();
        explorer.oneFrame();
        scoreboard.updateBoard();
        scoreboard.setPosition(0, 0);
    }

    private void setUpGame() {
        //this will set up and reset the layout for the beginning of a round.
        canvas.removeAll();
        //adding to canvas
        game.generateBoard(); //(putting the stuff in the cells array)
        game.setup();
        entityLayer.add(explorer.icon);
        uiLayer.add(scoreboard);
        scoreboard.setPosition(0, 0);
        canvas.add(worldLayer); // board
        canvas.add(entityLayer); // player
        canvas.add(uiLayer); // scoreboard

        // explorer movement
        canvas.animate(this::oneFrame);
    }

}
