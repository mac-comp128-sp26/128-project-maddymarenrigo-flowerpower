package FlowerPower.model;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Scoreboard extends GraphicsGroup{

    private final int SIZE = 100;

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

    public Scoreboard(CanvasWindow canvas, Gameboard board){
        this.canvas = canvas;
        this.board = board;
        setup();
        clickEvents();
    }

    private void setup(){
        window = new Rectangle(0, 0, 215, 245);
        window.setFillColor(Color.LIGHT_GRAY);
        this.add(window);

        Image title = new Image("gameName.png");
        title.setPosition(10, 10);
        title.setMaxWidth(180);
        this.add(title);

        //======================
        Image line2 = new Image("line.png");
        line2.setPosition(15, 160);
        line2.setMaxWidth(180);
        this.add(line2);

        Image buttonLabel = new Image("mapTo.png");
        buttonLabel.setPosition(10, 170);
        buttonLabel.setMaxHeight(10);
        this.add(buttonLabel);

        flowerButton = new Image("flower.png");
        flowerButton.setPosition(20, 190);
        this.add(flowerButton);
        Image flowerButtonLabel = new Image("flowerName.png");
        flowerButtonLabel.setPosition(20-5, 230);
        flowerButtonLabel.setMaxHeight(10);
        this.add(flowerButtonLabel);
        
        mushButton = new Image( "mushroom1.png");
        mushButton.setPosition(90, 160+ 30);
        this.add(mushButton);
        Image mushButtonLabel = new Image("mushName.png");
        mushButtonLabel.setPosition(90-16, 230);
        mushButtonLabel.setMaxHeight(10);
        this.add(mushButtonLabel);

        gemButton = new Image("gem-export.png");
        gemButton.setPosition(160, 160+ 30);
        this.add(gemButton);
        Image gemButtonLabel = new Image("gemName.png");
        gemButtonLabel.setPosition(164, 232);
        gemButtonLabel.setMaxHeight(7.5);
        this.add(gemButtonLabel);

        //======================

        //======================
        // Coordinates
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

        // Flower score
        flowerNum = 0;
        Image flowerLabel = new Image("flowerLabel.png");
        flowerLabel.setMaxHeight(10);
        flowerLabel.setPosition(10, 80);
        flowerScore = new GraphicsGroup();
        flowerScore = setNumber("0");
        flowerScore.setPosition(180, 80);
        this.add(flowerScore);
        this.add(flowerLabel);

        // Mushroom score
        mushNum = 0;
        Image mushLabel = new Image("mushLabel.png");
        mushLabel.setMaxHeight(10);
        mushLabel.setPosition(10,100);
        mushScore = new GraphicsGroup();
        mushScore = setNumber("0");
        mushScore.setPosition(180, 70+ 30);
        this.add(mushScore);
        this.add(mushLabel);

        // Gem score
        gemNum = 0;
        Image gemLabel = new Image("gemLabel.png");
        gemLabel.setMaxHeight(10);
        gemLabel.setPosition(10, 120);
        gemScore = new GraphicsGroup();
        gemScore = setNumber("0");
        gemScore.setPosition(180, 120);
        this.add(gemScore);
        this.add(gemLabel);
        
        // Total score
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
        
        updateBoard();
    }

    public void clickEvents(){
        /**
         *  Click events
         */
        canvas.onClick(event -> {
            double x = event.getPosition().getX();
            double y = event.getPosition().getY();

            if (this.getElementAt(x, y) == flowerButton) {
                int[] closest = board.findClosest("flower", Explorer.colPos, Explorer.rowPos);
                
                board.showPath(Explorer.colPos, Explorer.rowPos, closest[0], closest[1]);
                System.out.println("flower path");
            }

            if (this.getElementAt(x, y) == mushButton) {
                int[] closest = board.findClosest("mushroom", Explorer.colPos, Explorer.rowPos);
                
                board.showPath(Explorer.colPos, Explorer.rowPos, closest[0], closest[1]);
                System.out.println("mush path");
            }

            if (this.getElementAt(x, y) == gemButton) {
                int[] closest = board.findClosest("gem", Explorer.colPos, Explorer.rowPos);
                
                board.showPath(Explorer.colPos, Explorer.rowPos, closest[0], closest[1]);
                System.out.println("gem path");
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

        });
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
