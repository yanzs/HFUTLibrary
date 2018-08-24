package yanzs.hfutlibrary.bean;

public class Bean_News {
    private String title;
    private String url;
    private String day;
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Bean_News() {

    }

    public Bean_News(String day,String month, String title, String url) {
        this.title = title;
        this.url = url;
        this.day = day;
        this.month=month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }


}
