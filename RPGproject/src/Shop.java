//A front-end class that gives powerups, by Kai C

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Shop extends RPGPanel implements ActionListener {
    private JLabel currencyLabel;
    private JLabel[] powerLabels;
    private JButton[] powers;
    private JButton exitBtn;

    Shop(int id, RPG game, double xScale, double yScale) {
        super(id, game, xScale, yScale);

        setLayout(null);

        currencyLabel = new JLabel("Your wallet: "+game.getCurrency() + " coins");
        currencyLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*20)));
        currencyLabel.setForeground(Color.BLUE);
        currencyLabel.setBounds((int)(xScale*50),(int)(yScale*20),(int)(xScale*300),(int)(yScale*30));

        powerLabels = new JLabel[6];
        powers = new JButton[6];
        for (int count=0;count<6;count++) {
            powerLabels[count] = new JLabel();
            powerLabels[count].setHorizontalAlignment(JLabel.CENTER);
            powers[count] = new JButton();
            powers[count].setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
            powers[count].setOpaque(true);
            powers[count].setContentAreaFilled(false);
            powers[count].setBorderPainted(false);
            powers[count].setEnabled(true);
            powers[count].setActionCommand("power" + count);
            powers[count].addActionListener(this);

            if(count<=1) {
                powerLabels[count].setBounds((int)(xScale*300+(count*500*xScale)),(int)(yScale*300),
                        (int)(xScale*300),(int)(yScale*40));
                powers[count].setBounds((int)(xScale*400+(count*500*xScale)),(int)(yScale*350),
                        (int)(xScale*100),(int)(yScale*100));
            }
            else if(count==2 || count==3) {
                powerLabels[count].setBounds((int)(xScale*1300),(int)(yScale*300),
                        (int)(xScale*300),(int)(yScale*40));
                powers[count].setBounds((int)(xScale*1400),(int)(yScale*350),
                        (int)(xScale*100),(int)(yScale*100));
            }
            else {
                powerLabels[count].setBounds((int)(xScale*550+((count-4)*500*xScale)),(int)(yScale*600),
                        (int)(xScale*300),(int)(yScale*40));
                powers[count].setBounds((int)(xScale*650+((count-4)*500*xScale)),(int)(yScale*650),
                        (int)(xScale*100),(int)(yScale*100));
            }
            if((count==0 && game.getPower(0)>=20) ||
                    (count==1 && game.getPower(1)>=15) ||
                    (count==4 && game.getPower(4)<=200) ||
                    (count==5 && game.getPower(5)==1))
                powers[count].setEnabled(false);
            add(powerLabels[count]);
            add(powers[count]);
        }
        powerLabels[0].setText("Ship's HP inc");
        powers[0].setText("20");
        powerLabels[1].setText("Attack Speed inc");
        powers[1].setText("50");
        powerLabels[2].setText("Double Shooters");
        powers[2].setText("100");
        powerLabels[3].setText("Triple Shooters");
        powers[3].setText("150");
        powerLabels[4].setText("Boss HP dec");
        powers[4].setText("60");
        powerLabels[5].setText("Pause");
        powers[5].setText("200");

        if(game.getPower(2)==0 && game.getPower(3)==0) {
            powers[2].setVisible(true);
            powers[2].setEnabled(true);
            powerLabels[3].setVisible(false);
            powers[3].setVisible(false);
            powers[3].setEnabled(false);
        }
        else if(game.getPower(2)==1 && game.getPower(3)==0) {
            powerLabels[2].setVisible(false);
            powers[2].setVisible(false);
            powers[2].setEnabled(false);
            powerLabels[3].setVisible(true);
            powers[3].setVisible(true);
            powers[3].setEnabled(true);
        }
        else if(game.getPower(3)==1) {
            powerLabels[2].setVisible(false);
            powers[2].setVisible(false);
            powers[2].setEnabled(false);
            powerLabels[3].setVisible(true);
            powers[3].setVisible(true);
            powers[3].setEnabled(false);
        }

        exitBtn = new JButton("Back");
        exitBtn.setBounds((int)(xScale*1800),(int)(yScale*100),(int)(xScale*100),(int)(yScale*30));
        exitBtn.setActionCommand("Exit");
        exitBtn.addActionListener(this);

        add(currencyLabel);
        add(exitBtn);
    }
    public int getScreenID() {
        return id;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();

        if(eventName.contains("power")) {
            int power = Integer.parseInt(eventName.substring(5));
            if ((power==0 && game.getCurrency()>=20) || (power==1 && game.getCurrency()>=50) ||
                    (power==2 && game.getCurrency()>=100 && game.getPower(3)==0) ||
                    (power==3 && game.getCurrency()>=150 && game.getPower(2)==1) ||
                    (power==4 && game.getCurrency()>=60) || (power==5 && game.getCurrency()>=200)) {
                game.setPower(power);
                game.loseCurrency(power);
                currencyLabel.setText("Your wallet: "+game.getCurrency() + " coins");
                if((power==0 && game.getPower(0)>=20) ||
                        (power==1 && game.getPower(1)>=15) ||
                        (power==4 && game.getPower(4)<=200) ||
                        (power==5 && game.getPower(5)==1))
                    powers[power].setEnabled(false);
                else if(power==2 && game.getPower(2)==0) {
                    powers[2].setVisible(true);
                    powers[2].setEnabled(true);
                    powerLabels[3].setVisible(false);
                    powers[3].setVisible(false);
                    powers[3].setEnabled(false);
                }
                else if(power==2 && game.getPower(2)==1 && game.getPower(3)==0) {
                    powerLabels[2].setVisible(false);
                    powers[2].setVisible(false);
                    powers[2].setEnabled(false);
                    powerLabels[3].setVisible(true);
                    powers[3].setVisible(true);
                    powers[3].setEnabled(true);
                }
                else if(power==3 && game.getPower(3)==1) {
                    powerLabels[2].setVisible(false);
                    powers[2].setVisible(false);
                    powers[2].setEnabled(false);
                    powerLabels[3].setVisible(true);
                    powers[3].setVisible(true);
                    powers[3].setEnabled(false);
                }
            }
        }
        else if(eventName.equals("Exit")) {
            try {
                game.changeScreen(0);
            } catch(IOException ignored) {}
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.CYAN);
        g.fillOval((int)(xScale*400),(int)(yScale*350),(int)(xScale*100),(int)(yScale*100));
        g.fillOval((int)(xScale*900),(int)(yScale*350),(int)(xScale*100),(int)(yScale*100));
        g.fillOval((int)(xScale*1400),(int)(yScale*350),(int)(xScale*100),(int)(yScale*100));
        g.fillOval((int)(xScale*650),(int)(yScale*650),(int)(xScale*100),(int)(yScale*100));
        g.fillOval((int)(xScale*1150),(int)(yScale*650),(int)(xScale*100),(int)(yScale*100));
    }
}
