package FlowerPower.model;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Scoreboard extends GraphicsGroup{

    //private GraphicsGroup group;
    private Rectangle window;
    private CanvasWindow canvas;
    private Gameboard board;

    private int mushNum;
    private int flowerNum;
    private int gemNum;
    private int score;

    public GraphicsGroup mushScore;
    public GraphicsGroup flowerScore;
    public GraphicsGroup gemScore;
    public GraphicsGroup totalScore;
    public GraphicsGroup coords;

    public Image mushButton;
    public Image flowerButton;
    public Image gemButton;
    public Image astarButton;
    public Image djButton;
    public Image toggleButton;

    private PathMode mode;
    private CellType pathTo;

    public Scoreboard(CanvasWindow canvas, Gameboard board){
        this.canvas = canvas;
        this.board = board;

        mode = PathMode.ASTAR;
        pathTo = CellType.EMPTY;

        setup();
        clickEvents();
    }

    private void setup(){
        window = new Rectangle(0, 0, 215, 300);
        window.setFillColor(new Color(175, 175, 175));
        this.add(window);

        // title
        Image title = new Image("gameName.png");
        title.setPosition(10, 10);
        title.setMaxWidth(180);
        this.add(title);

        //======================
        // coordinates
        Image coordsLabel = new Image("coordsLabel.png");
        coordsLabel.setMaxHeight(10);
        coordsLabel.setPosition(10, 45);
        coords = new GraphicsGroup();
        coords = coordBuilder("0", "0");
        coords.setPosition(130, 15+ 30);
        this.add(coords);
        this.add(coordsLabel);

        Image line1 = new Image("line.png");
        line1.setPosition(15, 67);
        line1.setMaxWidth(180);
        this.add(line1);

        // flower score
        flowerNum = 0;
        Image flowerLabel = new Image("flowerLabel.png");
        flowerLabel.setMaxHeight(10);
        flowerLabel.setPosition(10, 80);
        flowerScore = new GraphicsGroup();
        flowerScore = setNumber("0");
        flowerScore.setPosition(180, 80);
        this.add(flowerScore);
        this.add(flowerLabel);

        // mushroom score
        mushNum = 0;
        Image mushLabel = new Image("mushLabel.png");
        mushLabel.setMaxHeight(10);
        mushLabel.setPosition(10,100);
        mushScore = new GraphicsGroup();
        mushScore = setNumber("0");
        mushScore.setPosition(180, 70+ 30);
        this.add(mushScore);
        this.add(mushLabel);

        // gem score
        gemNum = 0;
        Image gemLabel = new Image("gemLabel.png");
        gemLabel.setMaxHeight(10);
        gemLabel.setPosition(10, 120);
        gemScore = new GraphicsGroup();
        gemScore = setNumber("0");
        gemScore.setPosition(180, 120);
        this.add(gemScore);
        this.add(gemLabel);
        
        // total score
        score = 0;
        Image totalLabel = new Image("totalScore.png");
        totalLabel.setMaxHeight(10);
        totalLabel.setPosition(10, 140);
        totalScore = new GraphicsGroup();
        totalScore = setNumber("0");
        totalScore.setPosition(150, 140);
        this.add(totalScore);
        this.add(totalLabel);

        //======================

        Image line2 = new Image("line.png");
        line2.setPosition(15, 160);
        line2.setMaxWidth(180);
        this.add(line2);

        Image buttonLabel = new Image("mapTo.png");
        buttonLabel.setPosition(10, 170);
        buttonLabel.setMaxHeight(10);
        this.add(buttonLabel);

        // flower button and label
        flowerButton = new Image("flower.png");
        flowerButton.setPosition(20, 190);
        this.add(flowerButton);
        Image flowerButtonLabel = new Image("flowerName.png");
        flowerButtonLabel.setPosition(20-5, 230);
        flowerButtonLabel.setMaxHeight(10);
        this.add(flowerButtonLabel);
        
        // mushroom button and label
        mushButton = new Image( "mushroom1.png");
        mushButton.setPosition(90, 160+ 30);
        this.add(mushButton);
        Image mushButtonLabel = new Image("mushName.png");
        mushButtonLabel.setPosition(90-16, 230);
        mushButtonLabel.setMaxHeight(10);
        this.add(mushButtonLabel);

        // gem button and label
        gemButton = new Image("gem-export.png");
        gemButton.setPosition(160, 160+ 30);
        this.add(gemButton);
        Image gemButtonLabel = new Image("gemName.png");
        gemButtonLabel.setPosition(164, 232);
        gemButtonLabel.setMaxHeight(7.5);
        this.add(gemButtonLabel);

        //======================

        Image line3 = new Image("line.png");
        line3.setPosition(15, 250);
        line3.setMaxWidth(180);
        this.add(line3);

        // dijkstra button
        djButton = new Image("Dijkstra.png");
        djButton.setMaxHeight(75);
        djButton.setPosition(djButton.getWidth(), 258);
        this.add(djButton);


        // toggle button
        toggleButton = new Image("toggle.png");
        toggleButton.setMaxWidth(50);
        double tXPos = 107.5 - 25;
        toggleButton.setPosition(tXPos, 260);
        this.add(toggleButton);

        // A* button
        astarButton = new Image("aStar.png");
        djButton.setMaxHeight(75);
        astarButton.setPosition(215 - astarButton.getWidth()*2, 258);
        this.add(astarButton);

        //======================
        
        updateBoard();
    }

    public void clickEvents(){
        /**
         *  Click events
         */
        canvas.onClick(event -> {
            double x = event.getPosition().getX();
            double y = event.getPosition().getY();

            // Sets path to respected collectible
            if (this.getElementAt(x, y) == flowerButton) {
                flowerPath();
            } else if (this.getElementAt(x, y) == mushButton) {
                mushroomPath();
            } else if (this.getElementAt(x, y) == gemButton) {
                gemPath();
            } else if (this.getElementAt(x, y) == toggleButton) {
                toggleButton.rotateBy(180);
                if (mode == PathMode.DJ){
                    mode = PathMode.ASTAR;
                } else {
                    mode = PathMode.DJ;
                }

                if (pathTo == CellType.FLOWER){
                    flowerPath();
                } else if (pathTo == CellType.MUSHROOM){
                    mushroomPath();
                } else if (pathTo == CellType.GEM){
                    gemPath();
                } else {
                    board.showPath(Explorer.colPos, Explorer.rowPos, 0, 0, PathMode.NONE);
                }
            }
        });


        /**
         * Hover effects
         */
        canvas.onMouseMove(event -> {
            double x = event.getPosition().getX();
            double y = event.getPosition().getY();

            if (this.getElementAt(x, y) == flowerButton) {
                flowerButton.setScale(1.25);
            } else {
                flowerButton.setScale(1);
            }

            if (this.getElementAt(x, y) == mushButton) {
                mushButton.setScale(1.25);
            } else {
                mushButton.setScale(1);
            }

            if (this.getElementAt(x, y) == gemButton) {
                gemButton.setScale(1.25);
            } else {
                gemButton.setScale(1);
            }

            if (this.getElementAt(x, y) == toggleButton) {
                toggleButton.setScale(1.05);
            } else {
                toggleButton.setScale(1);
            }
        });
    }
    
    /**
     * Click helpers
     */
    private void flowerPath(){
        pathTo = CellType.FLOWER;
        int[] closest = board.findClosest(pathTo, Explorer.colPos, Explorer.rowPos);
        board.showPath(Explorer.colPos, Explorer.rowPos, closest[0], closest[1], mode);
    }
    private void mushroomPath(){
        pathTo = CellType.MUSHROOM;
        int[] closest = board.findClosest(pathTo, Explorer.colPos, Explorer.rowPos);
        board.showPath(Explorer.colPos, Explorer.rowPos, closest[0], closest[1], mode);
    }
    private void gemPath(){
        pathTo = CellType.GEM;
        int[] closest = board.findClosest(pathTo, Explorer.colPos, Explorer.rowPos);
        board.showPath(Explorer.colPos, Explorer.rowPos, closest[0], closest[1], mode);
    }

    // updates visual text displayed on scoreboard
    public void updateBoard(){
        if (mushNum != Explorer.mushroomsCollected){
            this.remove(mushScore);
            mushScore = setNumber(String.valueOf(Explorer.mushroomsCollected));
            mushScore.setPosition(180, 100);
            mushNum = Explorer.mushroomsCollected;
            this.add(mushScore);
        }

        if (flowerNum != Explorer.flowersCollected){
            this.remove(flowerScore);
            flowerScore = setNumber(String.valueOf(Explorer.flowersCollected));
            flowerScore.setPosition(180,80);
            flowerNum = Explorer.flowersCollected;
            this.add(flowerScore);
        }

        if (gemNum != Explorer.gemsCollected){
            this.remove(gemScore);
            gemScore = setNumber(String.valueOf(Explorer.gemsCollected));
            gemScore.setPosition(180,120);
            gemNum = Explorer.gemsCollected;
            this.add(gemScore);
        }

        if (score != Explorer.totalScore){
            this.remove(totalScore);
            totalScore = setNumber(String.valueOf(Explorer.totalScore));
            totalScore.setPosition(160,140);
            score = Explorer.totalScore;
            this.add(totalScore);
        }

        this.remove(coords);
        coords = coordBuilder(String.valueOf(Explorer.colPos), String.valueOf(Explorer.rowPos));
        coords.setPosition(130, 45);
        this.add(coords);
    }

    private GraphicsGroup coordBuilder(String x, String y){
        GraphicsGroup newLabel = new GraphicsGroup();

        GraphicsGroup xCoord = setNumber(x);
        GraphicsGroup yCoord = setNumber(y);
        Image open = new Image("(.png");
        Image comma = new Image(",.png");
        Image close = new Image(").png");

        open.setMaxHeight(10);
        comma.setMaxHeight(4);
        close.setMaxHeight(10);

        int xPos = 0;
        open.setPosition(xPos, 0);
        xPos += open.getWidth();
        xPos += 5;
        xCoord.setPosition(xPos, 0);
        xPos += xCoord.getWidth();
        xPos += 5;
        comma.setPosition(xPos, 8);
        xPos += comma.getWidth();
        xPos += 5;
        yCoord.setPosition(xPos, 0);
        xPos += yCoord.getWidth();
        xPos += 5;
        close.setPosition(xPos,0);

        newLabel.add(open);
        newLabel.add(xCoord);
        newLabel.add(comma);
        newLabel.add(yCoord);
        newLabel.add(close);

        return newLabel;
    }

    private GraphicsGroup setNumber(String num){
        String[] numbers = num.split("");
        GraphicsGroup newLabel = new GraphicsGroup();
        int curX = 0;

        for (String c : numbers){
            String path = c + ".png";
            Image i = new Image(path);
            i.setMaxHeight(10);
            i.setPosition(curX, 0);
            curX += 8;
            newLabel.add(i);
        }

        curX = 0;

        return newLabel;
    }
}
