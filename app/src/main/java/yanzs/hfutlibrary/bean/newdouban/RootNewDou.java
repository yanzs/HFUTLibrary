package yanzs.hfutlibrary.bean.newdouban;

public class RootNewDou {
    private Payload payload;

    private String type;

    public void setPayload(Payload payload){
        this.payload = payload;
    }
    public Payload getPayload(){
        return this.payload;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
}
