package view;

import controller.GameController;
import controller.UserController;

import java.util.regex.Matcher;

public class GameMenu extends Menu {
    private static final String SWIPE_REGEX = "swipe (?<x>-?\\d+) (?<y>-?\\d+) (?<direction>[RULD])";
    private static final String ACTIVE_LOLLIPOP_REGEX = "active lollipop hammer (?<x>-?\\d+) (?<y>-?\\d+)";
    private static final String ACTIVE_COLOR_BOMB_REGEX = "active colour bomb brush (?<x>-?\\d+) (?<y>-?\\d+)";
    private static final String ACTIVE_WRAPPED_REGEX = "active wrapped brush (?<x>-?\\d+) (?<y>-?\\d+)";
    private static final String ACTIVE_STRIPED_REGEX = "active striped brush (?<x>-?\\d+) (?<y>-?\\d+) (?<direction>[vh])";
    private static final String ACTIVE_FREE_SWITCH_REGEX = "active free switch (?<x>-?\\d+) (?<y>-?\\d+) (?<direction>[RULD])";
    private boolean startingGame = true;

    @Override
    protected boolean commands(String command) {
        if(startingGame) {
            startingGame = false;
        }
        if (command.matches(SWIPE_REGEX)) {
            Matcher matcher = getMatcher(SWIPE_REGEX, command);
            String result = GameController.swipe(Integer.parseInt(matcher.group("x"))
                    , Integer.parseInt(matcher.group("y"))
                    , matcher.group("direction"));
            System.out.println(result);
            System.out.print(GameController.print());
            return !result.equals("swipe cell successful") && !result.startsWith("invalid");
        }  else if (command.matches(ACTIVE_LOLLIPOP_REGEX)) {
            Matcher matcher = getMatcher(ACTIVE_LOLLIPOP_REGEX, command);
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            System.out.println(GameController.activeLollipop(x, y));
        } else if (command.matches(ACTIVE_COLOR_BOMB_REGEX)) {
            Matcher matcher = getMatcher(ACTIVE_COLOR_BOMB_REGEX, command);
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            System.out.println(GameController.activeColorBomb(x, y));
        } else if (command.matches(ACTIVE_WRAPPED_REGEX)) {
            Matcher matcher = getMatcher(ACTIVE_WRAPPED_REGEX, command);
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            System.out.println(GameController.activeWrapped(x, y));
        } else if (command.matches(ACTIVE_STRIPED_REGEX)) {
            Matcher matcher = getMatcher(ACTIVE_STRIPED_REGEX, command);
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            System.out.println(GameController.activeStriped(x, y, matcher.group("direction")));
        } else if (command.matches(ACTIVE_FREE_SWITCH_REGEX)) {
            Matcher matcher = getMatcher(ACTIVE_FREE_SWITCH_REGEX, command);
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            System.out.println(GameController.activeFreeSwitch(x, y, matcher.group("direction")));
        } else if (command.matches("show boosters")) System.out.println(UserController.showInventory());
        else if (command.matches("active extra moves")) System.out.println(GameController.activeExtraMove());
        else System.out.println("invalid command");
        System.out.print(GameController.print());
        return false;
    }

    protected String getMenuName() {
        return "Game Menu";
    }

}
