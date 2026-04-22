package FlowerPower.model;

import edu.macalester.graphics.GraphicsGroup;

public class Explorer {

    private GraphicsGroup icon;
    private int score;

    public Explorer(GraphicsGroup icon){
        icon = this.icon;
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
