package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * this class saves and loads all data that should be saved whenever the app opens or closes
 */
public class Data {
    public static ArrayList<User> loadUsers(){
        ArrayList<User> USER_ARRAY_LIST = new ArrayList<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get("users.json")));
            USER_ARRAY_LIST = new Gson().fromJson(json, new TypeToken<List<User>>() {
            }.getType());
        } catch (Exception e) {
            File file = new File("users.json");
            try {
                file.createNewFile();
                USER_ARRAY_LIST = new ArrayList<>();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return USER_ARRAY_LIST;
    }
    public static void saveData() {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("users.json");
            fileWriter.write(new Gson().toJson(UserController.getUserArrayList()));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
