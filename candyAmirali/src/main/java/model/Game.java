package model;

import controller.Function;
import model.candy.Candy;
import model.candy.ColorBomb;
import model.candy.StripedCandy;
import model.candy.WrappedCandy;

/**
 * this class provides the environment for playing game and makes the proper changes
 */
public class Game {
    private final Candy[][] board;
    private int moves;
    private final Function randomFunction;
    private int score;


    public Game(int seed, int moves) {
        board = new Candy[11][11];
        this.moves = moves;
        score = 0;
        randomFunction = new Function(seed);
        initBoard();
    }

    public int getMoves() {
        return moves;
    }

    public int getScore() {
        return score;
    }

    private void initBoard() {
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                board[i][j] = new Candy(randomFunction.nextInt(), i, j, "");
            }
        }
    }

    public String print() {
        StringBuilder boardString = new StringBuilder();
        boardString.append("Moves: ").append(moves).append("\n").append("Score: ").append(score).append("\n");
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                boardString.append(board[i][j].getIcon());
                boardString.append("\t");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }


    public boolean moveCandy(int x, int y, int newX, int newY) {
        Candy temp = board[x][y];
        board[x][y] = board[newX][newY];
        board[newX][newY] = temp;
        board[newX][newY].setX(newX);
        board[newX][newY].setY(newY);
        board[x][y].setX(x);
        board[x][y].setY(y);
        boolean isValidCombine = board[x][y].combine(board[newX][newY], board);
        boolean isValidCrush =  markToDestroyCandies(board);
        if (!isValidCombine && !isValidCrush) {
            temp = board[x][y];
            board[x][y] = board[newX][newY];
            board[newX][newY] = temp;
            board[newX][newY].setX(newX);
            board[newX][newY].setY(newY);
            board[x][y].setX(x);
            board[x][y].setY(y);
            return true;
        }
        moves--;
        combos();
        return false;
    }

    public void useLollipop(int x, int y) {
        board[x][y].crush(board);
        combos();
    }

    public boolean placeColorBomb(int x, int y) {
        if (board[x][y] instanceof ColorBomb) return false;
        board[x][y] = new ColorBomb(x, y);
        return true;
    }

    public int placeWrappedCandy(int x, int y) {
        if (board[x][y] instanceof ColorBomb) return -1;
        if (board[x][y] instanceof WrappedCandy) return -2;
        board[x][y] = new WrappedCandy(board[x][y].getColour(), x, y);
        return 0;
    }

    public int placeStriped(int x, int y, String direction) {
        if (board[x][y] instanceof ColorBomb) return -1;
        if (board[x][y] instanceof StripedCandy) return -2;
        board[x][y] = new StripedCandy(board[x][y].getColour(), x, y, direction);
        return 0;
    }

    public void freeSwitch(int x, int y, int newX, int newY) {
        Candy temp = board[x][y];
        board[x][y] = board[newX][newY];
        board[newX][newY] = temp;
        board[newX][newY].setX(newX);
        board[newX][newY].setY(newY);
        board[x][y].setX(x);
        board[x][y].setY(y);

    }

    private void combos() {
        boolean isValidCrush = true;
        int combo = 1;
        int destroyedCandies;
        while (isValidCrush) {
            destroyedCandies = destroyCandies();
            score += destroyedCandies * 60 * combo;
            fallCandies();
            fillFreeSpace();
            isValidCrush = markToDestroyCandies(board);
            combo++;
        }
    }

    public void useExtraMoves(int x) {
        moves += x;
    }

    private int destroyCandies() {
        int numberOfDestroyedCandies = 0;
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j].getDestroyed()) {
                    board[i][j] = null;
                    numberOfDestroyedCandies++;
                }
            }
        }
        return numberOfDestroyedCandies;
    }

    private void fallCandies() {
        int place;
        for (int j = 10; j >= 0; j--) {
            for (int i = 9; i >= 0; i--) {
                if (board[i][j] != null) {
                    place = i + 1;
                    while (place < 11 && board[place][j] == null) {
                        place++;
                    }
                    place--;
                    if (place != i) {
                        board[place][j] = board[i][j];
                        board[place][j].setX(place);
                        board[i][j] = null;
                    }
                }
            }
        }
    }

    private void fillFreeSpace() {
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j] == null) {
                    board[i][j] = new Candy(randomFunction.nextInt(), i, j, "");
                }
            }
        }
    }

    private boolean markToDestroyCandies(Candy[][] newBoard) {
        boolean destroyed = false;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (!newBoard[i][j].getDestroyed()) {
                    int l = j;
                    int r = j;
                    while (l >= 0 && (newBoard[i][l].getColour() == newBoard[i][j].getColour())) l--;
                    l++;
                    while (r <= 10 && (newBoard[i][r].getColour() == newBoard[i][j].getColour())) r++;
                    if (r - l >= 3) {
                        for (int k = l; k < r; k++) {
                            newBoard[i][k].crush(newBoard);
                            destroyed = true;
                        }
                    }
                    l = i;
                    r = i;
                    while (l >= 0 && (newBoard[l][j].getColour() == newBoard[i][j].getColour())) l--;
                    l++;
                    while (r <= 10 && (newBoard[r][j].getColour() == newBoard[i][j].getColour())) r++;
                    if (r - l >= 3) {
                        for (int k = l; k < r; k++) {
                            newBoard[k][j].crush(newBoard);
                            destroyed = true;
                        }
                    }
                }
            }
        }
        return destroyed;
    }
}
