//Minigame#5, Back-end class by Kai C.

import javax.swing.text.Element;
import java.util.ArrayList;

public class ElementalGame {
    private ArrayList<ElementalCard> player;
    private ArrayList<ElementalCard> computer;
    private int[] playerScore;
    private int[] computerScore;

    ElementalGame() {
        player = new ArrayList<ElementalCard>(5);
        computer = new ArrayList<ElementalCard>(5);
        for(int iterate=0;iterate<5;iterate++) {
            player.add(new ElementalCard());
            computer.add(new ElementalCard());
        }
        playerScore = new int[3];
        computerScore = new int[3];
        for(int count=0;count<playerScore.length;count++) {
            playerScore[count]=0;
            computerScore[count]=0;
        }
    }
    public int[] battle(int playerChoice) {
        int computerChoice = (int)(Math.random()*computer.size());
        ElementalCard playerCard = player.get(playerChoice);
        ElementalCard computerCard = computer.get(computerChoice);

        if(playerCard.getElement() == playerCard.fire) {
            if(computerCard.getElement() == computerCard.snow)
                playerScore[0]++;
            else if(computerCard.getElement() == computerCard.fire) {
                if(playerCard.getValue()>computerCard.getValue())
                    playerScore[0]++;
                else if(playerCard.getValue()<computerCard.getValue())
                    computerScore[0]++;
            }
            else
                computerScore[1]++;
        }
        else if (playerCard.getElement() == playerCard.water) {
            if(computerCard.getElement() == computerCard.fire)
                playerScore[1]++;
            else if(computerCard.getElement() == computerCard.water) {
                if(playerCard.getValue()>computerCard.getValue())
                    playerScore[1]++;
                else if(playerCard.getValue()<computerCard.getValue())
                    computerScore[1]++;
            }
            else
                computerScore[2]++;
        }
        else {
            if(computerCard.getElement() == computerCard.water)
                playerScore[2]++;
            else if(computerCard.getElement() == computerCard.snow) {
                if(playerCard.getValue()>computerCard.getValue())
                    playerScore[2]++;
                else if(playerCard.getValue()<computerCard.getValue())
                    computerScore[2]++;
            }
            else
                computerScore[0]++;
        }
        return new int[]{playerChoice,computerChoice};
    }
    public void drawCard(int playerChoice, int computerChoice) {
        player.remove(playerChoice);
        player.add(playerChoice,new ElementalCard());
        computer.remove(computerChoice);
        computer.add(computerChoice,new ElementalCard());
    }
    public boolean playerWin() {
        if(playerScore[0]==3)
            return true;
        else if(playerScore[1]==3)
            return true;
        else if(playerScore[2]==3)
            return true;
        else if(playerScore[0]>=1 && playerScore[1]>=1 && playerScore[2]>=1)
            return true;
        else
            return false;
    }
    public boolean playerLoss() {
        if(computerScore[0]==3)
            return true;
        else if(computerScore[1]==3)
            return true;
        else if(computerScore[2]==3)
            return true;
        else if(computerScore[0]>=1 && computerScore[1]>=1 && computerScore[2]>=1)
            return true;
        else
            return false;
    }
    public ArrayList<ElementalCard> getPlayer() {
        return player;
    }
    public ArrayList<ElementalCard> getComputer() {
        return computer;
    }
    public int[][] getScores() {
        return new int[][]{playerScore,computerScore};
    }
}
