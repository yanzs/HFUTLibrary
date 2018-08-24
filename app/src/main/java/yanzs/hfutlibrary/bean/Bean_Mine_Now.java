package yanzs.hfutlibrary.bean;

public class Bean_Mine_Now {
    private String num;
    private String name;
    private String lendData;
    private String endData;
    private String lendNum;
    private String locate;
    private String item;
    private String url;

    public Bean_Mine_Now(String num,String name,String lendData,String endData,String lendNum,String locate,String item,String url){
        this.name=name;
        this.num=num;
        this.lendData=lendData;
        this.endData=endData;
        this.lendNum=lendNum;
        this.locate=locate;
        this.item=item;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public String getEndData() {
        return endData;
    }

    public String getName() {
        return name;
    }

    public String getNum() {
        return num;
    }

    public String getItem() {
        return item;
    }

    public String getLendData() {
        return lendData;
    }

    public String getLendNum() {
        return lendNum;
    }

    public String getLocate() {
        return locate;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setLendData(String lendData) {
        this.lendData = lendData;
    }

    public void setLendNum(String lendNum) {
        this.lendNum = lendNum;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
