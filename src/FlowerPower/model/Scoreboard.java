package FlowerPower.model;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

public class Scoreboard extends GraphicsGroup{

    private final int SIZE = 100;

    //private GraphicsGroup group;
    private Rectangle window;

    public GraphicsText mushScore;
    public GraphicsText flowerScore;
    public GraphicsText gemScore;

    public GraphicsText totalScore;

    public GraphicsText coords;

    public Scoreboard(){
        setup();
    }

    private void setup(){
        window = new Rectangle(0, 0, 200, 130);
        window.setFillColor(Color.LIGHT_GRAY);
        this.add(window);

        //======================
        // Coordinates
        GraphicsText coordsLabel = new GraphicsText("Coordinates: ");
        coordsLabel.setPosition(5, 15);
        coords = new GraphicsText("(0,0)");
        coords.setPosition(100, 15);
        this.add(coords);
        this.add(coordsLabel);

        // Flower score
        GraphicsText flowerLabel = new GraphicsText("Flower Score: ");
        flowerLabel.setPosition(5, 35);
        flowerScore = new GraphicsText("0");
        flowerScore.setPosition(150, 35);
        this.add(flowerScore);
        this.add(flowerLabel);

        // Mushroom score
        GraphicsText mushLabel = new GraphicsText("Mushroom Score: ");
        mushLabel.setPosition(5, 55);
        mushScore = new GraphicsText("0");
        mushScore.setPosition(150, 55);
        this.add(mushScore);
        this.add(mushLabel);

        // Gem score
        GraphicsText gemLabel = new GraphicsText("Gem Score: ");
        gemLabel.setPosition(5, 75);
        gemScore = new GraphicsText("0");
        gemScore.setPosition(150, 75);
        this.add(gemScore);
        this.add(gemLabel);
        
        // Total score
        GraphicsText totalLabel = new GraphicsText("Total Score: ");
        totalLabel.setPosition(5, 105);
        totalScore = new GraphicsText("0");
        totalScore.setPosition(150, 105);
        this.add(totalScore);
        this.add(totalLabel);
        //======================

        styleFonts();
        updateBoard();
    }

    // updates visual text displayed on scoreboard
    public void updateBoard(){
        String mushNum = String.valueOf(Explorer.mushroomsCollected);
        if (!mushScore.getText().equals(mushNum)){
            mushScore.setText(mushNum);
        }

        String flowerNum = String.valueOf(Explorer.flowersCollected);
        if (!flowerScore.getText().equals(flowerNum)){
            flowerScore.setText(flowerNum);
        }

        String gemNum = String.valueOf(Explorer.gemsCollected);
        if (!gemScore.getText().equals(gemNum)){
            gemScore.setText(gemNum);
        }

        String totalNum = String.valueOf(Explorer.gemsCollected + Explorer.flowersCollected + Explorer.mushroomsCollected);
        totalScore.setText(totalNum);

        String coordString = coordBuilder(Explorer.colPos, Explorer.rowPos);
        coords.setText(coordString);
    }

    private void styleFonts(){
        // FontStyle style = new FontStyle
        // int size = 25;
       //GRAPHICSTEXT.setFont(style, size)
    }

    private String coordBuilder(int x, int y){
        return "(" + x + "," + y + ")";
    }

}
