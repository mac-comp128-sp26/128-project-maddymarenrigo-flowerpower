package FlowerPower.model;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Scoreboard extends GraphicsGroup{

    private final int SIZE = 100;

    //private GraphicsGroup group;
    private Rectangle window;

    private int mushNum;
    private int flowerNum;
    private int gemNum;
    private int score;

    // public GraphicsText mushScore;
    // public GraphicsText flowerScore;
    // public GraphicsText gemScore;
    public GraphicsGroup mushScore;
    public GraphicsGroup flowerScore;
    public GraphicsGroup gemScore;

    public GraphicsGroup totalScore;

    public GraphicsGroup coords;

    public Scoreboard(){
        setup();
    }

    private void setup(){
        window = new Rectangle(0, 0, 215, 130);
        window.setFillColor(Color.LIGHT_GRAY);
        this.add(window);

        //======================
        // Coordinates
        Image coordsLabel = new Image("coordsLabel.png");
        coordsLabel.setMaxHeight(10);
        coordsLabel.setPosition(10, 15);
        coords = new GraphicsGroup();
        coords = coordBuilder("0", "0");
        coords.setPosition(130, 15);
        this.add(coords);
        this.add(coordsLabel);

        // Flower score
        flowerNum = 0;
        Image flowerLabel = new Image("flowerLabel.png");
        flowerLabel.setMaxHeight(10);
        flowerLabel.setPosition(10, 35);
        flowerScore = new GraphicsGroup();
        flowerScore = setNumber("0");
        flowerScore.setPosition(180, 35);
        this.add(flowerScore);
        this.add(flowerLabel);

        // Mushroom score
        mushNum = 0;
        Image mushLabel = new Image("mushLabel.png");
        mushLabel.setMaxHeight(10);
        mushLabel.setPosition(10, 55);
        mushScore = new GraphicsGroup();
        mushScore = setNumber("0");
        mushScore.setPosition(180, 55);
        this.add(mushScore);
        this.add(mushLabel);

        // Gem score
        gemNum = 0;
        Image gemLabel = new Image("gemLabel.png");
        gemLabel.setMaxHeight(10);
        gemLabel.setPosition(10, 75);
        gemScore = new GraphicsGroup();
        gemScore = setNumber("0");
        gemScore.setPosition(180, 75);
        this.add(gemScore);
        this.add(gemLabel);
        
        // Total score
        score = 0;
        Image totalLabel = new Image("totalScore.png");
        totalLabel.setMaxHeight(10);
        totalLabel.setPosition(10, 105);
        totalScore = new GraphicsGroup();
        totalScore = setNumber("0");
        totalScore.setPosition(150, 105);
        this.add(totalScore);
        this.add(totalLabel);
        //======================
        
        updateBoard();
    }

    // updates visual text displayed on scoreboard
    public void updateBoard(){
        if (mushNum != Explorer.mushroomsCollected){
            this.remove(mushScore);
            mushScore = setNumber(String.valueOf(Explorer.mushroomsCollected));
            mushScore.setPosition(180,55);
            this.add(mushScore);
        }

        if (flowerNum != Explorer.flowersCollected){
            this.remove(flowerScore);
            flowerScore = setNumber(String.valueOf(Explorer.flowersCollected));
            flowerScore.setPosition(180,35);
            this.add(flowerScore);
        }

        if (gemNum != Explorer.gemsCollected){
            this.remove(gemScore);
            gemScore = setNumber(String.valueOf(Explorer.gemsCollected));
            gemScore.setPosition(180,75);
            this.add(gemScore);
        }

        if (score != Explorer.totalScore){
            this.remove(totalScore);
            totalScore = setNumber(String.valueOf(Explorer.totalScore));
            totalScore.setPosition(160,105);
            this.add(totalScore);
        }

        this.remove(coords);
        coords = coordBuilder(String.valueOf(Explorer.colPos), String.valueOf(Explorer.rowPos));
        coords.setPosition(130, 15);
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
