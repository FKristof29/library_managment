package library.model;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private final String id;
    private final String name;
    private final String email;
    private final List<String> borrowedIsbns = new ArrayList<>();

    public Profile(String id, String name, String email) {
        this.id    = id;
        this.name  = name;
        this.email = email;
    }

    public String getId()    { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }
    public List<String> getBorrowedIsbns() { return borrowedIsbns; }

    public void borrowBook(String isbn) { borrowedIsbns.add(isbn); }
    public void returnBook(String isbn) { borrowedIsbns.remove(isbn); }
    public boolean hasBorrowed(String isbn) { return borrowedIsbns.contains(isbn); }

    @Override
    public String toString() {
        return String.format("Profil: %s (%s) | Kolcsonzott konyvek: %d",
                name, email, borrowedIsbns.size());
    }
}
