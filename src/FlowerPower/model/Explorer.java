package FlowerPower.model;

import java.awt.Color;

import FlowerPower.ui.MainApp;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Explorer {
    private static final int FLOWER_POINTS = 10;
    private static final int MUSH_POINTS = 50;
    private static final int GEM_POINTS = 100;

    public GraphicsGroup icon;
    private Gameboard gameboard;
    private double speed;
    private double movementTimer;

    public static int flowersCollected;
    public static int gemsCollected;
    public static int mushroomsCollected;

    public static int rowPos;
    public static int colPos;

    private int x; // world map position
    private int y; // world map position


    public Explorer(GraphicsGroup icon, Gameboard gameboard) {
        this.icon = icon;          
        this.gameboard = gameboard; 
        this.speed = 5.0;
        this.movementTimer = this.speed;
        this.x = 0;
        this.y = 0;

        this.colPos = 0;
        this.rowPos = 0;

        this.flowersCollected = 0;
        this.gemsCollected = 0;
        this.mushroomsCollected = 0;

        //icon
        Rectangle explorerIcon = new Rectangle(0, 0, (int) MainApp.TILE_SIZE, (int) MainApp.TILE_SIZE);
        explorerIcon.setFillColor(Color.WHITE);
        icon.add(explorerIcon);
    }

    /**
     * Everything that needs to happen every single frame.
     */
    public void oneFrame() {
        gameboard.updateCamera(x, y);
        updateOnscreenPosition();
    }

    // --- movement methods ---

    public void moveUp() {
        movementTimer--;
        if (movementTimer <= 0) {
            y--;
            movementTimer = speed;
        }
        updateOnscreenPosition();
    }

    public void moveDown() {
        movementTimer--;
        if (movementTimer <= 0) {
            y++;
            movementTimer = speed;
        }
        updateOnscreenPosition();
    }

    public void moveLeft() {
        movementTimer--;
        if (movementTimer <= 0) {
            x--;
            movementTimer = speed;
        }
        updateOnscreenPosition();
    }

    public void moveRight() {
        movementTimer--;
        if (movementTimer <= 0) {
            x++;
            movementTimer = speed;
        }
        updateOnscreenPosition();
    }

    public void updateOnscreenPosition() {
        Point newPosition = gameboard.getOnscreenPosition(x, y);
        icon.setPosition(newPosition);

        rowPos = y;
        colPos = x;
    }

    public void handleCollisions(){
        // if collides with certain collectible object, increase respected score by the resepected points assigned
        // if collide with collectible object, explore picks it up and the object is removed from the gameboard visually and from the graph
        // if collide with obstacle, cannot continue moving "over it" --> movement blocked
    }

    // --- getters ---

    public double getX() {
        return x;
    }

    public double getY() {
        return x;
    }

    public Point getOnscreenPosition() {
        return icon.getCenter();
    }
}