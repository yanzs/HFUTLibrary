package yanzs.hfutlibrary.bean;

public class Bean_Inform_GoodBook {
    private String name;
    private String url;
    private String author;
    private String publish;
    private String index;
    private String store;
    private String times;
    private String scale;

    public Bean_Inform_GoodBook(String name, String url, String author, String publish, String index, String store, String times, String scale){
        this.name=name;
        this.url = url;
        this.author = author;
        this.publish=publish;
        this.index=index;
        this.times = times;
        this.store=store;
        this.scale=scale;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getPublish() {
        return publish;
    }

    public String getScale() {
        return scale;
    }

    public String getStore() {
        return store;
    }

    public String getTimes() {
        return times;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
