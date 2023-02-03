package view;

import controller.UserController;

import java.util.regex.Matcher;

public class ShopMenu extends Menu{
    private static final String BUY_POWER_UP_REGEX = "^buy (?<booster>\\S+) (?<count>-?\\d+)$";
    @Override
    protected boolean commands(String command){
        if(command.matches("^show money$")){
            System.out.println("wallet : " + UserController.getLoggedInUser().getBalance());
            return false;
        }
        else if(command.matches("^show inventory$")) System.out.println(UserController.showInventory());
        else if(command.matches(BUY_POWER_UP_REGEX)){
            Matcher matcher = getMatcher(BUY_POWER_UP_REGEX,command);
            System.out.println(UserController.buyItem(matcher.group("booster"),Integer.parseInt(matcher.group("count"))));
        }
        else if(command.matches("^back$")) {
            nextMenu = 1;
            return true;
        }
        else System.out.println("invalid command");
        return false;
    }

    protected String getMenuName(){return "Shop Menu";}

}
