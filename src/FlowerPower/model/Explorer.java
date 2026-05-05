package FlowerPower.model;

import java.awt.Color;

import FlowerPower.ui.MainApp;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Explorer {
    private static final int FLOWER_POINTS = 10;
    private static final int MUSH_POINTS = 50;
    private static final int GEM_POINTS = 100;

    public GraphicsGroup icon;
    private Image iconImage;
    private Gameboard gameboard;
    private double speed;
    private final double BASE_SPEED = 4.0;
    private final double MAX_SPEED = 2.0;
    private final double ACCELERATION = 0.99;
    private double movementTimer;

    public static int flowersCollected;
    public static int gemsCollected;
    public static int mushroomsCollected;
    public static int totalScore;

    public static int rowPos;
    public static int colPos;

    private int animationState;
    private boolean movedAlready;

    private int x; // world map position
    private int y; // world map position


    public Explorer(GraphicsGroup icon, Gameboard gameboard) {
        this.icon = icon;          
        this.gameboard = gameboard;
        this.speed = BASE_SPEED;
        this.movementTimer = 0;
        this.movedAlready = false;
        this.x = 0;
        this.y = 0;

        this.colPos = 0;
        this.rowPos = 0;

        this.flowersCollected = 0;
        this.gemsCollected = 0;
        this.mushroomsCollected = 0;

        // icon
        characterSetup();
    }

    public void characterSetup(){
        iconImage = new Image(0, 0);

        this.animationState = 0;
        updateAnimationFrame();

        icon.add(iconImage);
    }

    /**
     * Everything that needs to happen every single frame.
     */
    public void oneFrame() {
        gameboard.updateCamera(x, y);
        updateOnscreenPosition();
        movedAlready = false;
    }

    // --- movement methods ---

    public void moveUp() {
        movementTimer--;
        if (movementTimer <= 0) {
            movementTimer = speed;
            updateSpeed();
            if (!isBlocked(x, y - 1)) {
                y--;
                updateAnimationWalking();
                handleCollectible(x, y);
            } else {
                updateAnimationBlocked();
            }
        }
        updateOnscreenPosition();
    }

    public void moveDown() {
        movementTimer--;
        if (movementTimer <= 0) {
            movementTimer = speed;
            updateSpeed();
            if (!isBlocked(x, y + 1)) {
                y++;
                updateAnimationWalking();
                handleCollectible(x, y);
            } else {
                updateAnimationBlocked();
            }
        }
        updateOnscreenPosition();
    }

    public void moveLeft() {
        movementTimer--;
        if (movementTimer <= 0) {
            movementTimer = speed;
            updateSpeed();
            if (!isBlocked(x - 1, y)) {
                x--;
                updateAnimationWalking();
                handleCollectible(x, y);
            } else {
                updateAnimationBlocked();
            }
        }
        updateOnscreenPosition();
    }

    public void moveRight() {
        movementTimer--;
        if (movementTimer <= 0) {
            movementTimer = speed;
            updateSpeed();
            if (!isBlocked(x + 1, y)) {
                x++;
                updateAnimationWalking();
                handleCollectible(x, y);
            } else {
                updateAnimationBlocked();
            }
        }
        updateOnscreenPosition();
    }

    public void updateSpeed() {
        if (!movedAlready) {
            speed *= ACCELERATION;
            if (speed < MAX_SPEED) speed = MAX_SPEED;
        }
        movedAlready = true;
    }

    public void resetSpeed() {
        movementTimer = 0;
        speed = BASE_SPEED;
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
        return cell == CellType.BUSH
            || cell == CellType.TREE
            || cell == CellType.ROCK;
            //cell == CellType.WALL|| 
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

    private void updateAnimationWalking() {
        if (animationState == 1) {
            animationState = 2;
        } else {
            animationState = 1;
        }
        updateAnimationFrame();
    }

    private void updateAnimationBlocked() {
        animationState = 0;
        resetSpeed();
        updateAnimationFrame();
    }

    private void updateAnimationFrame() {
        if (animationState == 0) {
            iconImage.setImagePath("guy.png");
        } else if (animationState == 1) {
            iconImage.setImagePath("guyL.png");
        } else if (animationState == 2) {
            iconImage.setImagePath("guyR.png");
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