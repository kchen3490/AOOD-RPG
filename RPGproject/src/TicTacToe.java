//Minigame#6, Back-end class by Kai C.

public class TicTacToe {
    private final static int x = 1;
    private final static int o = 0;
    private final static int empty = 2;
    private int[][] grid;

    TicTacToe() {
        grid = new int[3][3];

        for(int row=0;row<grid.length;row++)
            for(int col=0;col<grid[0].length;col++)
                grid[row][col] = empty;
    }
    public void playerMove(int row, int col) {
        if(grid[row][col]==empty)
            grid[row][col] = x;
    }
    public int[] computerMove() {
        boolean moved = false;
        int randRow=0;
        int randCol=0;

        while (!moved) {
            randRow = (int)(Math.random()*3);
            randCol = (int)(Math.random()*3);
            if(grid[randRow][randCol]==empty) {
                grid[randRow][randCol] = o;
                moved = true;
            }
            int count=0;
            for(int row=0;row<grid.length;row++)
                for(int col=0;col<grid[0].length;col++)
                    if(grid[row][col]==x || grid[row][col]==o)
                        count++;
            if(count==9)
                moved = true;
        }
        return new int[]{randRow, randCol};
    }
    public boolean playerWin() {
        //Checks rows
        if(x==grid[0][0] && x==grid[0][1] && x==grid[0][2])
            return true;
        else if(x==grid[1][0] && x==grid[1][1] && x==grid[1][2])
            return true;
        else if(x==grid[2][0] && x==grid[2][1] && x==grid[2][2])
            return true;
            //Checks cols
        else if(x==grid[0][0] && x==grid[1][0] && x==grid[2][0])
            return true;
        else if(x==grid[0][1] && x==grid[1][1] && x==grid[2][1])
            return true;
        else if(x==grid[0][2] && x==grid[1][2] && x==grid[2][2])
            return true;
            //Checks diagonals
        else if(x==grid[0][0] && x==grid[1][1] && x==grid[2][2])
            return true;
        else if(x==grid[0][2] && x==grid[1][1] && x==grid[2][0])
            return true;
        else
            return false;
    }
    public boolean computerWin() {
        //Checks rows
        if(o==grid[0][0] && o==grid[0][1] && o==grid[0][2])
            return true;
        else if(o==grid[1][0] && o==grid[1][1] && o==grid[1][2])
            return true;
        else if(o==grid[2][0] && o==grid[2][1] && o==grid[2][2])
            return true;
            //Checks cols
        else if(o==grid[0][0] && o==grid[1][0] && o==grid[2][0])
            return true;
        else if(o==grid[0][1] && o==grid[1][1] && o==grid[2][1])
            return true;
        else if(o==grid[0][2] && o==grid[1][2] && o==grid[2][2])
            return true;
            //Checks diagonals
        else if(o==grid[0][0] && o==grid[1][1] && o==grid[2][2])
            return true;
        else if(o==grid[0][2] && o==grid[1][1] && o==grid[2][0])
            return true;
        else
            return false;
    }
    public boolean gameTie() {
        int count = 0;
        for(int row=0;row<grid.length;row++)
            for(int col=0;col<grid[0].length;col++)
                if(grid[row][col] != empty)
                    count++;
        if(count==9 && !computerWin() && !playerWin())
            return true;
        else
            return false;
    }
}
