package view;


import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * with this class and all its child we provide all menus that needed for our game
 */
public class Menu {
    protected int nextMenu;
    protected Scanner scanner;
    protected static int x = 0;
    public int run(Scanner scanner) {
        String command;
        this.scanner = scanner;
        while (true) {
            command = scanner.nextLine();
            clearScreen();
            System.out.println(command);
            if (command.matches("^show current menu$")) {
                System.out.println(getMenuName());
                continue;
            }
            if (commands(command))
                break;
        }
        return nextMenu;
    }

    protected Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        matcher.find();
        return matcher;
    }

    protected String getMenuName() {
        return "Menu";
    }

    protected boolean commands(String command) {
        return false;
    }

    protected void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception ignored) {
        }
    }
}
