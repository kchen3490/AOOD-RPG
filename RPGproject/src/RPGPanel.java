//Grandpa front-end class that helps all JPanels for the game to run, by Kai C

import javax.swing.*;

public abstract class RPGPanel extends JPanel {
    protected String fileLocation;
    protected int id;
    protected RPG game;
    protected double xScale;
    protected double yScale;

    RPGPanel(int id, RPG game, double xScale, double yScale) {
        fileLocation = "Resources/";
        this.id = id;
        this.game = game;
        this.xScale = xScale;
        this.yScale = yScale;
    }
    public abstract int getScreenID();
}
