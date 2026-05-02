package FlowerPower.model;

import java.awt.Color;

import FlowerPower.ui.MainApp;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Explorer {

    public GraphicsGroup icon;
    private Gameboard gameboard;
    private double speed;
    private double movementTimer;

    public static int flowersCollected;
    public static int gemsCollected;
    public static int mushroomsCollected;

    private int x; // world map position
    private int y; // world map position

    public Explorer(GraphicsGroup icon, Gameboard gameboard) {
        this.icon = icon;          
        this.gameboard = gameboard; 
        this.speed = 5.0;
        this.movementTimer = this.speed;
        this.x = 0;
        this.y = 0;

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