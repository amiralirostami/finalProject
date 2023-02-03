package view;

import controller.GameController;
import controller.UserController;
import model.Game;

import java.util.regex.Matcher;

public class MainMenu extends Menu {
    private static final String START_GAME_REGEX = "start new game (?<seed>-?\\d+) (?<moves>\\d+)";

    @Override
    protected boolean commands(String command) {
        if (command.matches(START_GAME_REGEX)) {
            Matcher matcher = getMatcher(START_GAME_REGEX, command);
            if(Integer.parseInt(matcher.group("moves")) == 0) System.out.println("invalid command");
            else {
                GameMenu gameMenu = new GameMenu();
                Game game = new Game(Integer.parseInt(matcher.group("seed")), Integer.parseInt(matcher.group("moves")));
                GameController.setGame(game);
                System.out.print(GameController.print());
                gameMenu.run(scanner);
            }
            return false;
        } else if (command.matches("enter Profile menu")) {
            nextMenu = 2;
            System.out.println("entered Profile menu!");
        } else if (command.matches("enter Shop menu")) {
            nextMenu = 3;
            System.out.println("entered Shop menu!");
        } else if (command.matches("logout")) {
            nextMenu = 0;
            System.out.println("user logged out!");
        } else if (command.matches("show scoreboard")) {
            System.out.print(UserController.showScoreboard());
            return false;
        } else {
            System.out.println("invalid command");
            return false;
        }
        return true;

    }

    protected String getMenuName() {
        return "Main Menu";
    }
}
