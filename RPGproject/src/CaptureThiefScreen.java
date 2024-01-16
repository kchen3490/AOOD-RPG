//Minigame#3, Front-end class by Kai C.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CaptureThiefScreen extends RPGPanel implements ActionListener {
    private CaptureThief capGame;
    private int[][] gameGrid;
    private JPanel helpPanel;
    private JLabel currencyLabel;
    private JLabel statusLabel;
    private JLabel moveLabel;
    private JButton[][] space;

    CaptureThiefScreen(int id, RPG game, double xScale, double yScale) {
        super(id, game, xScale, yScale);

        capGame = new CaptureThief();
        gameGrid = capGame.getGrid();
        setLayout(null);

        currencyLabel = new JLabel("Your wallet: "+game.getCurrency() + " coins");
        currencyLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*20)));
        currencyLabel.setForeground(Color.BLUE);
        currencyLabel.setBounds((int)(xScale*50),(int)(yScale*20),(int)(xScale*300),(int)(yScale*30));

        JLabel titleLabel = new JLabel("Capture Thief");
        titleLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*80)));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds((int)(xScale*735),(int)(yScale*50),(int)(xScale*700),(int)(yScale*70));

        statusLabel = new JLabel("Make your move");
        statusLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*35)));
        statusLabel.setBounds((int)(xScale*735),(int)(yScale*130),(int)(xScale*450),(int)(yScale*40));

        moveLabel = new JLabel(capGame.getMoves()[0]-capGame.getMoves()[1]+" moves left");
        moveLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*20)));
        moveLabel.setBounds((int)(xScale*735),(int)(yScale*170),(int)(xScale*450),(int)(yScale*30));

        space = new JButton[5][8];
        for(int row=0;row<space.length;row++)
            for(int col=0;col<space[0].length;col++) {
                space[row][col] = new JButton();
                space[row][col].setBounds((int)(xScale*360+(xScale*150*col)),(int)(yScale*200+(yScale*150*row)),
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

        ImageIcon help = new ImageIcon(new ImageIcon(fileLocation+"CapturethiefHelp.png").getImage()
                .getScaledInstance((int)(xScale*1280),(int)(yScale*720),Image.SCALE_SMOOTH));
        JLabel instructions = new JLabel();
        instructions.setIcon(help);
        instructions.setBounds(0,0,(int)(xScale*1280),(int)(yScale*720));
        helpPanel.add(instructions);

        add(currencyLabel);
        add(titleLabel);
        add(statusLabel);
        add(moveLabel);
        add(exitBtn);
        add(helpBtn);
        add(helpPanel);
        updateGrid();
    }
    public int getScreenID() {
        return id;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();

        if(eventName.contains("space")) {
            int row = Integer.parseInt(eventName.substring(5, 6));
            int col = Integer.parseInt(eventName.substring(6));
            if(gameGrid[row][col]==0) {
                boolean choosePhase = false;
                for (int[] ints : gameGrid)
                    for (int tempCol = 0; tempCol < gameGrid[0].length; tempCol++)
                        if (ints[tempCol] == 3 || ints[tempCol] == 4) {
                            choosePhase = true;
                            break;
                        }
                if(!choosePhase) {
                    capGame.playerChoose(row,col);
                    space[row][col].setActionCommand("move"+row+col);
                    for(int tRow=0;tRow<space.length;tRow++)
                        for(int tCol=0;tCol<space[0].length;tCol++)
                            if(gameGrid[tRow][tCol]==3 || gameGrid[tRow][tCol]==4)
                                space[tRow][tCol].setActionCommand("move"+tRow+tCol);
                }
                else
                    capGame.playerCancel();
                updateGrid();
            }
        }
        else if(eventName.contains("move")) {
            int row = Integer.parseInt(eventName.substring(4,5));
            int col = Integer.parseInt(eventName.substring(5));
            if(gameGrid[row][col]==3) {
                capGame.playerMove(row,col);
                capGame.computerMove();
                if(capGame.playerWin()) {
                    game.updateCurrency(80);
                    currencyLabel.setText("Your wallet: "+game.getCurrency() + " coins");
                    statusLabel.setText("You won!");
                    for (JButton[] jButtons : space)
                        for (int tCol = 0; tCol < space[0].length; tCol++)
                            jButtons[tCol].setEnabled(false);
                }
                else if(capGame.playerLost()) {
                    statusLabel.setText("You Lost!");
                    for (JButton[] jButtons : space)
                        for (int tCol = 0; tCol < space[0].length; tCol++)
                            jButtons[tCol].setEnabled(false);
                }
                moveLabel.setText(capGame.getMoves()[0]-capGame.getMoves()[1]+" moves left");
            }
            else
                capGame.playerCancel();
            for(int tRow=0;tRow<space.length;tRow++)
                for(int tCol=0;tCol<space[0].length;tCol++)
                    space[tRow][tCol].setActionCommand("space"+tRow+tCol);
            updateGrid();
        }
        else if(eventName.equals("Exit")) {
            try {
                game.changeScreen(0);
            } catch (IOException ignored) {}
        }
        else if(eventName.equals("About")) {
            helpPanel.setVisible(true);
            statusLabel.setVisible(false);
            moveLabel.setVisible(false);
            for(int row=0;row<space.length;row++)
                for(int col=0;col<space[0].length;col++)
                    space[row][col].setVisible(false);
        }
        else if(eventName.equals("AboutLeave")) {
            helpPanel.setVisible(false);
            statusLabel.setVisible(true);
            moveLabel.setVisible(true);
            for(int row=0;row<space.length;row++)
                for(int col=0;col<space[0].length;col++)
                    space[row][col].setVisible(true);
        }
    }
    private void updateGrid() {
        for(int row=0;row<space.length;row++)
            for(int col=0;col<space[0].length;col++) {
                if(gameGrid[row][col]==2)
                    space[row][col].setBackground(Color.WHITE);
                else if(gameGrid[row][col]==5) {
                    space[row][col].setBackground(Color.DARK_GRAY);
                    space[row][col].setEnabled(false);
                }
                else if(gameGrid[row][col]==4 || gameGrid[row][col]==0)
                    space[row][col].setBackground(Color.BLUE);
                else if(gameGrid[row][col]==3)
                    space[row][col].setBackground(Color.YELLOW);
                else if(gameGrid[row][col]==1)
                    space[row][col].setBackground(Color.RED);
            }
    }
}
