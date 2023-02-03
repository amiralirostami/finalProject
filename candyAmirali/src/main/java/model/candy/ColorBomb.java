package model.candy;

public class ColorBomb extends Candy {
    public ColorBomb(int x, int y) {
        super(13, x, y, "");
    }

    private void wrappedColorBombCombine(Candy candy, Candy[][] board) {
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j].colour == candy.colour) {
                    board[i][j] = new WrappedCandy(candy.colour, i, j);
                }
            }
        }
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j].colour == candy.colour) {
                    board[i][j].crush(board);
                }
            }
        }
    }

    private void stripedColorBombCombine(StripedCandy candy, Candy[][] board) {
        String direction = "H";
        if (candy.isVertical()) direction = "V";
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j].colour == candy.colour) {
                    board[i][j] = new StripedCandy(candy.colour, i, j, direction);
                }
            }
        }
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j].colour == candy.colour) {
                    board[i][j].crush(board);
                }
            }
        }

    }

    private void colorBombColorBombCombine(Candy candy, Candy[][] board) {
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                board[i][j].crush(board);
            }
        }
    }

    private void colorBombCandyCombine(Candy candy, Candy[][] board) {
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j].colour == candy.getColour())
                    board[i][j].crush(board);
            }
        }
    }

    @Override
    public boolean combine(Candy candy, Candy[][] board) {
        if (candy instanceof ColorBomb) colorBombColorBombCombine(candy, board);
        else if (candy instanceof StripedCandy) stripedColorBombCombine((StripedCandy) candy, board);
        else if (candy instanceof WrappedCandy) wrappedColorBombCombine(candy, board);
        else colorBombCandyCombine(candy, board);
        candy.crush(board);
        crush(board);
        return true;

    }
}
