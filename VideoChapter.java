package ui;

import java.util.List;

public class VideoChapter implements BookComposite {
    private List<String> videoChapter_nameList;

    public VideoChapter(List<String> videoChapter_nameList) {
        this.videoChapter_nameList = videoChapter_nameList;
        this.videoChapter_nameList.add(0,"Video");

    }

    public List<String> getList() {
        return videoChapter_nameList;
    }
}
