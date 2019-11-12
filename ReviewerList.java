package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewerList {
    private List<Reviewer> reviewer_list;
    private FileHandler fileHandler;

    public ReviewerList(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        reviewer_list = new ArrayList<>();
        read();
    }

    public List<Reviewer> getReviewerList() {
        return reviewer_list;
    }

    public void printReviews() {
        for (Reviewer r : reviewer_list) {
            System.out.println(r.getName() + " " + r.getReview() + " " + r.getBook().getBookName() + " " +
                    r.getBook().getBookAuthor());
        }
    }

    public String[][] getReviews(String bookName, String bookAuthor) {
        int count = 0;

        for (Reviewer r : reviewer_list) {
            if (r.getBook().getBookName().toLowerCase().equals(bookName.toLowerCase()) &&
                    r.getBook().getBookAuthor().toLowerCase().equals(bookAuthor.toLowerCase())) {
                count++;
            }
        }
        String[][] list = new String[count][];

        count = 0;
        for (Reviewer r : reviewer_list) {
            if (r.getBook().getBookName().toLowerCase().equals(bookName.toLowerCase()) &&
                    r.getBook().getBookAuthor().toLowerCase().equals(bookAuthor.toLowerCase())) {
                list[count] = new String[]{r.getName(), r.getReview()};
                count++;
            }
        }

        return list;
    }

    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split("--");
        return new ArrayList<>(Arrays.asList(splits));
    }

    private void read() {
        try {
            List<String> lines = fileHandler.readlAllLines();
            for (String line : lines) {
                Reviewer r;
                ArrayList<String> partsOfLine = splitOnSpace(line);
                r = new Reviewer(partsOfLine.get(0),
                        partsOfLine.get(1), new Book(partsOfLine.get(3), partsOfLine.get(4)));
                reviewer_list.add(r);
            }
        } catch (Exception e) {
        }

    }

    private void saveToFile(Reviewer r) {
        try {
            String line = r.getName() + "--" + r.getReview() + "--" + r.getBook().getBookID() + "--" +
                    r.getBook().getBookName() + "--" +
                    r.getBook().getBookAuthor();
            fileHandler.writeToFile(line);

        } catch (Exception e) {

        }

    }

    public void addReview(Reviewer r) {
        saveToFile(r);
        reviewer_list.add(r);
    }
}
