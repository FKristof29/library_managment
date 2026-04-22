package library.ui;

import library.model.Book;
import library.model.Profile;
import library.service.LibraryService;

import java.util.*;

public class LibraryApp {

    private static final LibraryService service = new LibraryService();
    private static final Scanner        scanner  = new Scanner(System.in);
    private static Profile activeProfile = null;

    public static void main(String[] args) {
        System.out.println("       Konyvtar Kezelo           ");
        while (true) {
            if (activeProfile == null) showGuestMenu();
            else showUserMenu();
        }
    }


    private static void showGuestMenu() {
        System.out.println("\n Fomenu ");
        System.out.println("1. Profil letrehozasa");
        System.out.println("2. Bejelentkezes (profil ID)");
        System.out.println("3. Konyvek bongeszeese");
        System.out.println("0. Kilepes");
        System.out.print("Valasztas: ");
        switch (readLine()) {
            case "1" -> createProfile();
            case "2" -> loginProfile();
            case "3" -> browseBooks();
            case "0" -> { System.out.println("Viszlat!"); System.exit(0); }
            default  -> System.out.println("Ervenytelen valasztas.");
        }
    }

    private static void showUserMenu() {
        System.out.println("\n Menu [" + activeProfile.getName() + "] ");
        System.out.println("1. Konyvek bongeszeese");
        System.out.println("2. Konyv kolcsonzese (ISBN)");
        System.out.println("3. Konyv visszahozasa");
        System.out.println("4. Kolcsonzott konyveim");
        System.out.println("5. Kijelentkezes");
        System.out.println("0. Kilepes");
        System.out.print("Valasztas: ");
        switch (readLine()) {
            case "1" -> browseBooks();
            case "2" -> borrowBook();
            case "3" -> returnBook();
            case "4" -> showBorrowed();
            case "5" -> { activeProfile = null; System.out.println("Kijelentkezve."); }
            case "0" -> { System.out.println("Viszlat!"); System.exit(0); }
            default  -> System.out.println("Ervenytelen valasztas.");
        }
    }


    private static void createProfile() {
        System.out.print("Neved: ");
        String name = readLine();
        System.out.print("Email: ");
        String email = readLine();
        Profile p = service.createProfile(name, email);
        System.out.println("Profil letrehozva! Azonositod: " + p.getId());
        activeProfile = p;
    }

    private static void loginProfile() {
        System.out.print("Profil ID (pl. USR1): ");
        String id = readLine();
        service.findProfile(id).ifPresentOrElse(
                p -> { activeProfile = p; System.out.println("Udv, " + p.getName() + "!"); },
                ()  -> System.out.println("Nem talalhato ilyen profil.")
        );
    }


    private static void browseBooks() {
        System.out.println("\n Konyvek ");
        System.out.println("1. Osszes konyv (ABC sorrend)");
        System.out.println("2. Szures tema alapjan");
        System.out.println("3. Kereses");
        System.out.print("Valasztas: ");
        switch (readLine()) {
            case "1" -> printBooks(service.getAllBooksSorted());
            case "2" -> filterByTopic();
            case "3" -> searchBooks();
            default  -> System.out.println("Ervenytelen valasztas.");
        }
    }

    private static void filterByTopic() {
        List<String> topics = service.getAllTopics();
        System.out.println("\nElerheto temak:");
        for (int i = 0; i < topics.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, topics.get(i));
        }
        System.out.print("Valassz szamot: ");
        try {
            int idx = Integer.parseInt(readLine()) - 1;
            if (idx >= 0 && idx < topics.size()) printBooks(service.getBooksByTopic(topics.get(idx)));
            else System.out.println("Ervenytelen szam.");
        } catch (NumberFormatException e) {
            System.out.println("Ervenytelen bemenet.");
        }
    }

    private static void searchBooks() {
        System.out.print("Keresesi kifejezes: ");
        List<Book> results = service.searchBooks(readLine());
        if (results.isEmpty()) System.out.println("Nincs talalat.");
        else printBooks(results);
    }

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) { System.out.println("Nincs konyv ebben a kategoriban."); return; }
        System.out.println();
        books.forEach(b -> System.out.println("  " + b));
    }


    private static void borrowBook() {
        System.out.print("Konyv ISBN szama: ");
        System.out.println(service.borrowBook(activeProfile.getId(), readLine()));
    }

    private static void returnBook() {
        List<String> isbns = activeProfile.getBorrowedIsbns();
        if (isbns.isEmpty()) { System.out.println("Nincs nalad kolcsonzott konyv."); return; }
        showBorrowed();
        System.out.print("Visszahozando konyv ISBN: ");
        System.out.println(service.returnBook(activeProfile.getId(), readLine()));
    }

    private static void showBorrowed() {
        List<String> isbns = activeProfile.getBorrowedIsbns();
        if (isbns.isEmpty()) { System.out.println("Nincs kolcsonzott konyved."); return; }
        System.out.println("\nKolcsonzott konyveid:");
        isbns.forEach(isbn -> service.findBook(isbn).ifPresent(
                b -> System.out.println("  [" + b.getIsbn() + "] " + b.getTitle())
        ));
    }


    private static String readLine() {
        return scanner.nextLine().trim();
    }
}
