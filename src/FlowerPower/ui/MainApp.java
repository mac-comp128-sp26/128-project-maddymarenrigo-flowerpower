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
    public static final double TILE_SIZE = 32;
    private CanvasWindow canvas;
    private Color backgroundColor = new Color(34, 125, 73);
    Explorer explorer;
    public Rectangle explorerIcon;
 
    private MainApp() {
        //constructor
        // add obstacles, explorer, collectables OR have Gameboard.java implement those and then just add a new Gameboard object
        canvas = new CanvasWindow("FlowerPower", (int) CANVAS_SIZE, (int) CANVAS_SIZE);
        canvas.setBackground(backgroundColor);
        game = new Gameboard(16, 16); // 256 by 256 for full gameboard
        explorer = new Explorer(new GraphicsGroup(), game);
        explorerIcon = new Rectangle(0, 0, (int) TILE_SIZE, (int) TILE_SIZE);
        explorerIcon.setFillColor(Color.WHITE);
        explorer.icon.add(explorerIcon); 
    }

    public static void main(String[] args) {
        new MainApp().play();
    }

    private void play() {
        // idk if we need this but just setting things up; we do
        setUpGame();
    }

    private void setUpGame() {
        //this will set up and reset the layout for the beginning of a round.
        canvas.removeAll();
        //adding to canvas
        canvas.add(game.getBoard());
        canvas.add(explorer.icon);
        // explorer movement
        canvas.onKeyDown(key -> {
            switch (key.getKey()) {
                case UP_ARROW: explorer.moveUp();
                case DOWN_ARROW: explorer.moveDown();
                case LEFT_ARROW: explorer.moveLeft();
                case RIGHT_ARROW: explorer.moveRight();
                default:
                    break;
            }
        });
    }

}
