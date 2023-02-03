package view;

import controller.UserController;

import java.util.regex.Matcher;

public class RegisterMenu extends Menu {
    private static final String ADD_USER_REGEX = "^register u (?<username>\\S+) p (?<password>\\S+) n (?<nickname>.+)";
    private static final String LOGIN_REGEX = "^login u (?<username>\\S+) p (?<password>\\S+)$";

    @Override
    protected boolean commands(String command) {
        if (command.matches(ADD_USER_REGEX)) {
            Matcher matcher = getMatcher(ADD_USER_REGEX, command);
            System.out.println(UserController.addUser(matcher.group("nickname")
                    , matcher.group("username")
                    , matcher.group("password")));
        } else if (command.matches(LOGIN_REGEX)) {
            Matcher matcher = getMatcher(LOGIN_REGEX, command);
            String response = UserController.login(matcher.group("username"), matcher.group("password"));
            System.out.println(response);
            if (response.equals("user successfully logged in!")) {
                nextMenu = 1;
                return true;
            }
        } else if (command.matches("^list of users$")) {
            System.out.print(UserController.userList());
        } else if (command.matches("exit")) {
            nextMenu = -1;
            return true;
        } else System.out.println("invalid command");
        return false;
    }

    protected String getMenuName() {
        return "Register Menu";
    }
}
