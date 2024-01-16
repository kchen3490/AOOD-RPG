//Grandpa back-end class that runs in the beginning
//Starts in MainScreen.java and helps to navigate in between different minigames and boss fight

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RPG {
    private MainScreen screen0;
    private DieRoll screen1;
    private RunecastScreen screen2;
    private CaptureThiefScreen screen3;
    private ReactionPop screen4;
    private ElementalScreen screen5;
    private TicTacToeScreen screen6;
    private ShootGame screen7;
    private Shop screen8;
    private JFrame frame;

    private double xScale;
    private double yScale;

    private int currency;
    private int power0;
    private int power1;
    private int power2;
    private int power3;
    private int power4;
    private int power5;
    public static int width;
    public static int height;

    RPG() {
        width = Toolkit.getDefaultToolkit().getScreenSize().width;
        height = Toolkit.getDefaultToolkit().getScreenSize().height;
        changeScale(width,height);

        currency = 0;
        power0 = 1;//player hp
        power1 = 5;//attack speed
        power2 = 0;//double
        power3 = 0;//triple
        power4 = 1000;//boss hp
        power5 = 0;//pause

        screen0 = new MainScreen(0, this, xScale, yScale);

        frame = new JFrame("RPG");
        frame.setSize(width,height);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(screen0);
        frame.setVisible(true);

    }
    private void changeScale(int width, int height) {
        xScale = (double)1/1920*width;
        yScale = (double)1/1080*height;
    }
    public void changeScreen(int screenID) throws IOException {
        switch(screenID) {
            case 0:
                screen0 = new MainScreen(0, this, xScale, yScale);
                frame.setContentPane(screen0);
                break;
            case 1:
                screen1 = new DieRoll(1, this, xScale, yScale);
                frame.setContentPane(screen1);
                break;
            case 2:
                screen2 = new RunecastScreen(2, this, xScale, yScale);
                frame.setContentPane(screen2);
                break;
            case 3:
                screen3 = new CaptureThiefScreen(3, this, xScale, yScale);
                frame.setContentPane(screen3);
                break;
            case 4:
                screen4 = new ReactionPop(4, this, xScale, yScale);
                frame.setContentPane(screen4);
                break;
            case 5:
                screen5 = new ElementalScreen(5, this, xScale, yScale);
                frame.setContentPane(screen5);
                break;
            case 6:
                screen6 = new TicTacToeScreen(6, this, xScale, yScale);
                frame.setContentPane(screen6);
                break;
            case 7:
                screen7 = new ShootGame(7, this, xScale, yScale);
                frame.setContentPane(screen7);
                break;
            case 8:
                screen8 = new Shop(8,this,xScale,yScale);
                frame.setContentPane(screen8);
                break;
        }
        JPanel pane = (JPanel) frame.getContentPane();
        pane.revalidate();
        pane.repaint();
        frame.repaint();
    }
    public void updateCurrency(int gains) {
        currency+=gains;
    }
    public void loseCurrency(int power) {
        switch(power) {
            case 0:
                currency-=20;
                break;
            case 1:
                currency-=50;
                break;
            case 2:
                currency-=100;
                break;
            case 3:
                currency-=150;
                break;
            case 4:
                currency-=60;
                break;
            case 5:
                currency-=200;
                break;
        }
    }
    public int getCurrency() {
        return currency;
    }
    public int getPower(int num){
        switch(num) {
            case 0:
                return power0;
            case 1:
                return power1;
            case 2:
                return power2;
            case 3:
                return power3;
            case 4:
                return power4;
            case 5:
                return power5;
        }
        return 0;
    }
    public void setPower(int num) {
        switch(num) {
            case 0:
                power0++;
                break;
            case 1:
                power1+=5;
                break;
            case 2:
                power2 = 1;
                break;
            case 3:
                power3 = 1;
                break;
            case 4:
                power4-=100;
                break;
            case 5:
                power5 = 1;
                break;
        }
    }
    public void exitGame() {
        frame.dispose();
        System.exit(0);
    }

    public static void main(String[] arg) {
        new RPG();
    }
}
