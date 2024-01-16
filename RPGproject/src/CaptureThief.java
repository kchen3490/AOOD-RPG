//Minigame#3, Back-end class by Kai C.

public class CaptureThief {
    private int[][] grid;
    private final static int player = 0;
    private final static int computer = 1;
    private final static int empty = 2;
    private final static int possible = 3;
    private final static int playerSelect = 4;
    private final static int tree = 5;
    private final static int maxMove = 30;
    private static int move;

    CaptureThief() {
        grid = new int[5][8];
        move = 0;

        for(int row=0;row<grid.length;row++)
            for(int col=0;col<grid[0].length;col++)
                grid[row][col] = empty;
        grid[1][0] = player;
        grid[3][0] = player;

        int count=0;
        int randRow;
        int randCol;
        while(count<4) {
            randRow = (int)(Math.random()*5);
            randCol = (int)(Math.random()*8);
            if(grid[randRow][randCol]==empty) {
                grid[randRow][randCol]=tree;
            }
            count++;
        }
        boolean thiefPlaced = false;
        while(!thiefPlaced) {
            randRow = (int)(Math.random()*5);
            randCol = (int)(Math.random()*6+2);
            if(grid[randRow][randCol] == empty && !playerWin()) {
                grid[randRow][randCol] = computer;
                thiefPlaced = true;
            }
        }
    }
    public void playerMove(int row, int col) {
        grid[row][col]=player;
        for(int tempRow=0; tempRow<grid.length; tempRow++)
            for(int tempCol=0; tempCol<grid[0].length; tempCol++)
                if(grid[tempRow][tempCol]==possible)
                    grid[tempRow][tempCol]=empty;
                else if(grid[tempRow][tempCol]==playerSelect)
                    grid[tempRow][tempCol]=empty;
        move++;
    }
    public void playerChoose(int row, int col) {
        if(grid[row][col]==player)
            grid[row][col]=playerSelect;
        try {
            if(grid[row+1][col]==empty)
                grid[row+1][col] = possible;
        } catch(ArrayIndexOutOfBoundsException ignored) {}
        try {
            if(grid[row-1][col]==empty)
                grid[row-1][col] = possible;
        } catch(ArrayIndexOutOfBoundsException ignored) {}
        try {
            if(grid[row][col+1]==empty)
                grid[row][col+1] = possible;
        } catch(ArrayIndexOutOfBoundsException ignored) {}
        try {
            if(grid[row][col-1]==empty)
                grid[row][col-1] = possible;
        } catch(ArrayIndexOutOfBoundsException ignored) {}
    }
    public void playerCancel() {
        for(int row=0; row<grid.length; row++)
            for(int col=0; col<grid[0].length; col++)
                if(grid[row][col]==possible)
                    grid[row][col]=empty;
                else if(grid[row][col]==playerSelect)
                    grid[row][col]=player;
    }
    public void computerMove() {
        int row = 0;
        int col = 0;
        for(int tRow=0; tRow<grid.length; tRow++)
            for(int tCol=0; tCol<grid[0].length; tCol++)
                if(grid[tRow][tCol]==computer) {
                    row = tRow;
                    col = tCol;
                }
        while(grid[row][col]==computer && !playerWin()) {
            int randMove = (int)(Math.random()*4);
            if(randMove==0) {
                try {
                    if(grid[row-1][col]==empty) {
                        grid[row-1][col] = computer;
                        grid[row][col] = empty;
                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {}
            }
            else if(randMove==1) {
                try {
                    if(grid[row+1][col]==empty) {
                        grid[row+1][col] = computer;
                        grid[row][col] = empty;
                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {}
            }
            else if(randMove==2) {
                try {
                    if(grid[row][col-1]==empty) {
                        grid[row][col-1] = computer;
                        grid[row][col] = empty;
                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {}
            }
            else {
                try {
                    if(grid[row][col+1]==empty) {
                        grid[row][col+1] = computer;
                        grid[row][col] = empty;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {}
            }
        }
    }
    public boolean playerWin() {
        boolean weWinThese = false;
        int row = 0;
        int col = 0;
        for(int tRow=0; tRow<grid.length; tRow++)
            for(int tCol=0; tCol<grid[0].length; tCol++)
                if(grid[tRow][tCol]==computer) {
                    row = tRow;
                    col = tCol;
                }
        try {
            if(grid[row+1][col]!=empty)
                if(grid[row-1][col]!=empty)
                    if(grid[row][col+1]!=empty)
                        if(grid[row][col-1]!=empty)
                            weWinThese = true;
        } catch(ArrayIndexOutOfBoundsException e0) {
            try {
                if(grid[row+1][col]!=empty && !weWinThese)
                    if(grid[row-1][col]!=empty)
                        if(grid[row][col+1]!=empty)
                            weWinThese = true;
            } catch(ArrayIndexOutOfBoundsException e1) {
                try {
                    if(grid[row+1][col]!=empty && !weWinThese)
                        if(grid[row-1][col]!=empty)
                            if(grid[row][col-1]!=empty)
                                weWinThese = true;
                } catch(ArrayIndexOutOfBoundsException e2) {
                    try {
                        if(grid[row+1][col]!=empty && !weWinThese)
                            if(grid[row][col+1]!=empty)
                                if(grid[row][col-1]!=empty)
                                    weWinThese = true;
                    } catch(ArrayIndexOutOfBoundsException e3) {
                        try {
                            if(grid[row-1][col]!=empty && !weWinThese)
                                if(grid[row][col+1]!=empty)
                                    if(grid[row][col-1]!=empty)
                                        weWinThese = true;
                        } catch(ArrayIndexOutOfBoundsException e4) {
                            try {
                                if(grid[row-1][col]!=empty && !weWinThese)
                                    if(grid[row][col-1]!=empty)
                                        weWinThese = true;
                            } catch(ArrayIndexOutOfBoundsException e5) {
                                try {
                                    if(grid[row-1][col]!=empty && !weWinThese)
                                        if(grid[row][col+1]!=empty)
                                            weWinThese = true;
                                } catch(ArrayIndexOutOfBoundsException e6) {
                                    try {
                                        if(grid[row+1][col]!=empty && !weWinThese)
                                            if(grid[row][col-1]!=empty)
                                                weWinThese = true;
                                    } catch(ArrayIndexOutOfBoundsException e7) {
                                        try {
                                            if(grid[row+1][col]!=empty && !weWinThese)
                                                if(grid[row][col+1]!=empty)
                                                    weWinThese = true;
                                        } catch(ArrayIndexOutOfBoundsException ignored) {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return weWinThese;
    }
    public boolean playerLost() {
        if(!playerWin() && move>=maxMove)
            return true;
        else
            return false;
    }
    public int[][] getGrid() {
        return grid;
    }
    public int[] getMoves() {
        return new int[]{maxMove, move};
    }
}
