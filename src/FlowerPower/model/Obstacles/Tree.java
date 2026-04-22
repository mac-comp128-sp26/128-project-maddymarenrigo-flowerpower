package FlowerPower.model.Obstacles;

import FlowerPower.model.Obstacle;

public class Tree implements Obstacle{

    private Double posX;
    private Double posY;
    private String type;

    public Tree(String type){
        posX = 0.0;
        posY = 0.0;
        type = this.type;
    }

    /**
     * Returns the x position
     *
     * @return the x position
     */
    public Double getX(){
        return posX;
    }

    /**
     * Returns the y position
     *
     * @return the y position
     */
    public Double getY(){
        return posY;
    }

    /**
     * Returns the type
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

}
