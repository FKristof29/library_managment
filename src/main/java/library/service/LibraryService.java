package library.service;

import library.model.Book;
import library.model.Profile;

import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {

    private final Map<String, Book>    books    = new LinkedHashMap<>();
    private final Map<String, Profile> profiles = new LinkedHashMap<>();

    public LibraryService() { seedBooks(); }


    public Profile createProfile(String name, String email) {
        String id = "USR" + (profiles.size() + 1);
        Profile p = new Profile(id, name, email);
        profiles.put(id, p);
        return p;
    }

    public Optional<Profile> findProfile(String id) {
        return Optional.ofNullable(profiles.get(id));
    }

    public Collection<Profile> getAllProfiles() { return profiles.values(); }


    public void addBook(String isbn, String title, String author, String topic, String description) {
        books.put(isbn, new Book(isbn, title, author, topic, description));
    }

    public Optional<Book> findBook(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    public List<Book> getAllBooksSorted() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByTopic(String topic) {
        return books.values().stream()
                .filter(b -> b.getTopic().equalsIgnoreCase(topic))
                .sorted(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public List<Book> searchBooks(String query) {
        String q = query.toLowerCase();
        return books.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q)
                          || b.getDescription().toLowerCase().contains(q)
                          || b.getAuthor().toLowerCase().contains(q))
                .sorted(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public List<String> getAllTopics() {
        return books.values().stream()
                .map(Book::getTopic).distinct().sorted()
                .collect(Collectors.toList());
    }


    public String borrowBook(String profileId, String isbn) {
        Profile profile = profiles.get(profileId);
        if (profile == null)     return "Profil nem talalhato.";
        Book book = books.get(isbn);
        if (book == null)        return "Konyv nem talalhato.";
        if (!book.isAvailable()) return "A konyv jelenleg nincs elerheto.";
        book.setAvailable(false);
        profile.borrowBook(isbn);
        return "Sikeres kolcsonzes: \"" + book.getTitle() + "\" -> " + profile.getName();
    }

    public String returnBook(String profileId, String isbn) {
        Profile profile = profiles.get(profileId);
        if (profile == null)            return "Profil nem talalhato.";
        if (!profile.hasBorrowed(isbn)) return "Ez a konyv nincs nalad kolcsonozve.";
        Book book = books.get(isbn);
        if (book != null) book.setAvailable(true);
        profile.returnBook(isbn);
        return "Sikeres visszahozas: \"" + (book != null ? book.getTitle() : isbn) + "\"";
    }


    private void seedBooks() {
        addBook("978-0-13-468599-1", "Clean Code",                "Robert C. Martin",  "Programozas",
                "Utmutato az olvasható, karbantarthato kod irasahoz.");
        addBook("978-0-13-110362-7", "The C Programming Language", "Brian W. Kernighan","Programozas",
                "A C nyelv klasszikus referenciaja, alapveto olvasmany fejlesztoknek.");
        addBook("978-0-13-235088-4", "The Pragmatic Programmer",   "David Thomas",      "Programozas",
                "Gyakorlati tanácsok szoftverfejlesztoknek a karrierjuk minden szakaszaban.");
        addBook("978-0-59-651798-1", "JavaScript: The Good Parts", "Douglas Crockford", "Web",
                "A JavaScript legjobb reszeit mutatja be tomoren es erthetoen.");
        addBook("978-1-49-195016-0", "Learning Python",            "Mark Lutz",         "Programozas",
                "Atfogo bevezetes a Python programozasi nyelvbe kezdoknek es haladoknak.");
        addBook("978-0-62-107286-4", "Godel, Escher, Bach",        "Douglas Hofstadter","Matematika",
                "Elmelekes az onreferenciárol, rekurziorol es a tudatrol.");
        addBook("978-1-59-327584-6", "The Linux Command Line",     "William Shotts",    "Rendszerek",
                "Teljes utmutato a Linux parancssor hatekony hasznalatahoz.");
        addBook("978-0-13-597871-2", "Design Patterns",            "Gang of Four",      "Programozas",
                "A szoftvertervezesi mintak alapkonyve, 23 klasszikus mintaval.");
        addBook("978-1-49-195216-4", "Flask Web Development",      "Miguel Grinberg",   "Web",
                "Python Flask keretrendszer bemutatasa valos webalkalmazasok fejleszteseshez.");
        addBook("978-0-13-468601-1", "Refactoring",                "Martin Fowler",     "Programozas",
                "Meglevo kod szerkezetenek javitasa a viselkedes megorzese mellett.");
    }
}
