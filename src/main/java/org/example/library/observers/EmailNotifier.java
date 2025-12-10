package org.example.library.observers;

import org.example.library.models.User;
import org.example.library.services.EmailService1;

public class EmailNotifier implements Observer {

    private EmailService1 emailService;



    @Override
    public void notify(User user, String message) {
        emailService.sendEmail(
                user.getEmail(),
                "Overdue Reminder",
                message
        );
    }
}
