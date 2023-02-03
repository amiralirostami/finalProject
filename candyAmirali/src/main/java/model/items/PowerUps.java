package model.items;

import model.User;

public enum PowerUps implements Purchasable {
    COLOUR_BOMB(20,"COLOUR_BOMB",0),
    EXTRA_MOVE(10,"EXTRA_MOVE",1),
    FREE_SWITCH(13,"FREE_SWITCH",2),
    LOLLIPOP_HAMMER(6,"LOLLIPOP_HAMMER",3),
    STRIPED_BRUSH(15,"STRIPED_BRUSH",4),
    WRAPPED_BRUSH(15,"WRAPPED_BRUSH",5);

    final int price;
    final int number;
    final String name;

    PowerUps(int price, String name, int number) {
        this.price = price;
        this.name = name;
        this.number = number;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void purchase(int count, User user) {
        user.getItems()[number] += count;
        user.increaseBalance(-count*price);
    }

    @Override
    public String getName() {
        return name;
    }
}
