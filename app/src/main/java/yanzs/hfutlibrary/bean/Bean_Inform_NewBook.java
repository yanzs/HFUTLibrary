package yanzs.hfutlibrary.bean;

public class Bean_Inform_NewBook {
    private String name;
    private String url;
    private String publish;
    private String index;


    public Bean_Inform_NewBook(String name, String url, String publish, String index) {
        this.name = name;
        this.url = url;
        this.publish = publish;
        this.index = index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public String getUrl() {
        return url;
    }


    public String getName() {
        return name;
    }

    public String getPublish() {
        return publish;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }
}
