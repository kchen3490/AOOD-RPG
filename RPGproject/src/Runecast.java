//Minigame#2, Back-end class by Kai C.

public class Runecast {
    private final static boolean red = false;
    private final static boolean yellow = true;
    private boolean[][] grid;

    Runecast() {
        int randChoice = (int)(Math.random()*3);
        grid = new boolean[3][3];

        if(randChoice==0)
            for(int row=0; row<grid.length; row++)
                for(int col=0;col<grid[0].length;col++)
                    grid[row][col]=red;
        else if(randChoice==1)
            for(int row=0; row<grid.length; row++)
                for(int col=0;col<grid[0].length;col++)
                    if((row==1 && (col==0 || col==2) ||
                            row==2 && col==1))
                        grid[row][col]=yellow;
                    else
                        grid[row][col]=red;
        else
            for(int row=0; row<grid.length; row++)
                for(int col=0;col<grid[0].length;col++)
                    if((row==0 && col==1) ||
                            (row==1 && (col==0 || col==2) ||
                                    (row==2 && col==1)))
                        grid[row][col]=yellow;
                    else
                        grid[row][col]=red;
    }

    public void updateGrid(int row, int col) {
        if(grid[row][col]==red)
            grid[row][col]=yellow;
        else
            grid[row][col]=red;
        if(row<2)
            if(grid[row+1][col]==red)
                grid[row+1][col]=yellow;
            else
                grid[row+1][col]=red;
        if(row>0)
            if(grid[row-1][col]==red)
                grid[row-1][col]=yellow;
            else
                grid[row-1][col]=red;
        if(col<2)
            if(grid[row][col+1]==red)
                grid[row][col+1]=yellow;
            else
                grid[row][col+1]=red;
        if(col>0)
            if(grid[row][col-1]==red)
                grid[row][col-1]=yellow;
            else
                grid[row][col-1]=red;
    }
    public boolean checkWin() {
        boolean weWinThese = false;
        int yellowCount = 0;

        for(int row=0;row<grid.length;row++)
            for(int col=0;col<grid[0].length;col++)
                if(grid[row][col]==yellow)
                    yellowCount++;
        if(yellowCount==9)
            weWinThese = true;
        return weWinThese;
    }
    public boolean[][] getGrid() {
        return grid;
    }
}
