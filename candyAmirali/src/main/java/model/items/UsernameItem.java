package model.items;

import model.User;

public class UsernameItem implements Purchasable{
    @Override
    public int getPrice() {
        return 100;
    }

    @Override
    public void purchase(int count, User user) {
        user.setUsernameItem(count+ user.getUsernameItem());
        user.increaseBalance(-count*100);
    }

    @Override
    public String getName() {
        return "USERNAME_ITEM";
    }
}
