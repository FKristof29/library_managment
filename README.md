# Library Manager

A library management system you run from the command line. Manage your profile, borrow and return books, search and browse. Built with Java and good OOP practices.

## What You Can Do

- **Create a profile** – Set up your account and log in with your ID
- **Borrow books** – Check out any book that's available (just need the ISBN)
- **Return books** – Give back books you've borrowed
- **See all books** – Full list sorted alphabetically
- **Browse by topic** – Filter books by category
- **Search** – Find books by title, author, or description

## How It's Organized

```
src/main/java/library/
├── model/
│   ├── Book.java            # The Book class
│   └── Profile.java         # The Profile/User class
├── service/
│   └── LibraryService.java  # All the business logic
└── ui/
    └── LibraryApp.java      # The console interface
```

## Getting Started

### What You Need
- Java 11+

### Build It
```bash
mkdir -p out
find src -name "*.java" | xargs javac -d out
```

### Run It
```bash
java -cp out library.ui.LibraryApp
```

Then just follow the prompts in the console.

## Built With

- Java 17
- OOP with clean separation (model / service / UI)
- Java Streams for filtering and sorting
- Collections: Map, List, Optional
