package view;

import controller.UserController;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu {
    private static final String RESET_PASSWORD_REGEX = "^change password (?<oldPassword>\\S+) (?<newPassword>\\S+)$";
    private static final String REMOVE_ACCOUNT_REGEX = "^remove account (?<password>\\S+)$";
    private static final String RESET_USERNAME_REGEX = "^change username (?<oldUsername>\\S+) (?<newUsername>\\S+)$";

    protected boolean commands(String command) {
        if (command.matches(RESET_PASSWORD_REGEX)) {
            Matcher matcher = getMatcher(RESET_PASSWORD_REGEX, command);
            System.out.println(UserController.resetPassword(matcher.group("oldPassword"), matcher.group("newPassword")));
            return false;
        } else if (command.matches("^back$")) {
            nextMenu = 1;
            return true;
        } else if (command.matches("^show information$")) System.out.println(UserController.showProfile());
        else if (command.matches(REMOVE_ACCOUNT_REGEX)) {
            Matcher matcher = getMatcher(REMOVE_ACCOUNT_REGEX, command);
            String result = UserController.removeAccount(matcher.group("password"));
            System.out.println(result);
            nextMenu = 0;
            return result.equals("account deleted!");
        } else if(command.matches(RESET_USERNAME_REGEX)){
            Matcher matcher=getMatcher(RESET_USERNAME_REGEX,command);
            System.out.println(UserController.resetUsername(matcher.group("oldUsername"), matcher.group("newUsername")));
            return false;
        }
        else System.out.println("invalid command");

        return false;
    }

    protected String getMenuName() {
        return "Profile Menu";
    }
}
