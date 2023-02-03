package model.candy;

/**
 * in this class and all of its subclasses we implemented all types of candy in the game
 * and their crushes and combines
 */
public class Candy {
    protected String icon;
    protected final int colour;
    protected int x;
    protected int y;
    protected boolean isDestroyed;

    public Candy(int colour, int x, int y, String suffix) {
        this.colour = colour;
        this.x = x;
        this.y = y;
        isDestroyed = false;
        String icon;
        switch (colour) {
            case 0:
                icon = "R";
                break;
            case 1:
                icon = "O";
                break;
            case 2:
                icon = "Y";
                break;
            case 3:
                icon = "G";
                break;
            case 4:
                icon = "B";
                break;
            case 5:
                icon = "P";
                break;
            default:
                icon = "CB";
        }
        this.icon = icon + suffix;
    }

    public String getIcon() {
        return icon;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void crush(Candy[][] board) {
        if (!isDestroyed) {
            isDestroyed = true;
            destroy(board);
        }
    }

    public boolean getDestroyed() {
        return isDestroyed;
    }

    public int getColour() {
        return colour;
    }

    public boolean combine(Candy candy, Candy[][] board) {
        if (candy instanceof ColorBomb) {
            colorBombCandyCombine(candy, board);
            candy.crush(board);
            return true;
        }
        return false;
    }

    protected void destroy(Candy[][] board) {
    }

    private void colorBombCandyCombine(Candy candy, Candy[][] board) {
        for (int i = 10; i >= 0; i--) {
            for (int j = 10; j >= 0; j--) {
                if (board[i][j].colour == colour)
                    board[i][j].crush(board);
            }
        }
    }

}
