package yanzs.hfutlibrary.bean.responselib;

import java.util.List;

public class FacetsList {
    private List<FacetList> facetList ;

    private String id;

    private String label;

    public void setFacetList(List<FacetList> facetList){
        this.facetList = facetList;
    }
    public List<FacetList> getFacetList(){
        return this.facetList;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabel(){
        return this.label;
    }
}
