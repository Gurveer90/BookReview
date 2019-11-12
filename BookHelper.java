package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookHelper {

    public static String bookListFileName = "BookList.txt";
    public static String bookReviewsListFileName = "BookReviewsList.txt";

    public static Book getBookDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter book name");
        String bookName = sc.nextLine();
        System.out.println("Please enter book author");
        String bookAuthor = sc.nextLine();
        return new Book(bookName, bookAuthor);
    }

    public static Book getBookDetails(String name, String author) {
        return new Book(name, author);
    }

    public static String getBookAuthor(String reason) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter book Author to " + reason + " ");
        return sc.nextLine();
    }

    public static String getBookName(String reason) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter book name to " + reason + " ");
        return sc.nextLine();
    }

    public static String getStringWithMessage(String reason) {
        Scanner sc = new Scanner(System.in);
        System.out.println(reason);
        return sc.nextLine();
    }

    public static List<String> getList(String reason) {

        List<String> l;
        System.out.println("Please enter multiple items separated by , to add as " + reason + "  or leave empty to enter nothing ");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        l = new ArrayList<>(Arrays.asList(s.split(",")));
        return l;
    }

    public static List<String> getList(String reason, String text) {
        List<String> l;
        String s = text;
        l = new ArrayList<>(Arrays.asList(s.split(",")));
        return l;
    }
}

