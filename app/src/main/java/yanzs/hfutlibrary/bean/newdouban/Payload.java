package yanzs.hfutlibrary.bean.newdouban;

import java.util.List;

public class Payload {
    private int count;

    private String error_info;

    private List<Items> items ;

    private Report report;

    private int start;

    private String text;

    private int total;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setError_info(String error_info){
        this.error_info = error_info;
    }
    public String getError_info(){
        return this.error_info;
    }
    public void setItems(List<Items> items){
        this.items = items;
    }
    public List<Items> getItems(){
        return this.items;
    }
    public void setReport(Report report){
        this.report = report;
    }
    public Report getReport(){
        return this.report;
    }
    public void setStart(int start){
        this.start = start;
    }
    public int getStart(){
        return this.start;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
}
