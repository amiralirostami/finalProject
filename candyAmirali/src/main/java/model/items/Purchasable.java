package model.items;

import model.User;

/**
 * this interface and its child implement items that User can buy in shop
 */
public interface Purchasable {
     int getPrice();
     void purchase(int count, User user);
     String getName();

}
