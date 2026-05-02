package FlowerPower.model;


import java.awt.Color;
import java.util.ArrayList;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Scoreboard {

    private final int SIZE = 100;

    private GraphicsGroup group;
    private Rectangle window;

    public GraphicsText mushScore;
    public GraphicsText flowerScore;
    public GraphicsText gemScore;

    public GraphicsText totalScore;

    public Scoreboard(){
        group = new GraphicsGroup();
        
        setup();
    }

    private void setup(){
        window = new Rectangle(0,0,SIZE,SIZE);
        window.setFillColor(Color.LIGHT_GRAY);
        group.add(window);

        mushScore = new GraphicsText("0");
        flowerScore = new GraphicsText("0");
        gemScore = new GraphicsText("0");
        group.add(mushScore);
        group.add(flowerScore);
        group.add(gemScore);

        totalScore = new GraphicsText("0");
        group.add(totalScore);

        styleFonts();

        updateScores();
    }

    public void updateScores(){
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
    }

    private void styleFonts(){

    }

}
