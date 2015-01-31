package ro.bfc.cpee_233.models;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Collection;

/**
 * Created by catalin on 1/3/15.
 */
public class CPEEDocument {
    public GlobalPrices Global;
    public Collection<Price> Prices;
    public Collection<County> Counties;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static  CPEEDocument readJSON(String jsonDoc){
        CPEEDocument doc = null;

        try{
            Gson gson = new Gson();
            doc = gson.fromJson(jsonDoc, CPEEDocument.class);
        }
        catch (Exception ex){
            Log.d("CPEEDocument.readJSON", ex.getMessage());
        }

        return doc;
    }
}
