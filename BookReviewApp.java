package ui;

import exceptions.BookIdAlreadyAvailabeException;
import exceptions.BookNotFoundException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BookReviewApp {
    static ReadWebPageEx webUrl = new ReadWebPageEx();
    static FileHandler bookListFile = new FileHandler(BookHelper.bookListFileName);
    static FileHandler bookReviewListFile = new FileHandler(BookHelper.bookReviewsListFileName);
    static BookListDatabase bookDatabaseList = new BookListDatabase(bookListFile);
    static BookList bookList = new BookList();
    static ReviewerList reviewerList = new ReviewerList(bookReviewListFile);

    static int previousSelection = -9;

    static
    private void refreshList() {
        bookDatabaseList.refreshList();
    }

    BookReviewApp() {
        refreshList();
    }

    static void run(int choice) {
        if (choice == 1) {
            Book book = BookHelper.getBookDetails();

            book.addListener(bookList);
            book.addListener(bookDatabaseList);
            List<String> audio = BookHelper.getList("Audio");
            List<String> video = BookHelper.getList("Video");
            AudioChapter ac = new AudioChapter(audio);
            VideoChapter vc = new VideoChapter(video);
            book.addBookComposite(ac);
            book.addBookComposite(vc);


            bookList.addBook(book);

            try {
                bookDatabaseList.addBook(book);
            } catch (BookIdAlreadyAvailabeException e) {
                System.out.println(e.getMessage());
            }
        }

        if (choice == 2) {
            String author = BookHelper.getBookAuthor("delete");
            String bookName = BookHelper.getBookName("delete");
            bookList.removeBook(author, bookName);
            try {
                bookDatabaseList.removeBook(author, bookName);
            } catch (BookNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        if (choice == 3) {

            String bookAuthor = BookHelper.getBookAuthor("search");
            for (String s : bookList.searchBookNameFromAuthorName(bookAuthor)) {
                System.out.println(s);
            }
        }

        if (choice == 4) {
            String bookName = BookHelper.getBookName("search");
            for (String s : bookList.searchBookAuthorFromBookName(bookName)) {
                System.out.println(s);
            }
        }

        if (choice == 5) {
            String bookAuthor = BookHelper.getBookAuthor("search");
            String bookName = BookHelper.getBookName("search");
            if (bookList.searchBookWithAuthorNameAndTitle(bookAuthor + bookName)) {
                System.out.println("Book Found");
            } else {
                System.out.println("Book Not Found");
            }
        }

        if (choice == 6) {
            Book book = BookHelper.getBookDetails();
            String revName = BookHelper.getStringWithMessage("Insert your name: ");
            String review = BookHelper.getStringWithMessage("Insert your review: ");
            reviewerList.addReview(new Reviewer(revName, review, book));
        }

        if (choice == 7) {
            reviewerList.printReviews();
        }
        if (choice == 8) {
            bookList.displayBooks();
        }
        if (choice == 9) {
            webUrl.printData();
        }
    }

    private static JTextField addLabelAndTextBox(JPanel panel, int x, int y, String title) {

        ListOfStrings a = new ListOfStrings();
        for(String s: a)
        {

        }

        JLabel bookNameAddBook = new JLabel(title);
        panel.add(bookNameAddBook);
        bookNameAddBook.setBounds(x, y, 150,
                bookNameAddBook.getPreferredSize().height);

        JTextField textField = new JTextField(8);
        panel.add(textField);
        textField.setBounds(x + 100, y, 200, 20);
        return textField;
    }

    private static void addBook(String bName, String aName, String aChpters, String vChapters) {
        Book book = BookHelper.getBookDetails(bName, aName);

        book.addListener(bookList);
        book.addListener(bookDatabaseList);
        List<String> audio = BookHelper.getList("Audio", aChpters);
        List<String> video = BookHelper.getList("Video", vChapters);
        AudioChapter ac = new AudioChapter(audio);
        VideoChapter vc = new VideoChapter(video);
        book.addBookComposite(ac);
        book.addBookComposite(vc);
        bookList.addBook(book);

        try {
            bookDatabaseList.addBook(book);
        } catch (BookIdAlreadyAvailabeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createAndShowGUI() {

        JFrame frame = new JFrame("Book Review Applications");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add Book UI
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton addBookButton = new JButton("Add Book");
        addBookButton.setBounds(50, 20, 150, 35);
        addBookButton.addActionListener(e -> {
            JFrame frame1 = new JFrame("Add Book");
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //Add Book UI
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);
            JTextField nameBook = addLabelAndTextBox(panel1, 15, 15, "Book Name");
            JTextField authorName = addLabelAndTextBox(panel1, 15, 45, "Book Author");
            JTextField audioChapters = addLabelAndTextBox(panel1, 15, 75, "Audio Chapters");
            JTextField videoChapters = addLabelAndTextBox(panel1, 15, 105, "Video Chapters");
            JButton button = new JButton("Add Book");
            button.setBounds(140, 135, 100, 30);
            button.addActionListener(e1 -> {
                addBook(nameBook.getText(), authorName.getText(),
                        audioChapters.getText(), videoChapters.getText());
                frame1.setVisible(false);
            });
            panel1.add(button);
            frame1.add(panel1);
            frame1.setSize(400, 300);
            frame1.setVisible(true);
        });
        panel.add(addBookButton);

        JButton findBookButton = new JButton("Find Book");
        findBookButton.setBounds(50, 60, 150, 35);
        findBookButton.addActionListener(e -> {
            JFrame frameFindBook = new JFrame("Find Book");
            frameFindBook.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //Add Book UI
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            JTextField searchText = addLabelAndTextBox(panel1, 15, 15, "Search Text");

            JButton button = new JButton("Find");
            button.setBounds(140, 45, 100, 30);

            button.addActionListener(eB -> {


                JFrame frame1 = new JFrame("Display Books");
                frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                String[] columnNames = {"Book Name", "Author Name", "Audio Chapters", "Video Chapters"};
                Object[][] data = bookList.displayBooksList(searchText.getText());
                JTable table = new JTable(data, columnNames);

                DefaultTableModel model = new DefaultTableModel();
                JTable reviewsTable = new JTable(model);
                model.addColumn("Reviewer Name");
                model.addColumn("Review");
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                table.setCellSelectionEnabled(false);
                table.setRowSelectionAllowed(true);
                table.getSelectionModel().addListSelectionListener(event -> {
                    if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1 &&
                            table.getSelectedRow() != previousSelection) {

                        int count = 0;//model.getRowCount();
                        //  for (int i = 0; i < count; i++) {
                        //    model.removeRow(i);
                        // }
                        model.setRowCount(0);
                        previousSelection = table.getSelectedRow();

                        Object[][] dataReviews = reviewerList.getReviews(table.getValueAt(table.getSelectedRow(), 0).toString(),
                                table.getValueAt(table.getSelectedRow(), 1).toString());

                        count = dataReviews.length;
                        for (int i = 0; i < count; i++) {
                            model.addRow(dataReviews[i]);
                        }
                    }

                });

                JScrollPane bookListScrollBar = new JScrollPane(table);
                JScrollPane reviewsScrollBar = new JScrollPane(reviewsTable);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                        bookListScrollBar, reviewsScrollBar);

                frame1.add(splitPane);
                frame1.setSize(1000, 500);
                frame1.setVisible(true);

            });

            panel1.add(button);
            frameFindBook.add(panel1);

            frameFindBook.setSize(400, 300);
            frameFindBook.setVisible(true);
        });
        panel.add(findBookButton);

        JButton reviewBookButton = new JButton("Review Book");
        reviewBookButton.setBounds(50, 100, 150, 35);
        reviewBookButton.addActionListener(e -> {
            JFrame frame1 = new JFrame("Add Review");
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);
            JTextField nameBook = addLabelAndTextBox(panel1, 15, 15, "Book Name");
            JTextField authorName = addLabelAndTextBox(panel1, 15, 45, "Book Author");
            JTextField reviewerName = addLabelAndTextBox(panel1, 15, 75, "Reviewer ");
            JTextField review = addLabelAndTextBox(panel1, 15, 105, "Review ");
            JButton button = new JButton("Review Book");
            button.setBounds(140, 135, 150, 30);
            button.addActionListener(e1 -> {
                Book book = BookHelper.getBookDetails(nameBook.getText(), authorName.getText());
                reviewerList.addReview(new Reviewer(reviewerName.getText(), review.getText(), book));
                frame1.setVisible(false);
            });
            panel1.add(button);
            frame1.add(panel1);
            frame1.setSize(400, 300);
            frame1.setVisible(true);
        });
        panel.add(reviewBookButton);

        JButton listBookButton = new JButton("Display All Books");
        listBookButton.setBounds(50, 140, 150, 35);
        listBookButton.addActionListener(e -> {

            JFrame frame1 = new JFrame("Display Books");
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // JPanel leftPanel = new JPanel();
            // leftPanel.setPreferredSize(new Dimension(500,400));
            // JPanel rightPanel = new JPanel();
            // rightPanel.setPreferredSize(new Dimension(500,400));

            String[] columnNames = {"Book Name", "Author Name", "Audio Chapters", "Video Chapters"};
            Object[][] data = bookList.displayBooksList();
            DefaultTableModel model = new DefaultTableModel();

            JTable reviewsTable = new JTable(model);
            model.addColumn("Reviewer Name");
            model.addColumn("Review");
            JScrollPane reviewsScrollBar = new JScrollPane(reviewsTable);
            JTable table = new JTable(data, columnNames);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setCellSelectionEnabled(false);
            table.setRowSelectionAllowed(true);
            table.getSelectionModel().addListSelectionListener(event -> {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1 &&
                        table.getSelectedRow() != previousSelection) {

                    int count = 0;//model.getRowCount();
                    //  for (int i = 0; i < count; i++) {
                    //    model.removeRow(i);
                    // }
                    model.setRowCount(0);
                    previousSelection = table.getSelectedRow();

                    Object[][] dataReviews = reviewerList.getReviews(table.getValueAt(table.getSelectedRow(), 0).toString(),
                            table.getValueAt(table.getSelectedRow(), 1).toString());

                    count = dataReviews.length;
                    for (int i = 0; i < count; i++) {
                        model.addRow(dataReviews[i]);
                    }
                }

            });

            JScrollPane bookListScrollBar = new JScrollPane(table);
            //  leftPanel.add(bookListScrollBar);
            // rightPanel.add(reviewsScrollBar);
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bookListScrollBar, reviewsScrollBar);

            frame1.add(splitPane);
            frame1.setSize(1000, 500);
            frame1.setVisible(true);


        });
        panel.add(listBookButton);

        JButton deleteBookButton = new JButton("Delete Book");
        deleteBookButton.setBounds(50, 180, 150, 35);
        deleteBookButton.addActionListener(e -> {

            JFrame frame1 = new JFrame("Delete Book");
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //Add Book UI
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);
            JTextField nameBook = addLabelAndTextBox(panel1, 15, 15, "Book Name");
            JTextField authorName = addLabelAndTextBox(panel1, 15, 45, "Book Author");
            JButton button = new JButton("Delete Book");
            button.setBounds(140, 85, 150, 30);
            button.addActionListener(e1 -> {
                bookList.removeBook(authorName.getText(), nameBook.getText());
                try {
                    bookDatabaseList.removeBook(authorName.getText(), nameBook.getText());
                } catch (BookNotFoundException e11) {
                }


                frame1.setVisible(false);
            });
            panel1.add(button);
            frame1.add(panel1);
            frame1.setSize(400, 300);
            frame1.setVisible(true);

        });
        panel.add(deleteBookButton);


        frame.add(panel);
        frame.setSize(300, 300);
        frame.setVisible(true);

    }


    public static void main(String[] args) {
        createAndShowGUI();
    }
}
    /*public static void main(String[] args) {

        int choice = 9999;

        while (choice != 0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("--------------------------------------");
            System.out.println("Welcome to Book Review App!!");
            System.out.println("Enter 1 to add a book\n"
                    + "Enter 2 to remove a book\n"
                    + "Enter 3 to search a book with Book author name\n"
                    + "Enter 4 to search a book with Book name\n"
                    + "Enter 5 to check if a book exists\n"
                    + "Enter 6 to add review\n"
                    + "Enter 7 to display reviews\n"
                    + "Enter 8 to display all books\n"
                    + "Enter 9 to print from web\n"
                    + "Enter 0 to Exit\n");
            choice = sc.nextInt();
            run(choice);
        }


    }}*/



