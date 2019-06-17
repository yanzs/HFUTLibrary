package yanzs.hfutlibrary.bean.newdouban;

public class Rating {
    private int count;

    private String rating_info;

    private double star_count;

    private double value;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setRating_info(String rating_info){
        this.rating_info = rating_info;
    }
    public String getRating_info(){
        return this.rating_info;
    }
    public void setStar_count(double star_count){
        this.star_count = star_count;
    }
    public double getStar_count(){
        return this.star_count;
    }
    public void setValue(double value){
        this.value = value;
    }
    public double getValue(){
        return this.value;
    }
}
