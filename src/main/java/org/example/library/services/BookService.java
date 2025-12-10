package org.example.library.services;

import org.example.library.models.Book;
import org.example.library.storage.FileDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.example.library.models.User;
import org.example.library.strategies.FineStrategy;
import org.example.library.strategies.BookFineStrategy;
import org.example.library.observers.Observer;


public class BookService {




    public JSONObject findBookByISBN(String isbn) {
        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");

        for (int i = 0; i < books.length(); i++) {
            JSONObject b = books.getJSONObject(i);
            if (b.getString("isbn").equals(isbn)) {
                return b;
            }
        }
        return null;
    }

    // ================= OBSERVER PATTERN ==================

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(User user, String message) {
        for (Observer obs : observers) {
            obs.notify(user, message);
        }
    }

    public void addBook(Book book) {
        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");

        JSONObject b = new JSONObject();
        b.put("title", book.getTitle());
        b.put("author", book.getAuthor());
        b.put("isbn", book.getIsbn());
        b.put("quantity", book.getQuantity());
        b.put("available", book.isAvailable());

        books.put(b);
        FileDatabase.save(db);
    }

    // ===== Search Functions =====

    public List<Book> searchByISBN(String isbn) {
        List<Book> results = new ArrayList<>();
        JSONObject b = findBookByISBN(isbn);
        if (b != null) {
            results.add(new Book(
                    b.getString("title"),
                    b.getString("author"),
                    b.getString("isbn"),
                    b.getInt("quantity"),
                    b.getBoolean("available")
            ));
        }
        return results;
    }

    public List<Book> searchByTitle(String title) {
        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");
        List<Book> results = new ArrayList<>();

        for (int i = 0; i < books.length(); i++) {
            JSONObject b = books.getJSONObject(i);
            if (b.getString("title").toLowerCase().contains(title.toLowerCase())) {
                results.add(new Book(
                        b.getString("title"),
                        b.getString("author"),
                        b.getString("isbn"),
                        b.getInt("quantity"),
                        b.getBoolean("available")
                ));
            }
        }
        return results;
    }

    public List<Book> searchByAuthor(String author) {
        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");
        List<Book> results = new ArrayList<>();

        for (int i = 0; i < books.length(); i++) {
            JSONObject b = books.getJSONObject(i);
            if (b.getString("author").toLowerCase().contains(author.toLowerCase())) {
                results.add(new Book(
                        b.getString("title"),
                        b.getString("author"),
                        b.getString("isbn"),
                        b.getInt("quantity"),
                        b.getBoolean("available")
                ));
            }
        }
        return results;
    }

    public List<Book> getAllBooks() {
        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");
        List<Book> results = new ArrayList<>();

        for (int i = 0; i < books.length(); i++) {
            JSONObject b = books.getJSONObject(i);
            results.add(new Book(
                    b.getString("title"),
                    b.getString("author"),
                    b.getString("isbn"),
                    b.getInt("quantity"),
                    b.getBoolean("available")
            ));
        }
        return results;
    }

    public void checkOverdueBooks() {
        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");

        LocalDate today = LocalDate.now();

        // Create strategy once (for books only)
        FineStrategy strategy = new BookFineStrategy();

        for (int i = 0; i < books.length(); i++) {
            JSONObject b = books.getJSONObject(i);

            if (b.optBoolean("borrowed", false)) {
                String due = b.optString("dueDate", null);

                if (due != null && !due.equals("null")) {
                    LocalDate dueDate = LocalDate.parse(due);
                    long daysLate = ChronoUnit.DAYS.between(dueDate, today);

                    if (daysLate > 0) {

                        // ⭐ Use STRATEGY to calculate fine
                        double fine = strategy.calculateFine((int) daysLate);
                        b.put("fine", fine);
                    }
                }
            }
        }

        FileDatabase.save(db);
        System.out.println("✔ Book overdue detection complete (Strategy Pattern applied).");
    }



    // ===================================================
//               BORROW BOOK (Sprint 3)
// ===================================================
    public void borrowBook(String isbn, User currentUser) {

        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");

        for (int i = 0; i < books.length(); i++) {
            JSONObject b = books.getJSONObject(i);

            if (b.getString("isbn").equals(isbn)) {

                if (!b.getBoolean("available")) {
                    System.out.println("❌ Book not available.");
                    return;
                }

                if (b.getBoolean("borrowed")) {
                    System.out.println("❌ Book already borrowed.");
                    return;
                }

                // reduce quantity
                int qty = b.getInt("quantity");
                b.put("quantity", qty - 1);

                if (qty - 1 == 0)
                    b.put("available", false);

                b.put("borrowed", true);
                b.put("borrowedBy", currentUser.getId());
                b.put("dueDate", LocalDate.now().plusDays(14).toString()); // 14 days
                b.put("fine", 0);

                FileDatabase.save(db);

                System.out.println("📘 Book borrowed! Due date: " + b.getString("dueDate"));
                return;
            }
        }

        System.out.println("❌ Book not found.");
    }


    // ===================================================
//                   PAY FINE
// ===================================================
    public void payFine(String isbn, double amount) {

        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");

        for (int i = 0; i < books.length(); i++) {
            JSONObject b = books.getJSONObject(i);

            if (b.getString("isbn").equals(isbn)) {

                double fine = b.optDouble("fine", 0);

                if (fine == 0) {
                    System.out.println("✔ No fines for this book.");
                    return;
                }

                if (amount >= fine) {
                    b.put("fine", 0);
                    System.out.println("✔ Fine cleared!");
                } else {
                    b.put("fine", fine - amount);
                    System.out.println("Remaining fine: " + (fine - amount));
                }

                FileDatabase.save(db);
                return;
            }
        }

        System.out.println("❌ Book not found.");
    }


    // ===================================================
//            GET TOTAL FINE FOR A USER
// ===================================================
    public double getTotalBookFineForUser(int userId) {

        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");

        double total = 0;

        for (int i = 0; i < books.length(); i++) {
            JSONObject b = books.getJSONObject(i);

            if (b.optInt("borrowedBy", -1) == userId) {
                total += b.optDouble("fine", 0);
            }
        }

        return total;
    }


    public List<JSONObject> getOverdueBooksForUser(int userId) {

        JSONObject db = FileDatabase.load();
        JSONArray books = db.getJSONArray("books");

        List<JSONObject> overdue = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < books.length(); i++) {
            JSONObject b = books.getJSONObject(i);

            if (b.optInt("borrowedBy", -1) == userId &&
                    b.optBoolean("borrowed", false) &&
                    b.has("dueDate")) {


                String raw = b.getString("dueDate");

                // إصلاح صيغة التاريخ تلقائيًا
                if (raw.matches("\\d{4}-\\d-\\d{2}")) raw = raw.replaceFirst("-(\\d)-", "-0$1-");
                if (raw.matches("\\d{4}-\\d{2}-\\d")) raw = raw.replaceFirst("-(\\d)$", "-0$1");
                if (raw.matches("\\d{4}-\\d-\\d"))    raw = raw.replaceFirst("-(\\d)-", "-0$1-").replaceFirst("-(\\d)$", "-0$1");

                LocalDate due = LocalDate.parse(raw);


                if (due.isBefore(today)) {
                    overdue.add(b);
                }
            }
        }

        return overdue;
    }


    public void sendReminder(User user) {

        List<JSONObject> overdue = getOverdueBooksForUser(user.getId());
        int n = overdue.size();

        if (n == 0) {
            System.out.println("✔ No overdue books for: " + user.getUsername());
            return;
        }

        String msg = "You have " + n + " overdue book(s).";

        // 🔥 الآن BookService لا يرسل إيميل مباشرة
        // ❗ فقط ينادي notifyObservers
        notifyObservers(user, msg);

        System.out.println("📧 Reminder notification triggered for " + user.getUsername());
    }




    public int sendRemindersToAllUsers(List<User> users, EmailService1 emailService) {

        int totalSent = 0;

        for (User user : users) {

            List<JSONObject> overdue = getOverdueBooksForUser(user.getId());

            if (!overdue.isEmpty()) {

                String message = "You have " + overdue.size() + " overdue book(s).";

                // ⭐ send real email
                emailService.sendEmail(
                        user.getEmail(),         // <– الإيميل الحقيقي للمستخدم
                        "Overdue Reminder",
                        message
                );

                totalSent++;
            }
        }

        return totalSent;
    }




}
