//Minigame#5, Front-end class by Kai C.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ElementalScreen extends RPGPanel implements ActionListener {
    private ElementalGame elemGame;
    private int[][] scores;
    private JPanel helpPanel;
    private JLabel currencyLabel;
    private JLabel titleLabel;
    private JLabel statusLabel;
    private JButton[] playerCards;
    private JButton[] computerCards;
    private JButton playerCard;
    private JButton computerCard;

    ElementalScreen(int id, RPG game, double xScale, double yScale) {
        super(id, game, xScale, yScale);

        elemGame = new ElementalGame();
        scores = elemGame.getScores();
        setLayout(null);

        currencyLabel = new JLabel("Your wallet: "+game.getCurrency() + " coins");
        currencyLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*20)));
        currencyLabel.setForeground(Color.BLUE);
        currencyLabel.setBounds((int)(xScale*50),(int)(yScale*20),(int)(xScale*300),(int)(yScale*30));

        titleLabel = new JLabel("Elemental Game");
        titleLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*70)));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds((int)(xScale*735),(int)(yScale*50),(int)(xScale*700),(int)(yScale*70));

        statusLabel = new JLabel("Player: "+scores[0][0]+" "+scores[0][1]+" "+scores[0][2]+" Computer: "+
                scores[1][0]+" "+scores[1][1]+" "+scores[1][2]);
        statusLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*35)));
        statusLabel.setBounds((int)(xScale*735),(int)(yScale*130),(int)(xScale*600),(int)(yScale*50));

        playerCards = new JButton[5];
        computerCards = new JButton[5];
        for(int card=0;card< playerCards.length;card++) {
            playerCards[card] = new JButton();
            playerCards[card].setFont(new Font("Arial",Font.BOLD,(int)(yScale*160)));
            playerCards[card].setBounds((int)(xScale*460+(xScale*200*card)),(int)(yScale*700),
                    (int)(xScale*150),(int)(yScale*200));
            playerCards[card].setActionCommand("Play"+card);
            playerCards[card].addActionListener(this);

            computerCards[card] = new JButton();
            computerCards[card].setBackground(Color.DARK_GRAY);
            computerCards[card].setBounds((int)(xScale*810+(50*card)),(int)(yScale*200),
                    (int)(xScale*40),(int)(yScale*50));
            computerCards[card].setEnabled(false);

            add(playerCards[card]);
            add(computerCards[card]);
        }

        computerCard = new JButton();
        computerCard.setBackground(Color.BLACK);
        computerCard.setFont(new Font("Arial",Font.BOLD,(int)(yScale*80)));
        computerCard.setBounds((int)(xScale*760),(int)(yScale*300),(int)(xScale*100),(int)(yScale*125));
        computerCard.setEnabled(false);

        playerCard = new JButton();
        playerCard.setBackground(Color.BLACK);
        playerCard.setFont(new Font("Arial",Font.BOLD,(int)(yScale*80)));
        playerCard.setBounds((int)(xScale*1010),(int)(yScale*500),(int)(xScale*100),(int)(yScale*125));
        playerCard.setEnabled(false);

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

        ImageIcon help = new ImageIcon(new ImageIcon(fileLocation+"ElementalGameHelp.png").getImage()
                .getScaledInstance((int)(xScale*1280),(int)(yScale*720),Image.SCALE_SMOOTH));
        JLabel instructions = new JLabel();
        instructions.setIcon(help);
        instructions.setBounds(0,0,(int)(xScale*1280),(int)(yScale*720));
        helpPanel.add(instructions);

        add(currencyLabel);
        add(titleLabel);
        add(statusLabel);
        add(playerCard);
        add(computerCard);
        add(exitBtn);
        add(helpBtn);
        add(helpPanel);

        updateCards();
    }
    public int getScreenID() {
        return id;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();

        if(eventName.contains("Play")) {
            int card = Integer.parseInt(eventName.substring(4));
            int[] play = elemGame.battle(card);
            updateField(play[0],play[1]);
            elemGame.drawCard(play[0],play[1]);
            statusLabel.setText("Player: "+scores[0][0]+" "+scores[0][1]+" "+scores[0][2]+" Computer: "+
                    scores[1][0]+" "+scores[1][1]+" "+scores[1][2]);
            if(elemGame.playerWin()) {
                game.updateCurrency(50);
                currencyLabel.setText("Your wallet: "+game.getCurrency() + " coins");
                titleLabel.setText("You won!");
                for(int iterate=0;iterate<5;iterate++)
                    playerCards[iterate].setEnabled(false);
            }
            else if(elemGame.playerLoss()) {
                titleLabel.setText("You lost!");
                for(int iterate=0;iterate<5;iterate++)
                    playerCards[iterate].setEnabled(false);
            }
            updateCards();
        }
        else if(eventName.equals("Exit")) {
            try {
                game.changeScreen(0);
            } catch (IOException ignored) {}
        }
        else if(eventName.equals("About")) {
            helpPanel.setVisible(true);
            statusLabel.setVisible(false);
            for(int card=0;card< playerCards.length;card++) {
                playerCards[card].setVisible(false);
                computerCards[card].setVisible(false);
            }
            playerCard.setVisible(false);
            computerCard.setVisible(false);
        }
        else if(eventName.equals("AboutLeave")) {
            helpPanel.setVisible(false);
            statusLabel.setVisible(true);
            for(int card=0;card< playerCards.length;card++) {
                playerCards[card].setVisible(true);
                computerCards[card].setVisible(true);
            }
            playerCard.setVisible(true);
            computerCard.setVisible(true);
        }
    }
    private void updateCards() {
        for(int card=0;card<5;card++) {
            if(elemGame.getPlayer().get(card).getElement()==0)
                playerCards[card].setBackground(Color.RED);
            else if(elemGame.getPlayer().get(card).getElement()==1)
                playerCards[card].setBackground(Color.BLUE);
            else if(elemGame.getPlayer().get(card).getElement()==2)
                playerCards[card].setBackground(Color.CYAN);
            playerCards[card].setText(""+elemGame.getPlayer().get(card).getValue());
        }
    }
    private void updateField(int play,int cpuPlay) {
        if(elemGame.getPlayer().get(play).getElement()==0)
            playerCard.setBackground(Color.RED);
        else if(elemGame.getPlayer().get(play).getElement()==1)
            playerCard.setBackground(Color.BLUE);
        else if(elemGame.getPlayer().get(play).getElement()==2)
            playerCard.setBackground(Color.CYAN);
        playerCard.setText(""+elemGame.getPlayer().get(play).getValue());

        if(elemGame.getComputer().get(cpuPlay).getElement()==0)
            computerCard.setBackground(Color.RED);
        else if(elemGame.getComputer().get(cpuPlay).getElement()==1)
            computerCard.setBackground(Color.BLUE);
        else if(elemGame.getComputer().get(cpuPlay).getElement()==2)
            computerCard.setBackground(Color.CYAN);
        computerCard.setText(""+elemGame.getComputer().get(cpuPlay).getValue());
    }
}
