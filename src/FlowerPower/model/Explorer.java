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
    public static int totalScore;

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
            movementTimer = speed;
            if (!isBlocked(x, y - 1)) {
                y--;
                handleCollectible(x, y);
            }
        }
        updateOnscreenPosition();
    }

    public void moveDown() {
        movementTimer--;
        if (movementTimer <= 0) {
            movementTimer = speed;
            if (!isBlocked(x, y + 1)) {
                y++;
                handleCollectible(x, y);
            }
        }
        updateOnscreenPosition();
    }

    public void moveLeft() {
        movementTimer--;
        if (movementTimer <= 0) {
            movementTimer = speed;
            if (!isBlocked(x - 1, y)) {
                x--;
                handleCollectible(x, y);
            }
        }
        updateOnscreenPosition();
    }

    public void moveRight() {
        movementTimer--;
        if (movementTimer <= 0) {
            movementTimer = speed;
            if (!isBlocked(x + 1, y)) {
                x++;
                handleCollectible(x, y);
            }
        }
        updateOnscreenPosition();
    }

    public void updateOnscreenPosition() {
        Point newPosition = gameboard.getOnscreenPosition(x, y);
        icon.setPosition(newPosition);

        rowPos = y;
        colPos = x;
    }

    /**
     * Returns true if the cell at (x, y) is an obstacle the explorer can't walk through.
     */
    private boolean isBlocked(int x, int y) {
        CellType cell = gameboard.getCellAt(x, y);
        return cell == CellType.WALL
            || cell == CellType.BUSH
            || cell == CellType.TREE
            || cell == CellType.ROCK;
    }

    private void handleCollectible(int x, int y) {
        CellType cell = gameboard.getCellAt(x, y);
        switch (cell) {
            case FLOWER:
                flowersCollected++;
                totalScore += FLOWER_POINTS;
                gameboard.clearCellAt(x, y);
                break;
            case MUSHROOM:
                mushroomsCollected++;
                totalScore += MUSH_POINTS;
                gameboard.clearCellAt(x, y);
                break;
            case GEM:
                gemsCollected++;
                totalScore += GEM_POINTS;
                gameboard.clearCellAt(x, y);
                break;
            default:
                break;
        }
    }

    // --- getters ---

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point getOnscreenPosition() {
        return icon.getCenter();
    }
}