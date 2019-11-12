package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BookSearch {

    protected HashMap<Integer, Book> book_list;

    public BookSearch() {
        book_list = new HashMap<>();
    }

    public boolean searchBookWithAuthorNameAndTitle(String st) {
        return book_list.containsKey(st.hashCode());
    }

    public List<String> searchBookNameFromAuthorName(String authorName) {
        List<String> books = new ArrayList<String>();
        for (Book b : book_list.values()) {
            if (b.getBookAuthor().equals(authorName)) {
                books.add(b.getBookName());
            }
        }
        return books;
    }

    public List<String> searchBookAuthorFromBookName(String title) {
        List<String> books = new ArrayList<String>();
        for (Book b : book_list.values()) {
            if (b.getBookName().equals(title)) {
                books.add(b.getBookAuthor());
            }
        }
        return books;
    }


}
