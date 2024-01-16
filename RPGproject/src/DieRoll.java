//Minigame#1 class by Kai C.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DieRoll extends RPGPanel implements ActionListener {
    private JPanel helpPanel;
    private JLabel currencyLabel;
    private JLabel dieLabel;
    private JLabel statusLabel;
    private JButton rollDie;
    private JTextField userGuess;

    DieRoll(int id, RPG game, double xScale, double yScale) throws IOException {
        super(id, game, xScale, yScale);

        setLayout(null);

        currencyLabel = new JLabel("Your wallet: "+game.getCurrency() + " coins");
        currencyLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*20)));
        currencyLabel.setForeground(Color.BLUE);
        currencyLabel.setBounds((int)(xScale*50),(int)(yScale*20),(int)(xScale*300),(int)(yScale*30));

        statusLabel = new JLabel("Roll the Dice");
        statusLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*35)));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setVerticalAlignment(JLabel.CENTER);
        statusLabel.setBounds((int)(xScale*610),(int)(yScale*250),(int)(xScale*700),(int)(yScale*50));

        dieLabel = new JLabel();
        dieLabel.setHorizontalAlignment(JLabel.CENTER);
        dieLabel.setVerticalAlignment(JLabel.CENTER);
        dieLabel.setBounds((int)(xScale*885),(int)(yScale*300),(int)(xScale*150),(int)(yScale*150));
        setDie();

        userGuess = new JTextField("");
        userGuess.setFont(new Font("Arial",Font.PLAIN,(int)(yScale*25)));
        userGuess.setHorizontalAlignment(JTextField.CENTER);
        userGuess.setBounds((int)(xScale*910),(int)(yScale*490),(int)(xScale*100),(int)(yScale*50));
        userGuess.setEnabled(true);

        rollDie = new JButton("Roll");
        rollDie.setHorizontalAlignment(JButton.CENTER);
        rollDie.setBounds((int)(xScale*910),(int)(yScale*550),(int)(xScale*100),(int)(yScale*50));
        rollDie.setEnabled(true);
        rollDie.setActionCommand("Roll Die");
        rollDie.addActionListener(this);

        JButton exitBtn = new JButton("Back");
        exitBtn.setBounds((int)(xScale*1800),(int)(yScale*100),(int)(xScale*100),(int)(yScale*30));
        exitBtn.setActionCommand("Exit");
        exitBtn.addActionListener(this);

        JButton helpBtn = new JButton("HELP");
        helpBtn.setBounds((int)(xScale*1800),(int)(yScale*50),(int)(xScale*100),(int)(yScale*30));
        helpBtn.setActionCommand("About");
        helpBtn.addActionListener(this);

        helpPanel = new JPanel();
        helpPanel.setLayout(null);
        helpPanel.setBounds((int)(xScale*320),(int)(yScale*180),(int)(xScale*1280),(int)(yScale*720));
        helpPanel.setVisible(false);

        JButton helpLeave = new JButton("X");
        helpLeave.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,7));
        helpLeave.setOpaque(false);
        helpLeave.setBorderPainted(false);
        helpLeave.setBounds((int)(xScale*1200),(int)(yScale*50),(int)(xScale*50),(int)(yScale*50));
        helpLeave.setActionCommand("AboutLeave");
        helpLeave.addActionListener(this);
        helpPanel.add(helpLeave);

        ImageIcon help = new ImageIcon(new ImageIcon(fileLocation+"DieRollHelp.png").getImage()
                .getScaledInstance((int)(xScale*1280),(int)(yScale*720),Image.SCALE_SMOOTH));
        JLabel instructions = new JLabel();
        instructions.setIcon(help);
        instructions.setBounds(0,0,(int)(xScale*1280),(int)(yScale*720));
        helpPanel.add(instructions);

        add(currencyLabel);
        add(statusLabel);
        add(dieLabel);
        add(userGuess);
        add(rollDie);
        add(exitBtn);
        add(helpBtn);
        add(helpPanel);
        revalidate();
        repaint();
    }
    public int getScreenID() {
        return id;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();

        if(eventName.equals("Roll Die")) {
            int randRoll = (int)(Math.random()*6)+1;
            try {
                if (!userGuess.getText().equals("") &&
                        (Integer.parseInt(userGuess.getText())>0 &&
                                Integer.parseInt(userGuess.getText())<=6)) {
                    try {
                        setDie(randRoll);
                    } catch (IOException ignored) {}
                    if (Integer.parseInt(userGuess.getText()) == randRoll) {
                        game.updateCurrency(10);
                        currencyLabel.setText("Your wallet: "+game.getCurrency() + " coins");
                        statusLabel.setText("You won!");
                        rollDie.setEnabled(false);
                        userGuess.setEnabled(false);
                    }
                    else {
                        statusLabel.setText("No match! Reroll!");
                    }
                }
            } catch (NullPointerException | NumberFormatException ignored){}
        }
        else if(eventName.equals("Exit")) {
            try {
                game.changeScreen(0);
            } catch (IOException ignored) {}
        }
        else if(eventName.equals("About")) {
            helpPanel.setVisible(true);
            statusLabel.setVisible(false);
            dieLabel.setVisible(false);
            userGuess.setVisible(false);
            rollDie.setVisible(false);
        }
        else if(eventName.equals("AboutLeave")) {
            helpPanel.setVisible(false);
            statusLabel.setVisible(true);
            dieLabel.setVisible(true);
            userGuess.setVisible(true);
            rollDie.setVisible(true);
        }
    }
    private void setDie() throws IOException {
        int randRoll = (int)(Math.random()*6)+1;
        ImageIcon dieImage = new ImageIcon(fileLocation+"die1.gif");
        switch(randRoll) {
            case 1:
                dieImage = new ImageIcon(fileLocation+"die1.gif");
                break;
            case 2:
                dieImage = new ImageIcon(fileLocation+"die2.gif");
                break;
            case 3:
                dieImage = new ImageIcon(fileLocation+"die3.gif");
                break;
            case 4:
                dieImage = new ImageIcon(fileLocation+"die4.gif");
                break;
            case 5:
                dieImage = new ImageIcon(fileLocation+"die5.gif");
                break;
            case 6:
                dieImage = new ImageIcon(fileLocation+"die6.gif");
                break;
        }
        dieLabel.setIcon(new ImageIcon(dieImage.getImage().getScaledInstance((int)(xScale*150),(int)(yScale*150),
                Image.SCALE_SMOOTH)));
        revalidate();
        repaint();
    }
    private void setDie(int randNum) throws IOException {
        ImageIcon dieImage = new ImageIcon(fileLocation+"die1.gif");
        switch(randNum) {
            case 1:
                dieImage = new ImageIcon(fileLocation+"die1.gif");
                break;
            case 2:
                dieImage = new ImageIcon(fileLocation+"die2.gif");
                break;
            case 3:
                dieImage = new ImageIcon(fileLocation+"die3.gif");
                break;
            case 4:
                dieImage = new ImageIcon(fileLocation+"die4.gif");
                break;
            case 5:
                dieImage = new ImageIcon(fileLocation+"die5.gif");
                break;
            case 6:
                dieImage = new ImageIcon(fileLocation+"die6.gif");
                break;
        }
        dieLabel.setIcon(new ImageIcon(dieImage.getImage().getScaledInstance((int)(xScale*150),(int)(yScale*150),
                Image.SCALE_SMOOTH)));
        revalidate();
        repaint();
    }
}
