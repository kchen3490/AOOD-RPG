//Minigame#4, class by Kai C.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ReactionPop extends RPGPanel implements ActionListener {
    private final static int maxSeconds = 30;
    private int hit;
    private int seconds;
    private Timer timer;
    private TimerTask task;
    private JLabel currencyLabel;
    private JLabel statusLabel;
    private JLabel counterLabel;
    private JLabel timerLabel;
    private JButton hitBtn;

    ReactionPop(int id, RPG game, double xScale, double yScale) {
        super(id, game, xScale, yScale);

        hit = 0;
        seconds = 0;
        int randRow = (int)(Math.random()*(1650*xScale))+100;
        int randCol = (int)(Math.random()*(750*yScale))+100;
        setLayout(null);

        currencyLabel = new JLabel("Your wallet: "+game.getCurrency() + " coins");
        currencyLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*20)));
        currencyLabel.setForeground(Color.BLUE);
        currencyLabel.setBounds((int)(xScale*50),(int)(yScale*20),(int)(xScale*300),(int)(yScale*30));

        statusLabel = new JLabel("Click 10 coins");
        statusLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*50)));
        statusLabel.setForeground(Color.BLUE);
        statusLabel.setBounds((int)(xScale*735),(int)(yScale*50),(int)(xScale*800),(int)(yScale*60));

        counterLabel = new JLabel("You hit: " + hit + " times");
        counterLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*40)));
        counterLabel.setBounds((int)(xScale*735),(int)(yScale*120),(int)(xScale*700),(int)(yScale*50));

        timerLabel = new JLabel("You'll have "+ maxSeconds + " seconds!");
        timerLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*40)));
        timerLabel.setBounds((int)(xScale*735),(int)(yScale*180),(int)(xScale*700),(int)(yScale*50));

        ImageIcon coin = new ImageIcon(fileLocation+"coin.png");
        hitBtn = new JButton();
        hitBtn.setIcon(new ImageIcon(coin.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        hitBtn.setOpaque(false);
        hitBtn.setContentAreaFilled(false);
        hitBtn.setBorderPainted(false);
        hitBtn.setBounds(randRow,randCol,100,100);
        hitBtn.setActionCommand("hit");
        hitBtn.addActionListener(this);

        JButton exitBtn = new JButton("Back");
        exitBtn.setBounds((int)(xScale*1800),(int)(yScale*100),(int)(xScale*100),(int)(yScale*30));
        exitBtn.setActionCommand("Exit");
        exitBtn.addActionListener(this);

        add(currencyLabel);
        add(statusLabel);
        add(counterLabel);
        add(timerLabel);
        add(hitBtn);
        add(exitBtn);

        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                seconds++;
                timerLabel.setText((maxSeconds-seconds) + " seconds left");
                if(seconds>=maxSeconds) {
                    hitBtn.setEnabled(false);
                    hitBtn.setVisible(false);
                    statusLabel.setText("You lost!");
                    counterLabel.setText("You hit: " + hit + " times");
                    timer.cancel();
                }
            }
        };
    }
    public int getScreenID() {
        return id;
    }
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();
        if(eventName.equals("hit")) {
            if(hit==0)
                timer.scheduleAtFixedRate(task,1000,1000);
            hit++;
            if(hit>=10) {
                game.updateCurrency(10);
                hitBtn.setEnabled(false);
                hitBtn.setVisible(false);
                currencyLabel.setText("Your wallet: "+game.getCurrency() + " coins");
                statusLabel.setText("You won!");
                counterLabel.setText("You collected: " + hit + " coins");
                timer.cancel();
            }
            else {
                counterLabel.setText("You collected: " + hit + " coins");
                int randCol = (int)(Math.random()*(1720*xScale)+100);
                int randRow = (int)(Math.random()*(650*yScale)+230);
                hitBtn.setBounds(randCol,randRow,100,100);
            }
        }
        else if(eventName.equals("Exit")) {
            try {
                timer.cancel();
                game.changeScreen(0);
            } catch (IOException ignored) {}
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon background = new ImageIcon(new ImageIcon(fileLocation+"Forest.png")
                .getImage().getScaledInstance((int)(xScale*1920),(int)(yScale*1080),Image.SCALE_SMOOTH));
        g.drawImage(background.getImage(),0,0,null);
    }
}
