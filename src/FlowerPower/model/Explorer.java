package FlowerPower.model;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class Explorer {

    public GraphicsGroup icon;
    private int score;
    private Gameboard gameboard;
    private double speed;

    private int x; // world map position
    private int y; // world map position

    public Explorer(GraphicsGroup icon, Gameboard gameboard) {
        this.icon = icon;          
        this.gameboard = gameboard; 
        this.speed = 5.0;
        this.x = 0;
        this.y = 0;
    }

    // --- movement methods ---

    public void moveUp() {
        icon.moveBy(0, -speed);
    }

    public void moveDown() {
        icon.moveBy(0, speed);
    }

    public void moveLeft() {
        icon.moveBy(-speed, 0);
    }

    public void moveRight() {
        icon.moveBy(speed, 0);
    }

    public void updateOnscreenPosition() {
        
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

    public int getScore() {
        return score;
    }
}