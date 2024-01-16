//Minigame#6, Front-end class by Kai C.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class TicTacToeScreen extends RPGPanel implements ActionListener {
    private TicTacToe ticGame;
    private JPanel helpPanel;
    private JLabel currencyLabel;
    private JLabel statusLabel;
    private JButton[][] space;

    TicTacToeScreen(int id, RPG game, double xScale, double yScale) {
        super(id, game, xScale, yScale);

        ticGame = new TicTacToe();
        setLayout(null);

        currencyLabel = new JLabel("Your wallet: "+game.getCurrency() + " coins");
        currencyLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*20)));
        currencyLabel.setForeground(Color.BLUE);
        currencyLabel.setBounds((int)(xScale*50),(int)(yScale*20),(int)(xScale*300),(int)(yScale*30));

        JLabel titleLabel = new JLabel("TicTacToe");
        titleLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*80)));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds((int)(xScale*735),(int)(yScale*50),(int)(xScale*700),(int)(yScale*90));

        statusLabel = new JLabel("Make your move");
        statusLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*35)));
        statusLabel.setBounds((int)(xScale*735),(int)(yScale*150),(int)(xScale*450),(int)(yScale*40));

        space = new JButton[3][3];
        for(int row=0;row<space.length;row++)
            for(int col=0;col<space[0].length;col++) {
                space[row][col] = new JButton();
                space[row][col].setFont(new Font("Arial",Font.BOLD,(int)(yScale*45)));
                space[row][col].setBounds((int)(xScale*735+(xScale*150*col)),(int)(yScale*200+(yScale*150*row)),
                        (int)(xScale*150),(int)(yScale*150));
                space[row][col].setEnabled(true);
                space[row][col].setActionCommand("space"+row+col);
                space[row][col].addActionListener(this);
                add(space[row][col]);
            }

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

        ImageIcon help = new ImageIcon(new ImageIcon(fileLocation+"TicTacToeHelp.png").getImage()
                .getScaledInstance((int)(xScale*1280),(int)(yScale*720),Image.SCALE_SMOOTH));
        JLabel instructions = new JLabel();
        instructions.setIcon(help);
        instructions.setBounds(0,0,(int)(xScale*1280),(int)(yScale*720));
        helpPanel.add(instructions);

        add(currencyLabel);
        add(titleLabel);
        add(statusLabel);
        add(exitBtn);
        add(helpBtn);
        add(helpPanel);
    }
    public int getScreenID() {
        return id;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();

        if(eventName.contains("space")) {
            int row = Integer.parseInt(eventName.substring(5,6));
            int col = Integer.parseInt(eventName.substring(6));
            ticGame.playerMove(row,col);
            space[row][col].setEnabled(false);
            space[row][col].setText("X");

            if (!checkWin()) {
                if(checkTie()) {
                    statusLabel.setText("It's a tie!");
                    for (JButton[] jButtons : space)
                        for (int tCol = 0; tCol < space[0].length; tCol++)
                            jButtons[tCol].setEnabled(false);
                }
                else {
                    int[] compMove = ticGame.computerMove();
                    row = compMove[0];
                    col = compMove[1];
                    space[row][col].setEnabled(false);
                    space[row][col].setText("O");

                    if(checkLoss()) {
                        statusLabel.setText("Computer won!");
                        for (JButton[] jButtons : space)
                            for (int tCol = 0; tCol < space[0].length; tCol++)
                                jButtons[tCol].setEnabled(false);
                    }
                }
            }
            else {
                game.updateCurrency(20);
                currencyLabel.setText("Your wallet: "+game.getCurrency() + " coins");
                statusLabel.setText("Player won!");
                for (JButton[] jButtons : space)
                    for (int tCol = 0; tCol < space[0].length; tCol++)
                        jButtons[tCol].setEnabled(false);
            }
        }
        else if(eventName.equals("Exit")) {
            try {
                game.changeScreen(0);
            } catch (IOException ignored) {}
        }
        else if(eventName.equals("About")) {
            helpPanel.setVisible(true);
            statusLabel.setVisible(false);
            for(int row=0;row<space.length;row++)
                for(int col=0;col<space[0].length;col++)
                    space[row][col].setVisible(false);
        }
        else if(eventName.equals("AboutLeave")) {
            helpPanel.setVisible(false);
            statusLabel.setVisible(true);
            for(int row=0;row<space.length;row++)
                for(int col=0;col<space[0].length;col++)
                    space[row][col].setVisible(true);
        }
    }
    private boolean checkWin() {
        return ticGame.playerWin();
    }
    private boolean checkLoss() {
        return ticGame.computerWin();
    }
    private boolean checkTie() {
        return ticGame.gameTie();
    }
}
