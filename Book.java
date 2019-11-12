package ui;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.List;

public class Book implements Observable, BookComposite{
    private String book_name;
    private String book_author;
    List<InvalidationListener> in = new ArrayList<>();
    List<BookComposite> bc = new ArrayList<>();

    public Book(String book_name, String book_author) {
        this.book_name = book_name;
        this.book_author = book_author;
    }

    public String getBookName() {
        return book_name;
    }

    public String getBookAuthor() {
        return book_author;
    }

    public int getBookID() {
        return (book_author + book_name).hashCode();
    }

    public void addBookComposite(BookComposite b) {

        for(InvalidationListener l: in)
        {
            l.invalidated(this);
        }


        bc.add(b);
    }

    public List<String> getList() {

        List<String> list = new ArrayList<>();

        for (BookComposite b : bc) {
            for (String s : b.getList()) {
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        if(!in.contains(listener))
        {
            in.add(listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        in.remove(listener);
    }
}
