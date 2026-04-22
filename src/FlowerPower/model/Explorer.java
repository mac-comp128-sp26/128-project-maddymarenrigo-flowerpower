package FlowerPower.model;

import edu.macalester.graphics.GraphicsGroup;

// This is our character!!!

public class Explorer {

    private GraphicsGroup icon;
    private int score;
    private Gameboard gameboard;

    private double speed;
    

    public Explorer(GraphicsGroup icon, Gameboard gameboard){
        icon = this.icon;
        gameboard = this.gameboard;

    }

    public double getX(){
        return 0.0; 
    }

    public double getY(){
        return 0.0; 
    }

    public int getScore(){
        return score;
    }

}
