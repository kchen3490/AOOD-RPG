//Game Menu Class by Kai C.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainScreen extends RPGPanel implements ActionListener {
    private JPanel aboutPanel;
    private JLabel shopLabel;
    private JButton[] games;

    MainScreen(int id, RPG game, double xScale, double yScale) {
        super(id, game, xScale, yScale);

        setLayout(null);

        JLabel currencyLabel = new JLabel("Your wallet: " + game.getCurrency() + " coins");
        currencyLabel.setFont(new Font("Arial",Font.BOLD,(int)(yScale*20)));
        currencyLabel.setForeground(Color.BLUE);
        currencyLabel.setBounds((int)(xScale*50),(int)(yScale*20),(int)(xScale*300),(int)(yScale*30));

        shopLabel = new JLabel("Shop");
        shopLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,(int)(yScale*20)));
        shopLabel.setHorizontalAlignment(JLabel.CENTER);
        shopLabel.setBounds((int)(xScale*50),(int)(yScale*155),(int)(xScale*100),(int)(yScale*25));

        JButton shopBtn = new JButton();
        ImageIcon shopIcon = new ImageIcon(fileLocation+"shopIcon.png");
        shopBtn.setIcon(new ImageIcon(shopIcon.getImage().getScaledInstance((int)(xScale*100),(int)(yScale*100),
                Image.SCALE_SMOOTH)));
        shopBtn.setOpaque(false);
        shopBtn.setContentAreaFilled(false);
        shopBtn.setBounds((int)(xScale*50),(int)(yScale*50),(int)(xScale*100),(int)(yScale*100));

        games = new JButton[7];
        for(int gameNum=0;gameNum<games.length;gameNum++) {
            games[gameNum] = new JButton("Quest "+ (gameNum+1));
            games[gameNum].setFont(new Font(Font.SANS_SERIF,Font.BOLD,(int)(yScale*8)));
            games[gameNum].setOpaque(false);
            games[gameNum].setContentAreaFilled(false);
            games[gameNum].setActionCommand("game"+gameNum);
            games[gameNum].addActionListener(this);
            add(games[gameNum]);
        }

        games[0].setBounds((int)(xScale*615),(int)(yScale*240),(int)(xScale*80),(int)(yScale*40));
        games[1].setBounds((int)(xScale*1160),(int)(yScale*330),(int)(xScale*80),(int)(yScale*40));
        games[2].setBounds((int)(xScale*750),(int)(yScale*510),(int)(xScale*80),(int)(yScale*40));
        games[3].setBounds((int)(xScale*365),(int)(yScale*760),(int)(xScale*80),(int)(yScale*40));
        games[4].setBounds((int)(xScale*592),(int)(yScale*990),(int)(xScale*80),(int)(yScale*40));
        games[5].setBounds((int)(xScale*977),(int)(yScale*780),(int)(xScale*80),(int)(yScale*40));
        games[6].setText("FINAL BOSS");
        games[6].setFont(new Font(Font.SANS_SERIF,Font.BOLD,(int)(yScale*15)));
        games[6].setBounds((int)(xScale*1300),(int)(yScale*600),(int)(xScale*180),(int)(yScale*40));

        shopBtn.setActionCommand("Shop");
        shopBtn.addActionListener(this);

        JButton aboutBtn = new JButton("About");
        aboutBtn.setBounds((int)(xScale*1800),(int)(yScale*50),(int)(xScale*100),(int)(yScale*30));
        aboutBtn.setActionCommand("About");
        aboutBtn.addActionListener(this);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds((int)(xScale*1800),(int)(yScale*100),(int)(xScale*100),(int)(yScale*30));
        exitBtn.setActionCommand("Exit");
        exitBtn.addActionListener(this);

        aboutPanel = new JPanel();
        aboutPanel.setLayout(null);
        aboutPanel.setBounds((int)(xScale*320),(int)(yScale*180),(int)(xScale*1280),(int)(yScale*720));
        aboutPanel.setVisible(false);
        JLabel[] aboutText = new JLabel[6];
        for(int count=0;count<aboutText.length;count++) {
            aboutText[count] = new JLabel();
            aboutText[count].setHorizontalAlignment(JLabel.CENTER);
            aboutText[count].setVerticalAlignment(JLabel.CENTER);
            aboutText[count].setFont(new Font(Font.SANS_SERIF,Font.BOLD,(int)(yScale*20)));
            aboutText[count].setBounds((int)(xScale*100),(int)(yScale*60*count),(int)(xScale*1080),(int)(yScale*400));
            aboutPanel.add(aboutText[count]);
        }
        aboutText[0].setText("About the Game");
        aboutText[1].setText("Created by Weijie Y and Kai");
        aboutText[2].setText("Inspired by previous games like Club Penguin and older projects");
        aboutText[3].setText("Quests by Kai C");
        aboutText[4].setText("Final boss by Weijie Y");
        aboutText[5].setText("Started project on 3/24/2022 and finished on 5/15/2022");
        JButton aboutLeave = new JButton("X");
        aboutLeave.setFont(new Font(Font.SANS_SERIF,Font.BOLD,(int)(yScale*8)));
        aboutLeave.setOpaque(false);
        aboutLeave.setBorderPainted(false);
        aboutLeave.setBounds((int)(xScale*1200),(int)(yScale*50),(int)(xScale*50),(int)(yScale*50));
        aboutLeave.setActionCommand("AboutLeave");
        aboutLeave.addActionListener(this);
        aboutPanel.add(aboutLeave);

        add(aboutPanel);
        add(currencyLabel);
        add(shopLabel);
        add(shopBtn);
        add(aboutBtn);
        add(exitBtn);
    }
    public int getScreenID() {
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();

        switch (eventName) {
            case "Shop":
                try {
                    game.changeScreen(8);
                } catch (IOException ignored) {
                }
                break;
            case "Exit":
                int result = JOptionPane.showConfirmDialog(this,
                        "Do you want to exit? (All progress will be lost!)",
                        "Leave game", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                    this.game.exitGame();
                break;
            case "About":
                aboutPanel.setVisible(true);
                for (int count = 0; count < games.length; count++) {
                    if (count != 4) {
                        games[count].setVisible(false);
                        games[count].setEnabled(false);
                    }
                }
                break;
            case "AboutLeave":
                aboutPanel.setVisible(false);
                for (JButton jButton : games) {
                    jButton.setVisible(true);
                    jButton.setEnabled(true);
                }
                break;
            default:
                int gameNum = Integer.parseInt(eventName.substring(4));
                switch (gameNum) {
                    case 0:
                        try {
                            game.changeScreen(1);
                        } catch (IOException ignored) {
                        }
                        break;
                    case 1:
                        try {
                            game.changeScreen(2);
                        } catch (IOException ignored) {
                        }
                        break;
                    case 2:
                        try {
                            game.changeScreen(3);
                        } catch (IOException ignored) {
                        }
                        break;
                    case 3:
                        try {
                            game.changeScreen(4);
                        } catch (IOException ignored) {
                        }
                        break;
                    case 4:
                        try {
                            game.changeScreen(5);
                        } catch (IOException ignored) {
                        }
                        break;
                    case 5:
                        try {
                            game.changeScreen(6);
                        } catch (IOException ignored) {
                        }
                        break;
                    case 6:
                        if (game.getPower(0) == 1 && game.getPower(1) == 5 &&
                                game.getPower(2) == 0 && game.getPower(3) == 0 &&
                                game.getPower(4) == 1000 && game.getPower(5) == 0) {
                            int choice = JOptionPane.showConfirmDialog(this,
                                    "Are you sure you want to fight?" +
                                            "\nIt's recommended to get upgrades from the shop first!",
                                    "WAIT!!!", JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                try {
                                    game.changeScreen(7);
                                } catch (IOException ignored) {
                                }
                            }
                        } else {
                            try {
                                game.changeScreen(7);
                            } catch (IOException ignored) {
                            }
                        }
                        break;
                }
                break;
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon map = new ImageIcon(new ImageIcon(fileLocation+"MainScreen.png").getImage().
                getScaledInstance((int)(xScale*1920),(int)(yScale*1080),Image.SCALE_SMOOTH));
        g.drawImage(map.getImage(),0,0,null);
    }
}
