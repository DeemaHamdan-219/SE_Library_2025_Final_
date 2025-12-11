package org.example.library.observers;

import org.example.library.models.User;
/**
 * Observer interface used in the Observer Pattern.
 *
 * Any class implementing this interface can subscribe to notifications,
 * such as overdue alerts or system messages sent to users.
 *
 * When the system detects an event (e.g., overdue book),
 * it calls {@code notify()} to deliver the message to the observer.
 *
 * @author Dima & Asmaa
 * @version 1.0
 */

public interface Observer {
    void notify(User user, String message);
}
