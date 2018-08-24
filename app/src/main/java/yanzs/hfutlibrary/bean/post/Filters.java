package yanzs.hfutlibrary.bean.post;

import java.util.List;

public class Filters {
    private String fieldName;

    private List<String> values ;

    public void setFieldName(String fieldName){
        this.fieldName = fieldName;
    }
    public String getFieldName(){
        return this.fieldName;
    }
    public void setString(List<String> values){
        this.values = values;
    }
    public List<String> getString(){
        return this.values;
    }
}
