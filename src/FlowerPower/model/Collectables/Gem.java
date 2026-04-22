package FlowerPower.model.Collectables;

import FlowerPower.model.Collectable;

public class Gem implements Collectable{

    private Double posX;
    private Double posY;
    private String type;

    public Gem(String type){
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
