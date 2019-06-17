package yanzs.hfutlibrary.bean.responsedou;

import java.util.List;

public class DouData {
    private String title;
    private String imgUrl;
    private List<String> strings;

    public String getTitle() {
        return title;
    }

    public DouData(String title, String imgUrl, List<String> strings) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.strings = strings;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
}
