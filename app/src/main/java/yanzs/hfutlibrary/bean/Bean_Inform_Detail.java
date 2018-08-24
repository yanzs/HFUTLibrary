package yanzs.hfutlibrary.bean;

import java.util.List;

public class Bean_Inform_Detail {
    private String imgUrl;
    private String name;
    private String writer;
    private String introduction;
    private List<Bean_Inform_Local> beanInformLocals;

    public void setBeanInformLocals(List<Bean_Inform_Local> beanInformLocals) {
        this.beanInformLocals = beanInformLocals;
    }

    public List<Bean_Inform_Local> getBeanInformLocals() {
        return beanInformLocals;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getWriter() {
        return writer;
    }

}
