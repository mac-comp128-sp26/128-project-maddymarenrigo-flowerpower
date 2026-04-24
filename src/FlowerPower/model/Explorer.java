package FlowerPower.model;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class Explorer {

    public GraphicsGroup icon;
    private int score;
    private Gameboard gameboard;
    private double speed;
    private double movementTimer;

    private int x; // world map position
    private int y; // world map position

    public Explorer(GraphicsGroup icon, Gameboard gameboard) {
        this.icon = icon;          
        this.gameboard = gameboard; 
        this.speed = 5.0;
        this.movementTimer = this.speed;
        this.x = 0;
        this.y = 0;
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
        icon.setCenter(newPosition);
        System.out.println(icon.getCenter());
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