package se.lexicon.model;

/**
 * Represents a book in a simple library domain, including basic metadata
 * (title, author), an auto-generated identifier, and optional loan info
 * (availability and current borrower).
 */
public class Book {

    /** Monotonic counter used to generate unique book identifiers. */
    private static int sequencer = 0;

    /** Unique identifier for the book, e.g., {@code book-0}, {@code book-1}, ... */
    private  String id;

    private String title;
    private String author;

    //loan info
    private boolean available;
    private Person borrower;


    //Platform-specific line separator used when building multi-line strings.
    private static final String LS = System.lineSeparator();

    /**
     * Creates an available Book with a generated id and no borrower.
     *
     * @param title  non-null book title
     * @param author non-null book author
     * @throws IllegalArgumentException if {@code title} or {@code author} is null
     */
    public Book( String title, String author){
        this.id = "book" + "-" + sequencer++;
        setTitle(title);
        setAuthor(author);
        setAvailable(true);
        setBorrower(null);
    }

    public Book (String title, String author, Person borrower ){
        setTitle(title);
        setAuthor(author);
        this.borrower = borrower;
        setAvailable(false);
    }

    /**
     * Returns the book title.
     *
     * @return non-null title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the book title.
     *
     * @param title non-null title
     * @throws IllegalArgumentException if {@code title} is null
     */
    public void setTitle(String title) {
        if(title == null)
            throw new IllegalArgumentException("Title of a book can not be null");
        this.title = title;
    }

    /**
     * Returns the unique identifier of this book.
     *
     * @return non-null id (e.g., {@code book-3})
     */
    public String getId() {return id;
    }

    /**
     * Returns the book author.
     *
     * @return non-null author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the book author.
     *
     * @param author non-null author
     * @throws IllegalArgumentException if {@code author} is null
     */
    public void setAuthor(String author) {
        if(author == null)
            throw new IllegalArgumentException("A book must have an author");
        this.author = author;
    }

    /**
     * Indicates whether the book is currently available (i.e., not on loan).
     *
     * @return {@code true} if available; {@code false} otherwise
     */
    public boolean isAvailable() {return available;}

    /**
     * Updates the availability flag.
     * <p>
     * Note: This method does not automatically synchronize with {@link #setBorrower(Person)}.
     * Callers are responsible for keeping {@code available} and {@code borrower} consistent.
     *
     * @param available {@code true} to mark available; {@code false} to mark not available
     */
    public void setAvailable(boolean available) { this.available = available; }

    /**
     * Assigns or clears the borrower of this book.
     * <p>
     * Note: This method does not change {@link #available}. If you want borrow/return
     * semantics, update availability accordingly in the calling code.
     *
     * @param borrower the borrowing {@code Person}, or {@code null} to clear
     */
    public void setBorrower(Person borrower){
        this.borrower = borrower;
    }

    /**
     * Builds a human-readable, multi-line string containing the book's metadata
     * (title and author), and, if present, the borrower's information.
     * <p>
     * Lines are separated using the platform line separator ({@link #LS}).
     *
     * @return formatted book information; if {@code borrower} is non-null,
     *         the borrower's {@code getPersonInformation()} is appended
     */
    public String getBookInformation(){
        String bookInfo;
        bookInfo =  "Book information:" + LS +
                "title : " + getTitle() + LS +
                "author : " + getAuthor() + LS;

        if (borrower == null)
            return bookInfo;
        else
            return bookInfo + "\n" +  borrower.getPersonInformation();
    }
}