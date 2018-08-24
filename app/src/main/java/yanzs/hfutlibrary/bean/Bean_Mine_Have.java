package yanzs.hfutlibrary.bean;

public class Bean_Mine_Have {

    private String index;
    private String title;
    private String author;
    private String lendDate;
    private String returnData;
    private String local;
    private String url;


    public Bean_Mine_Have(String index,String title,String author,String lendDate,String returnData,String local,String url){
        this.url=url;
        this.index=index;
        this.title=title;
        this.author=author;
        this.lendDate=lendDate;
        this.returnData=returnData;
        this.local=local;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getIndex() {
        return index;
    }

    public String getLendDate() {
        return "借出日期："+lendDate;
    }

    public String getLocal() {
        return local;
    }

    public String getReturnData() {
        return "归还日期："+returnData;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

}
