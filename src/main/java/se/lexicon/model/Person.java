package se.lexicon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member who can borrow and return {@link Book}s.
 * <p>
 * <strong>Design notes:</strong>
 * <ul>
 *   <li>{@code sequencer} is currently <em>instance</em>-scoped, so each Person starts counting from 0.
 *       That means IDs will collide across different Person instances. Consider making it {@code static}
 *       and assigning to the {@code id} field (see TODOs below).</li>
 *   <li>Borrow/return methods print messages to stdout; in real apps prefer returning booleans or throwing exceptions.</li>
 * </ul>
 */
public class Person {
    /**
     * Monotonic counter used to generate IDs.
     * <br>
     * NOTE: This is instance-scoped; every new Person starts at 0.
     * If you want globally unique IDs across all Person instances, make this {@code static}.
     */
    private int sequencer = 0;

    /** The Person's identifier (currently never assigned). */
    private int id;

    /** Given name; must be non-null. */
    private String firstName;

    /** Family name; must be non-null. */
    private String lastName;

    /** List of books currently borrowed by this person (never null). */
    private final List<Book> borrowedBooks;

    /** Platform line separator for multi-line string building. */
    private static final String LS = System.lineSeparator();

    /**
     * Creates a new {@code Person} and initializes an empty borrowed-books list.
     * <p>
     * Calls {@link #getNextId()} but does not assign the result to {@link #id}.
     * See TODO notes if you intend {@code id} to hold the generated identifier.
     *
     * @param firstName non-null first name
     * @param lastName  non-null last name
     * @throws IllegalArgumentException if either name is null
     */
    public Person(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
        getNextId();
        this.borrowedBooks = new ArrayList<>();
    }

    // SetId()/GetId()
    /**
     * Returns this person's identifier.
     * <p>
     * NOTE: Currently returns {@code sequencer} (the counter), not the {@code id} field.
     * If your intent is to expose the assigned id, return {@code id} instead.
     *
     * @return current value of {@code sequencer}
     */
    public int getId() {
        return sequencer;
    }

    /**
     * Increments and returns the next counter value for this instance.
     * <p>
     * NOTE: This does not update {@link #id}. If you want {@code id} to store
     * the generated value, assign it where appropriate.
     *
     * @return incremented sequencer value
     */
    public int getNextId() {
        //if(id<=0) throw new IllegalArgumentException("ID can not be 0 or a negative value");
        return ++sequencer;
    }

    /**
     * Returns the first name.
     *
     * @return non-null first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName non-null first name
     * @throws IllegalArgumentException if {@code firstName} is null
     *                                  (consider also rejecting blank strings)
     */
    public void setFirstName(String firstName) {
        if (firstName == null)
            throw new IllegalArgumentException(" First name can't be empty");
        this.firstName = firstName;
    }

    /**
     * Returns the last name.
     *
     * @return non-null last name
     */
    public String getLastName() { return lastName; }

    /**
     * Sets the last name.
     *
     * @param lastName non-null last name
     * @throws IllegalArgumentException if {@code lastName} is null
     *                                  (consider also rejecting blank strings)
     */
    public void setLastName(String lastName) {
        if (lastName == null)
            throw new IllegalArgumentException("Last name can't be empty");
        this.lastName = lastName;
    }

    /**
     * Attempts to loan the given book to this person.
     * <p>
     * Behavior:
     * <ul>
     *   <li>If the book is available, adds it to {@link #borrowedBooks} and sets the book's borrower to this person.</li>
     *   <li><strong>NOTE:</strong> Does <em>not</em> set {@code book.setAvailable(false)}; availability may become inconsistent.</li>
     *   <li>If the book is not available, prints a message to stdout.</li>
     * </ul>
     *
     * @param book the book to loan; if null, this will throw a {@link NullPointerException}
     *             when {@code book.isAvailable()} is evaluated (consider null-guarding)
     */
    public void loanBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.setBorrower(this);
        } else {
            System.out.println("Currently the book is not available or is loaned");
        }
    }

    /**
     * Returns the given book from this person's borrowed list.
     * <p>
     * Behavior:
     * <ul>
     *   <li>If the book was borrowed by this person, removes it, clears borrower, and marks it available.</li>
     *   <li>Otherwise, prints a message to stdout.</li>
     * </ul>
     *
     * @param book the book to return; if null, {@link List#remove(Object)} will safely no-op (prints message)
     */
    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.setBorrower(null);
            book.setAvailable(true);
        } else {
            System.out.println("This book is not borrowed by this person.");
        }
    }

    /**
     * Builds a human-readable description of this person and, if any,
     * their currently borrowed books (one per line).
     *
     * @return multi-line string with first name, last name, id, and any borrowed books
     */
    public String getPersonInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person information:").append(LS)
                .append("first name : ").append(getFirstName()).append(LS)
                .append("last name : ").append(getLastName()).append(LS)
                .append("id : ").append(getId()).append(LS);

        if (borrowedBooks.isEmpty()) {
            return sb.toString();
        }

        for (Book book : borrowedBooks) {
            sb.append(formatBorrowed(book)).append(LS);
        }
        return sb.toString();
    }


    private static String formatBorrowed(Book b) {
        return b.getId() + " " + b.getTitle();
    }
}




