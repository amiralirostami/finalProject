package model.candy;

public class StripedCandy extends Candy {
    private final boolean isVertical;

    public StripedCandy(int colour, int x, int y, String direction) {
        super(colour, x, y, direction);
        isVertical = direction.equals("V");
    }

    public boolean isVertical() {
        return isVertical;
    }

    private void stripedStripedCombine(Candy candy, Candy[][] board) {
        for (int i = 0; i < 11; i++) {
            board[i][y].crush(board);
        }
        for (int j = 0; j < 11; j++) {
            board[x][j].crush(board);
        }
        for (int i = 0; i < 11; i++) {
            board[i][candy.getY()].crush(board);
        }
        for (int j = 0; j < 11; j++) {
            board[candy.getX()][j].crush(board);
        }
    }

    private void stripedWrappedCombine(Candy candy, Candy[][] board) {
        for (int j = y - 1; j <= y + 1; j++) {
            if (j >= 0 && j < 11) {
                for (int i = 0; i < 11; i++) {
                    if (!board[i][j].getDestroyed())
                        board[i][j].crush(board);
                }
            }
        }
        for (int i = x - 1; i <= x + 1; i++) {
            if (i >= 0 && i < 11) {
                for (int j = 0; j < 11; j++) {
                    if (!board[i][j].getDestroyed())
                        board[i][j].crush(board);
                }
            }
        }


    }

    private void stripedColorBombCombine(Candy candy, Candy[][] board) {
        String direction = "H";
        if (isVertical) direction = "V";
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j].colour == colour) {
                    board[i][j] = new StripedCandy(colour, i, j, direction);
                }
            }
        }
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j].colour == colour) {
                    board[i][j].crush(board);
                }
            }
        }

    }


    @Override
    public boolean combine(Candy candy, Candy[][] board) {
        if (candy instanceof StripedCandy) stripedStripedCombine(candy, board);
        else if (candy instanceof WrappedCandy) stripedWrappedCombine(candy, board);
        else if (candy instanceof ColorBomb) stripedColorBombCombine(candy, board);
        else return false;
        candy.crush(board);
        crush(board);
        return true;

    }

    protected void destroy(Candy[][] board) {
        if (isVertical) {
            for (int i = 0; i < 11; i++) {
                board[i][y].crush(board);
            }
        } else {
            for (int j = 0; j < 11; j++) {
                board[x][j].crush(board);
            }
        }

    }
}
