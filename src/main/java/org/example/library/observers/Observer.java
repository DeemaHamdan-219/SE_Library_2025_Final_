package org.example.library.observers;

import org.example.library.models.User;

public interface Observer {
    void notify(User user, String message);
}
