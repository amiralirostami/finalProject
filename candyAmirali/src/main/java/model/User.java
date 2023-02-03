package model;

import controller.Data;

import java.util.ArrayList;

/**
 * as you see in this class all attributes and basic methods for a players are implemented
 */
public class User {
    private  String username;
    private String password;
    private final String nickname;
    private int highScore;
    private int balance;
    private int items[];
    private int usernameItem=0;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.highScore = 0;
        this.balance = 100;
        this.items = new int[6];
    }

    public int getHighScore() {
        return highScore;
    }

    public int getNegativeHighScore() {
        return -highScore;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public int getExtraMovesNumber() {
        return items[1];
    }

    public int getFreeSwitchNumber() {
        return items[2];
    }

    public int getLollipopHammerNumber() {
        return items[3];
    }

    public int getWrappedBrushNumber() {
        return items[5];
    }

    public int getStripedBrushNumber() {
        return items[4];
    }

    public int getColourBombNumber() {
        return items[0];
    }

    public int getBalance() {
        return balance;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public int getUsernameItem() {
        return usernameItem;
    }

    public void setUsernameItem(int usernameItem) {
        this.usernameItem = usernameItem;
    }

    public void increaseBalance(int amount) {
        balance = balance + amount;
        Data.saveData();

    }




    public boolean isPasswordCorrect(String password) {
        return password.equals(this.password);

    }

    public void setPassword(String password) {
        this.password = password;
        Data.saveData();
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        Data.saveData();
    }

    public int[] getItems() {
        return items;
    }
}
