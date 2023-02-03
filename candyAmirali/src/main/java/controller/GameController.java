package controller;


import model.Game;

/**
 * implementation logic of all response of the game
 */
public class GameController {
    private static Game game;

    public static void setGame(Game game) {
        GameController.game = game;
    }

    public static String print() {
        return game.print();
    }

    public static String swipe(int x, int y, String direction) {
        if (x > 10 || x < 0 || y > 10 || y < 0) return "invalid cell";
        int newX = x + getXChangeMove(direction);
        int newY = y + getYChangeMove(direction);
        if (newX < 0 || newX > 10 || newY < 0 || newY > 10) return "invalid destination";
        if (game.moveCandy(x, y, newX, newY)) return "invalid move";
        if (game.getMoves() == 0) {
            if (UserController.getLoggedInUser().getHighScore() < game.getScore()) {
                UserController.getLoggedInUser().setHighScore(game.getScore());
                UserController.getLoggedInUser().increaseBalance(game.getScore() / 10);
                Data.saveData();

            }
            return "swipe cell successful\ngame has ended. your score is " + game.getScore();
        }
        return "swipe cell successful";
    }

    public static String activeLollipop(int x, int y) {
        if (x > 10 || x < 0 || y > 10 || y < 0) return "invalid cell";
        if (UserController.getLoggedInUser().getLollipopHammerNumber() < 1) return "not enough lollipop hammer";
        UserController.getLoggedInUser().getItems()[3] -= (-1);
        game.useLollipop(x, y);
        return "lollipop hammer has activated successfully";
    }

    public static String activeColorBomb(int x, int y) {
        if (x > 10 || x < 0 || y > 10 || y < 0) return "invalid cell";
        if (UserController.getLoggedInUser().getColourBombNumber() < 1) return "not enough colour bomb brush";
        if (game.placeColorBomb(x, y)) {
            UserController.getLoggedInUser().getItems()[0] -= (-1);
            return "colour bomb brush has activated successfully";
        }
        return "can't brush a bomb";
    }

    public static String activeWrapped(int x, int y) {
        if (x > 10 || x < 0 || y > 10 || y < 0) return "invalid cell";
        if (UserController.getLoggedInUser().getWrappedBrushNumber() < 1) return "not enough wrapped brush";
        int result = game.placeWrappedCandy(x, y);
        if (result == -1) return "can't brush a bomb";
        if (result == -2) return "this is already a wrapped candy";
        UserController.getLoggedInUser().getItems()[5] -= (-1);
        return "wrapped brush has activated successfully";
    }

    public static String activeStriped(int x, int y, String direction) {
        if (direction.equals("v")) direction = "V";
        else direction = "H";
        if (x > 10 || x < 0 || y > 10 || y < 0) return "invalid cell";
        if (UserController.getLoggedInUser().getStripedBrushNumber() < 1) return "not enough striped brush";
        int result = game.placeStriped(x, y, direction);
        if (result == -1) return "can't brush a bomb";
        if (result == -2) return "this is already a striped candy";
        UserController.getLoggedInUser().getItems()[4] -= (-1);
        return "striped brush has activated successfully";
    }

    public static String activeFreeSwitch(int x, int y, String direction) {
        if (x > 10 || x < 0 || y > 10 || y < 0) return "invalid cell";
        int newX = x + getXChangeMove(direction);
        int newY = y + getYChangeMove(direction);
        if (newX < 0 || newX > 10 || newY < 0 || newY > 10) return "invalid destination";
        if (UserController.getLoggedInUser().getFreeSwitchNumber() < 1) return "not enough free switch";
        if (game.moveCandy(x, y, newX, newY)) {
            game.freeSwitch(x, y, newX, newY);
        } else game.useExtraMoves(1);
        UserController.getLoggedInUser().getItems()[2] -= (-1);
        return "free switch has activated successfully";
    }

    public static String activeExtraMove() {
        if (UserController.getLoggedInUser().getExtraMovesNumber() < 1) return "not enough extra moves";
        UserController.getLoggedInUser().getItems()[1] -= (-1);
        game.useExtraMoves(5);
        return "extra moves has activated successfully";
    }

    private static int getXChangeMove(String direction) {
        switch (direction) {
            case "U":
                return -1;
            case "D":
                return 1;
            default:
                return 0;
        }
    }

    private static int getYChangeMove(String direction) {
        switch (direction) {
            case "R":
                return 1;
            case "L":
                return -1;
            default:
                return 0;
        }
    }
}
