package FlowerPower.model.Collectibles;

import java.awt.Color;

import edu.macalester.graphics.Ellipse;
import FlowerPower.model.Collectible;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;

public class Gem implements Collectible{

    public static final double TILE_SIZE = 32;

    private Double posX;
    private Double posY;
    private String type;
    public GraphicsGroup icon;

    public Gem(String type, GraphicsGroup icon){
        this.icon = icon;
        posX = 0.0;
        posY = 0.0;
        this.type = type;

        Ellipse rect = new Ellipse(0, 0, (int) TILE_SIZE, (int) TILE_SIZE);
        rect.setFillColor(Color.ORANGE);
        icon.add(rect); 
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
