package model.candy;

public class WrappedCandy extends Candy{
    public WrappedCandy(int colour,int x,int y){
        super(colour,x,y,"W");
    }
    private void wrappedStripedCombine(Candy candy,Candy[][] board){
        for(int j = y - 1; j <= y + 1;j++){
            if(j >= 0 && j < 11){
                for (int i = 0; i < 11; i++) {
                    if(!board[i][j].getDestroyed())
                        board[i][j].crush(board);
                }
            }
        }
        for (int i = x - 1; i <= x + 1; i++) {
            if(i >= 0 && i < 11){
                for (int j = 0; j < 11; j++) {
                    board[i][j].crush(board);
                }
            }
        }
    }

    private void wrappedWrappedCombine(Candy candy,Candy[][] board) {
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                if(i < 11 && j < 11 && i >= 0 && j >= 0){
                    board[i][j].crush(board);
                }
            }
        }
        for (int i = candy.getX() - 2; i <= candy.getX() + 2; i++) {
            for (int j = candy.getY() - 2; j <= candy.getY() + 2; j++) {
                if(i < 11 && j < 11 && i >= 0 && j >= 0){
                    board[i][j].crush(board);
                }
            }
        }
    }

    private void wrappedColorBombCombine(Candy candy,Candy[][] board){
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--){
                if(board[i][j].colour == colour){
                    board[i][j] = new WrappedCandy(colour,i,j);
                }
            }
        }
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--){
                if(board[i][j].colour == colour){
                    board[i][j].crush(board);
                }
            }
        }

    }
        @Override
    public boolean combine(Candy candy, Candy[][] board){
        if(candy instanceof WrappedCandy)wrappedWrappedCombine(candy,board);
        else if(candy instanceof StripedCandy)wrappedStripedCombine(candy,board);
        else if(candy instanceof ColorBomb)wrappedColorBombCombine(candy,board);
        else return false;
        candy.crush(board);
        crush(board);
        return true;

    }
    protected void destroy(Candy[][] board){
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if(i < 11 && j < 11 && i >= 0 && j >= 0)
                    board[i][j].crush(board);

            }
        }
    }
}
