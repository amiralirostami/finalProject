package controller;

import model.User;
import model.items.*;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * this class controls all information and functions of players (user)
 */
public class UserController {
    private static ArrayList<User> USER_ARRAY_LIST = new ArrayList<>();

    static {
        USER_ARRAY_LIST = Data.loadUsers();
    }

    private static User loggedInUser;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static String addUser(String nickname, String username, String password) {
        if (!username.matches("^\\w+$")) return "username's format is invalid!";
        if (!nickname.matches("^[a-zA-Z ]+$")) return "nickname's format is invalid!";
        if (isPasswordWeak(password)) return "password is weak!";
        if (getUserByUsername(username) != null) return "username already exists!";
        User user = new User(username, password, nickname);
        USER_ARRAY_LIST.add(user);
        Data.saveData();
        return "user successfully created!";

    }

    public static String login(String username, String password) {
        if (!username.matches("^\\w+$")) return "username's format is invalid!";
        User user = getUserByUsername(username);
        if (user == null) return "username doesn't exist!";
        if (!user.isPasswordCorrect(password)) return "incorrect password!";
        loggedInUser = user;
        return "user successfully logged in!";

    }

    public static String userList() {
        StringBuilder list = new StringBuilder();
        list.append(USER_ARRAY_LIST.size()).append(" users have registered!\n");
        for (int i = 0; i < USER_ARRAY_LIST.size(); i++) {
            list.append(i + 1).append(" - ").append(USER_ARRAY_LIST.get(i).getNickname()).append("\n");
        }
        return list.toString();
    }

    public static String showInventory() {
        return "Lollipop Hammer : " + loggedInUser.getLollipopHammerNumber() + "\n" +
                "Colour Bomb : " + loggedInUser.getColourBombNumber() + "\n" +
                "Extra Moves : " + loggedInUser.getExtraMovesNumber() + "\n" +
                "Free Switch : " + loggedInUser.getFreeSwitchNumber() + "\n" +
                "Striped Brush : " + loggedInUser.getStripedBrushNumber() + "\n" +
                "Wrapped Brush : " + loggedInUser.getWrappedBrushNumber();

    }

    public static String showScoreboard() {
        StringBuilder list = new StringBuilder();
        Comparator<User> comparator = Comparator.comparing(User::getNegativeHighScore)
                .thenComparing(User::getUsername);
        USER_ARRAY_LIST.sort(comparator);
        for (int i = 0; i < USER_ARRAY_LIST.size(); i++) {
            list.append(i + 1).append("- ").append(USER_ARRAY_LIST.get(i).getUsername())
                    .append(": ").append(USER_ARRAY_LIST.get(i).getHighScore()).append("\n");
        }
        return list.toString();
    }

    public static String showProfile() {
        return "username : " + loggedInUser.getUsername() + "\n"
                + "nickname : " + loggedInUser.getNickname() + "\n"
                + "money : " + loggedInUser.getBalance() + "\n"
                + "highscore : " + loggedInUser.getHighScore();
    }

    private static boolean isPasswordWeak(String password) {
        return !password.matches(".*\\d.*") || !password.matches(".*[a-z].*")
                || !password.matches(".*[A-Z].*") || !password.matches(".*[*.!@$%^&(){}\\[\\]:;<>,?/~_+\\-=|].*")
                || password.length() > 32 || password.length() < 8;
    }

    private static User getUserByUsername(String username) {
        for (User user : USER_ARRAY_LIST) {
            if (user != null && user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static String resetPassword(String oldPassword, String newPassword) {
        if (loggedInUser.isPasswordCorrect(oldPassword)) {
            if (isPasswordWeak(newPassword)) {
                return "password is weak!";
            }
            loggedInUser.setPassword(newPassword);
            Data.saveData();
            return "password changed!";

        }
        return "password is incorrect!";
    }

    public static String resetUsername(String oldUsername, String newUsername) {
        for (User u :
                USER_ARRAY_LIST) {
            if (u.getUsername().equals(newUsername)) return "username cannot change!";
        }
        if (loggedInUser.getUsernameItem() <= 0)
            return "not enough usernameItem";
        loggedInUser.setUsername(newUsername);
        loggedInUser.setUsernameItem(loggedInUser.getUsernameItem() - 1);
        Data.saveData();
        return "username changed!";
    }


    public static String removeAccount(String password) {
        if (loggedInUser.isPasswordCorrect(password)) {
            USER_ARRAY_LIST.remove(loggedInUser);
            USER_ARRAY_LIST.remove(loggedInUser);
            loggedInUser = null;
            Data.saveData();
            return "account deleted!";
        }
        return "password is incorrect!";
    }

    public static String buyItem(String item, int count) {
        switch (item) {
            case "free_switch":
                return buyItem(PowerUps.FREE_SWITCH, count);
            case "colour_bomb_brush":
                return buyItem(PowerUps.COLOUR_BOMB,count);
            case "striped_brush":
                return buyItem(PowerUps.STRIPED_BRUSH,count);

            case "lollipop_hammer":
                return buyItem(PowerUps.LOLLIPOP_HAMMER,count);
            case "wrapped_brush":
                return buyItem(PowerUps.WRAPPED_BRUSH,count);
            case "extra_moves":
                return buyItem(PowerUps.EXTRA_MOVE,count);
            case "username_item":
                return buyItem(new UsernameItem(),count);
            default:
                return "there is no product with this name";
        }
    }
    private static String buyItem(Purchasable item, int count){
        if (count < 1) return "invalid number";
        if (loggedInUser.getBalance() < count * item.getPrice()) return "not enough money!";
        item.purchase(count,loggedInUser);
        return item.getName() + " was bought!";
    }

    public static ArrayList<User> getUserArrayList() {
        return USER_ARRAY_LIST;
    }

}
