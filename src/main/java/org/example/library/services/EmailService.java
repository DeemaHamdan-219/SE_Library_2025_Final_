package org.example.library.services;

import java.util.ArrayList;
import java.util.List;

public class EmailService {

    private static final List<String> emailLog = new ArrayList<>();

    /** Mock email → store message instead of sending */
    public void sendEmail(String to, String subject, String message) {
        String entry = "TO: " + to + " | SUBJECT: " + subject + " | MESSAGE: " + message;
        emailLog.add(entry);

        System.out.println("📧 Mock email sent → " + entry);
    }

    /** Return all logged emails */
    public List<String> getEmailLog() {
        return emailLog;
    }
}
