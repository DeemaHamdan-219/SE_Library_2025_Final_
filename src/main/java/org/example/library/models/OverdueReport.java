package org.example.library.models;

import org.json.JSONObject;
import java.util.List;

public class OverdueReport {

    private final User user;
    private final List<JSONObject> overdueItems;

    public OverdueReport(User user, List<JSONObject> overdueItems) {
        this.user = user;
        this.overdueItems = overdueItems;
    }

    public User getUser() { return user; }

    public int getCount() { return overdueItems.size(); }

    public List<JSONObject> getItems() { return overdueItems; }

    @Override
    public String toString() {
        return "OverdueReport{ user=" + user.getUsername() +
                ", overdueCount=" + overdueItems.size() + " }";
    }
}
