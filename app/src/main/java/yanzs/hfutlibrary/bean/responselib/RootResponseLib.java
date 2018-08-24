package yanzs.hfutlibrary.bean.responselib;

import java.util.List;

import yanzs.hfutlibrary.bean.post.Filters;

public class RootResponseLib {
    private int total;

    private double cost;

    private List<Content> content ;

    private List<FacetsList> facetsList ;

    private List<Filters> filters ;

    private List<TranslateWords> translateWords ;

    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setCost(double cost){
        this.cost = cost;
    }
    public double getCost(){
        return this.cost;
    }
    public void setContent(List<Content> content){
        this.content = content;
    }
    public List<Content> getContent(){
        return this.content;
    }
    public void setFacetsList(List<FacetsList> facetsList){
        this.facetsList = facetsList;
    }
    public List<FacetsList> getFacetsList(){
        return this.facetsList;
    }
    public void setFilters(List<Filters> filters){
        this.filters = filters;
    }
    public List<Filters> getFilters(){
        return this.filters;
    }
    public void setTranslateWords(List<TranslateWords> translateWords){
        this.translateWords = translateWords;
    }
    public List<TranslateWords> getTranslateWords(){
        return this.translateWords;
    }
}
