import view.MainMenu;
import view.ProfileMenu;
import view.RegisterMenu;
import view.ShopMenu;

import java.awt.*;
import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

/**
 * main class is a driver for all menus of o game
 */
public class Main {

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        RegisterMenu registerMenu = new RegisterMenu();
        MainMenu mainMenu = new MainMenu();
        ShopMenu shopMenu = new ShopMenu();
        ProfileMenu profileMenu = new ProfileMenu();
        int menu = 0;
        while (menu != -1) {
            switch (menu) {
                case 0:
                    menu = registerMenu.run(myScanner);
                    break;
                case 1:
                    menu = mainMenu.run(myScanner);
                    break;
                case 2:
                    menu = profileMenu.run(myScanner);
                    break;
                case 3:
                    menu = shopMenu.run(myScanner);
            }

        }
    }
}
