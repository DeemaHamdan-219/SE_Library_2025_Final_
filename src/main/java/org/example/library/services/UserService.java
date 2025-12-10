package org.example.library.services;

import org.example.library.models.User;
import org.example.library.storage.FileDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<User> getAllUsers() {

        JSONObject db = FileDatabase.load();
        JSONArray arr = db.getJSONArray("users");

        List<User> users = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject u = arr.getJSONObject(i);

            users.add(new User(
                    u.getInt("id"),
                    u.getString("username"),
                    u.getString("password"),
                    u.getString("role"),
                    u.getString("email")   // ⭐ NEW
            ));
        }

        return users;
    }
}
