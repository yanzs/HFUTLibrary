package yanzs.hfutlibrary.bean.post;

import java.util.List;

public class SearchWords {
    private List<FieldList> fieldList ;

    public void setFieldList(List<FieldList> fieldList){
        this.fieldList = fieldList;
    }
    public List<FieldList> getFieldList(){
        return this.fieldList;
    }
}
