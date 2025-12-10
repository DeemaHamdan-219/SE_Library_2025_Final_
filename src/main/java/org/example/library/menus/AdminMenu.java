package org.example.library.menus;

import org.example.library.models.User;
import org.example.library.services.AdminService;
import org.example.library.utils.Input;
import org.example.library.services.EmailService1;
import org.example.library.services.BookService;
import org.example.library.services.UserService;

public class AdminMenu extends LibrarianMenu {

    private static AdminService adminService = new AdminService();


    public static void show(User currentUser) {

        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1) Add Librarian");
            System.out.println("2) Add Book");
            System.out.println("3) Search Book");
            System.out.println("4) Show All Books");
            System.out.println("5) Unregister User");
            System.out.println("6) Send Reminders");
            System.out.println("7) View Email Log");
            System.out.println("8) Logout");

            int choice = Input.number("Choose: ");

            switch (choice) {

                case 1 -> addLibrarian();
                case 2 -> addBook(currentUser);
                case 3 -> searchBook();
                case 4 -> showAllBooks();
                case 5 -> unregisterUser(currentUser);
                case 6 -> sendReminders();
                case 7 -> showEmailLog();
                case 8 -> { return; }

                default -> System.out.println("Invalid option!");
            }
        }
    }

    // =====================================================
    //                ADD LIBRARIAN (admin only)
    // =====================================================
    private static void addLibrarian() {
        String username = Input.text("Enter librarian username: ");
        String password = Input.text("Enter password: ");

        System.out.println("✔ Librarian added successfully!");
    }

    // =====================================================
    //             UNREGISTER USER
    // =====================================================
    private static void unregisterUser(User currentUser) {
        String username = Input.text("Enter username to unregister: ");

        boolean result = adminService.unregisterUser(username, currentUser);

        if (!result) {
            System.out.println("❌ Failed to unregister user.");
        }
    }

    // =====================================================
    //            SEND REMINDERS  (SPRINT 3)
    // =====================================================
    private static void sendReminders() {

        EmailService1 email = new EmailService1();
        BookService bookService = new BookService();
        UserService userService = new UserService();

        var users = userService.getAllUsers();

        int totalSent = bookService.sendRemindersToAllUsers(users, email);

        System.out.println("✔ Total reminders sent: " + totalSent);
    }

    // =====================================================
    //            VIEW EMAIL LOG  (SPRINT 3)
    // =====================================================
    private static void showEmailLog() {

        EmailService1 email = new EmailService1();
        var log = email.getEmailLog();   // <- هذه تعمل الآن

        System.out.println("\n===== Email Log =====");

        if (log == null || log.isEmpty()) {
            System.out.println("No emails sent yet.");
            return;
        }

        for (String msg : log) {
            System.out.println("- " + msg);
        }
    }
}
