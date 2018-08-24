package yanzs.hfutlibrary.bean.post;

import java.util.ArrayList;
import java.util.List;

import yanzs.hfutlibrary.constant.Values;

public class RootPost {
    public RootPost(String fieldValue, int pageCount, String local) {
        setLocale("zh_CN");
        setFirst(true);
        setPageCount(pageCount);
        setPageSize(20);
        setSortType("desc");
        setSortField("relevance");
        FieldList fieldList = new FieldList();
        fieldList.setFieldCode("");
        fieldList.setFieldValue(fieldValue);
        List<FieldList> list = new ArrayList<>();
        list.add(fieldList);
        SearchWords searchWords = new SearchWords();
        searchWords.setFieldList(list);
        List<SearchWords> searchWordsList = new ArrayList<>();
        searchWordsList.add(searchWords);
        setSearchWords(searchWordsList);
        List<Filters> filters = new ArrayList<>();
        List<Limiter> limiter = new ArrayList<>();
        setLimiter(limiter);
        if (!local.equals(Values.SEARCH_LOCAL_KEY_ITEM[0])){
            Filters filter=new Filters();
            filter.setFieldName("locationFacet");
            List<String> values=new ArrayList<>();
            values.add(local);
            filter.setString(values);
            filters.add(filter);
        }
        setFilters(filters);
    }

    private List<SearchWords> searchWords;

    private List<Filters> filters;

    private List<Limiter> limiter;

    private String sortField;

    private String sortType;

    private int pageSize;

    private int pageCount;

    private String locale;

    private boolean first;

    public void setSearchWords(List<SearchWords> searchWords) {
        this.searchWords = searchWords;
    }

    public List<SearchWords> getSearchWords() {
        return this.searchWords;
    }

    public void setFilters(List<Filters> filters) {
        this.filters = filters;
    }

    public List<Filters> getFilters() {
        return this.filters;
    }

    public void setLimiter(List<Limiter> limiter) {
        this.limiter = limiter;
    }

    public List<Limiter> getLimiter() {
        return this.limiter;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortField() {
        return this.sortField;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortType() {
        return this.sortType;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean getFirst() {
        return this.first;
    }
}
