package id.com.ervsoftware.ysl;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {
    @SerializedName("id")
    private String mID;
    @SerializedName("name")
    private String mName;

    public ProfileModel(String ID, String name){
        mID = ID;
        mName = name;
    }

    public String getID(){
        return mID;
    }
    public String getName(){
        return mName;
    }
}
