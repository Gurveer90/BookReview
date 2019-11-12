package ui;

import java.util.List;

public class AudioChapter implements BookComposite {
    private List<String> nameList;

    public AudioChapter(List<String> nameList) {
        this.nameList = nameList;
        this.nameList.add(0,"Audio");

    }

    public List<String> getList() {
        return nameList;
    }
}
