package org.example.library.observers;

import org.example.library.models.User;
import org.example.library.services.EmailService1;

/**
 * Observer implementation that sends an email whenever it is notified.
 * <p>
 * This class is part of the Observer pattern used in the project.
 * When some event happens (for example: overdue book), the subject
 * can call {@link #notify(User, String)} to send an email to the user.
 */
public class EmailNotifier implements Observer {

    // Service responsible for actually sending the email
    private EmailService1 emailService = new EmailService1();

    /**
     * Sends an email to the given user with the provided message.
     *
     * @param user    the target user (must have a valid email)
     * @param message the body of the notification message
     */
    @Override
    public void notify(User user, String message) {
        emailService.sendEmail(
                user.getEmail(),
                "Overdue Reminder",
                message
        );
    }
}
