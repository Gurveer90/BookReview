package ui;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BookList extends BookSearch implements BookInterface, BookReadInterface, InvalidationListener {

    public BookList() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(BookHelper.bookListFileName));
            for (String line : lines) {
                ArrayList<String> partsOfLine = splitOnSpace(line);
                Book b = new Book(partsOfLine.get(1), partsOfLine.get(2));
                book_list.put(Integer.parseInt(partsOfLine.get(0)), b);
            }
        } catch (Exception e) {
        }

    }

    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split("--");
        return new ArrayList<>(Arrays.asList(splits));
    }

    @Override
    public void addBook(Book book) {
        if (!book_list.containsKey((book.getBookAuthor() + book.getBookName()).hashCode()))
            book_list.put((book.getBookAuthor() + book.getBookName()).hashCode(), book);
    }

    @Override
    public HashMap<Integer, Book> getListOfBooks() {
        return book_list;
    }

    @Override
    public void removeBook(String bookAuthor, String bookName) {
        if (book_list.containsKey((bookAuthor + bookName).hashCode()))
            book_list.remove((bookAuthor + bookName).hashCode());
    }

    //EFFECTS: Display List of Books
    public void displayBooks() {
        for (Book b : book_list.values()) {
            System.out.println("Book Name is: " + b.getBookName() + "  --  " + "Author Name is: " + b.getBookAuthor()
                    + " \n" + " Book List -- " + String.join(",", b.getList()));
        }
    }
	
	public String[][] displayBooksList(String text) {
		int count =0;
		try {
            List<String> lines = Files.readAllLines(Paths.get(BookHelper.bookListFileName));
            for (String line : lines) {
                ArrayList<String> partsOfLine = splitOnSpace(line);
                if(partsOfLine.get(1).toLowerCase().contains(text.toLowerCase())
                        || partsOfLine.get(2).toLowerCase().contains(text.toLowerCase())) {
                    count++;
                }
            }
        } catch (Exception e) {
        }

        String[][] list = new String[count][];

        try {
            int i = 0;
            List<String> lines = Files.readAllLines(Paths.get(BookHelper.bookListFileName));
            for (String line : lines) {
                ArrayList<String> partsOfLine = splitOnSpace(line);
                if(partsOfLine.get(1).toLowerCase().contains(text.toLowerCase())
                        || partsOfLine.get(2).toLowerCase().contains(text.toLowerCase())) {
                    Book b = new Book(partsOfLine.get(1), partsOfLine.get(2));

                    String audio = "";
                    String video = "";

                    try {
                        String[] parts = partsOfLine.get(3).split("Video,");
                        audio = parts[0].split("Audio,")[1];
                        video = parts[1];

                    } catch (Exception e) {
                    }

                    list[i] = new String[]{b.getBookName(), b.getBookAuthor(), audio, video};
                    i++;
                }
            }
        } catch (Exception e) {
        }

        return list;
    }

    //EFFECTS: Display List of Books
    public String[][] displayBooksList() {

        String[][] list = new String[book_list.size()][];

        try {
            int i = 0;
            List<String> lines = Files.readAllLines(Paths.get(BookHelper.bookListFileName));
            for (String line : lines) {
                ArrayList<String> partsOfLine = splitOnSpace(line);
                Book b = new Book(partsOfLine.get(1), partsOfLine.get(2));

                String audio = "";
                String video = "";

                try
                {
                    String[] parts = partsOfLine.get(3).split("Video,");
                    audio = parts[0].split("Audio,")[1];
                    video = parts[1];

                }catch (Exception e)
                {
                }

                list[i] = new String[]{b.getBookName(), b.getBookAuthor(), audio, video};
                i++;
            }
        } catch (Exception e) {
        }

        return list;
    }


    @Override
    public void invalidated(Observable observable) {
        System.out.println("From list ");
        ReadWebPageEx ex = new ReadWebPageEx();
        ex.printData();

    }
}



