package yanzs.hfutlibrary.bean;

public class Bean_Inform_WillBuy {

    private String title;
    private String author;
    private String publisher;
    private String data;
    private String state;

    public Bean_Inform_WillBuy(String title,String author,String publisher,String data,String state){
        this.publisher = publisher;
        this.author = author;
        this.title = title;
        this.data = data;
        this.state = state;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setData(String data) {
        this.data = data;
    }


    public void setState(String state) {
        this.state = state;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getData() {
        return data;
    }

    public String getState() {
        return state;
    }
}
