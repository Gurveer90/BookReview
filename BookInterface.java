package ui;

import exceptions.BookIdAlreadyAvailabeException;
import exceptions.BookNotFoundException;
import exceptions.FileNotFoundLocalException;

import java.io.IOException;

public interface BookInterface
{
    void addBook(Book book) throws IOException, BookIdAlreadyAvailabeException;
    void removeBook(String bookAuthor, String bookName) throws IOException, BookNotFoundException, FileNotFoundLocalException;
}
