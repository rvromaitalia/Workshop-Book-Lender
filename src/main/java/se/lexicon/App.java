package se.lexicon;


import se.lexicon.model.Book;
import se.lexicon.model.Person;

public class App {
    public static void main(String[] args) {
        // Create a book instance
        Book book = new Book("Java programming for dummies", "Vivek Chandra");

        // Display book information
        System.out.println("Display book information after creating a book");
        System.out.println(book.getBookInformation());

        // Create a person instance
        Person person = new Person("Roman", "Vanoyan");
        // Display person information
        System.out.println("\nDisplay person information after creating a person");
        System.out.println(person.getPersonInformation());

        // Loan a book to the person
        person.loanBook(book);

        // Display person information after borrowing a book
        System.out.println("\nDisplay person information after borrowing a book");
        System.out.println(person.getPersonInformation());

        // Display book information after borrowing a book
        System.out.println("\nDisplay book information after borrowing a book");
        System.out.println(book.getBookInformation());

        // Return the borrowed book
        System.out.println("\nReturn the borrowed book");
        person.returnBook(book);

        // Display person information after returning the book
        System.out.println("Display person information after returning the book");
        System.out.println(person.getPersonInformation());

        // Display book information after borrowing a book
        System.out.println("\nDisplay book information after borrowing a book");
        System.out.println(book.getBookInformation());
    }
}
