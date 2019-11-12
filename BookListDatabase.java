package ui;

import exceptions.BookIdAlreadyAvailabeException;
import exceptions.BookNotFoundException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.*;

public class BookListDatabase extends BookSearch implements BookInterface, InvalidationListener {

    private  FileHandler fileHandler;
    private long numOfLines = 0;


    public BookListDatabase(FileHandler fileHandler)
    {
        this.fileHandler = fileHandler;
    }

    public void refreshList()  {
        List<String> lines = fileHandler.readlAllLines();
        for (String line : lines) {
            numOfLines++;
            ArrayList<String> partsOfLine = splitOnSpace(line);
            Book book = new Book(partsOfLine.get(1), partsOfLine.get(2));

            if (!book_list.containsKey(book.getBookID()))
                book_list.put(book.getBookID(), book);

        }
    }

    @Override
    public void addBook(Book book) throws BookIdAlreadyAvailabeException {

        String line = book.getBookID() + "--" + book.getBookName() + "--" +
                book.getBookAuthor() + "--" +  String.join(",", book.getList());

        List<String> lines = fileHandler.readlAllLines();
        for (String lineR : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(lineR);
            if (Integer.parseInt(partsOfLine.get(0)) == book.getBookID()) {
                throw new BookIdAlreadyAvailabeException("Book with given Id is already exists.");
            }
        }

        if (!book_list.containsKey((book.getBookAuthor() + book.getBookName()).hashCode()))
            book_list.put((book.getBookAuthor() + book.getBookName()).hashCode(), book);

        fileHandler.writeToFile(line);

    }

    public long getBookCount() {
        return fileHandler.getFileLineCount();
    }

    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split("--");
        return new ArrayList<>(Arrays.asList(splits));
    }

    @Override
    public void removeBook(String bookAuthor, String bookName) throws
            BookNotFoundException {

        int bid = (bookAuthor + bookName).hashCode();

        List<String> linesCopy = new ArrayList<>();

        List<String> lines = fileHandler.readlAllLines();
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            if (Integer.parseInt(partsOfLine.get(0)) != bid) {
                linesCopy.add(line);
            }
        }

        if (linesCopy.size() == lines.size()) {
            throw new BookNotFoundException("Book Not Found");
        }

        fileHandler.removeFileContent();

        for (String line : linesCopy) {
            fileHandler.writeToFile(line);
        }

        numOfLines = getBookCount();

        refreshList();
    }

    @Override
    public void invalidated(Observable observable) {
        System.out.println("From Database ");
        ReadWebPageEx ex = new ReadWebPageEx();
        ex.printData();

    }
}
