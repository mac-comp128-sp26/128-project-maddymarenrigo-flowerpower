package FlowerPower.model;

import edu.macalester.graphics.GraphicsGroup;

public class Explorer {

    private GraphicsGroup icon;
    private int score;
    private Gameboard gameboard;
    private double speed;

    public Explorer(GraphicsGroup icon, Gameboard gameboard) {
        this.icon = icon;          
        this.gameboard = gameboard; 
        this.speed = 5.0;         
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

    // --- getters ---

    public double getX() {
        return 0.0; //icon.getX(); 
    }

    public double getY() {
        return 0.0; //icon.getY(); 
    }

    public int getScore() {
        return score;
    }
}