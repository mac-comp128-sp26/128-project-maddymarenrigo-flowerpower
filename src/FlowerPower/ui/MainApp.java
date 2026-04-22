package FlowerPower.ui;

import FlowerPower.model.Collectible;
import FlowerPower.model.Obstacle;
import FlowerPower.model.Explorer;
import FlowerPower.model.Gameboard;

//Maren is doing ui
/**
 * backround
 * shapes for player, collect and obstical
 */
public class MainApp {

    //variables
    Gameboard game;

    private MainApp(){
        //constructor
        // add obstacles, explorer, collectables OR have Gameboard.java implement those and then just add a new Gameboard object
        game = new Gameboard(16, 16); // 256 by 256 for full gameboard
    }

    public static void main(String[] args) {
        new MainApp().play();
    }

    private void play(){
        // idk if we need this but just setting things up
    }

}
