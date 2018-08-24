package yanzs.hfutlibrary.bean;

public class Bean_Inform_Search  {
    private String url;
    private String callNo;
    private String title;
    private String author;
    private String publisher;

    public Bean_Inform_Search(String url,String callNo,String title,String author,String publisher){
        this.author=author;
        this.callNo=callNo;
        this.publisher=publisher;
        this.title=title;
        this.url=url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getCallNo() {
        return callNo;
    }

    public String getPublisher() {
        return publisher;
    }
}
