package yanzs.hfutlibrary.bean.newdouban;

import java.util.List;

public class Items {
    private String abstract_2;

    private String cover_url;

    private List<Extra_actions> extra_actions ;

    private int id;

    private String interest;

    private List<Label_actions> label_actions ;

    private List<Labels> labels ;

    private String more_url;

    private Rating rating;

    private String title;

    private List<Topics> topics ;

    private String tpl_name;

    private String url;

    public void setAbstract_2(String abstract_2){
        this.abstract_2 = abstract_2;
    }
    public String getAbstract_2(){
        return this.abstract_2;
    }
    public void setCover_url(String cover_url){
        this.cover_url = cover_url;
    }
    public String getCover_url(){
        return this.cover_url;
    }
    public void setExtra_actions(List<Extra_actions> extra_actions){
        this.extra_actions = extra_actions;
    }
    public List<Extra_actions> getExtra_actions(){
        return this.extra_actions;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setInterest(String interest){
        this.interest = interest;
    }
    public String getInterest(){
        return this.interest;
    }
    public void setLabel_actions(List<Label_actions> label_actions){
        this.label_actions = label_actions;
    }
    public List<Label_actions> getLabel_actions(){
        return this.label_actions;
    }
    public void setLabels(List<Labels> labels){
        this.labels = labels;
    }
    public List<Labels> getLabels(){
        return this.labels;
    }
    public void setMore_url(String more_url){
        this.more_url = more_url;
    }
    public String getMore_url(){
        return this.more_url;
    }
    public void setRating(Rating rating){
        this.rating = rating;
    }
    public Rating getRating(){
        return this.rating;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setTopics(List<Topics> topics){
        this.topics = topics;
    }
    public List<Topics> getTopics(){
        return this.topics;
    }
    public void setTpl_name(String tpl_name){
        this.tpl_name = tpl_name;
    }
    public String getTpl_name(){
        return this.tpl_name;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}
