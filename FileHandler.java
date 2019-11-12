package ui;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private String fileName;
    PrintWriter writer;


    public FileHandler(String fileName) {
        this.fileName = fileName;
        if (!checkFileExists())
            createFile();
    }

    private void createFile() {
        try {
            Files.createFile(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkFileExists() {
        return Files.exists(Paths.get(fileName));
    }

    public void writeToFile(String value) {
        try {
            writer = new PrintWriter(new FileOutputStream(
                    new File(fileName), true));
            writer.println(value);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFileContent() {
        try {
            writer = new PrintWriter(new FileOutputStream(
                    new File(fileName), false));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> readlAllLines()
    {
        try {
            return Files.readAllLines(Paths.get(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
      return new ArrayList<String>();
    }

    public long getFileLineCount() {
        long size = 0;
        try {
            size = Files.lines(Paths.get(fileName), Charset.defaultCharset()).count();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }
}
