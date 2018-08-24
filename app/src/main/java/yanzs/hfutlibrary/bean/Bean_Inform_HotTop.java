package yanzs.hfutlibrary.bean;

public class Bean_Inform_HotTop {
    private String value;
    private String time;
    private String url;

    public Bean_Inform_HotTop(String value, String time, String url){
        this.time=time;
        this.url=url;
        this.value=value;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
