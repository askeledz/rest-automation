package com.rest.autotests.core.objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rest.autotests.core.webservice.appuser.LoadAppUserData;

public class Adress extends BasicObject{

    private String name; //"name":"ime",
    private String thoroughfare; //                "thoroughfare":"thoroughfare",
    private String subThoroughfare; //                "subThoroughfare":"subThoroughfare",
    private String postalCode;//                "postalCode":"postalcode",
    private String locality;     //                "locality":"locality",
    private String iSOcountryCode;   //                "iSOcountryCode":"iSOcountryCode"

    public Adress(String name, String thoroughfare, String subThoroughfare, String postalCode, String locality, String iSOcountryCode) {
        this.name = name;
        this.thoroughfare = thoroughfare;
        this.subThoroughfare = subThoroughfare;
        this.postalCode = postalCode;
        this.locality = locality;
        this.iSOcountryCode = iSOcountryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThoroughfare() {
        return thoroughfare;
    }

    public void setThoroughfare(String thoroughfare) {
        this.thoroughfare = thoroughfare;
    }

    public String getSubThoroughfare() {
        return subThoroughfare;
    }

    public void setSubThoroughfare(String subThoroughfare) {
        this.subThoroughfare = subThoroughfare;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getiSOcountryCode() {
        return iSOcountryCode;
    }

    public void setiSOcountryCode(String iSOcountryCode) {
        this.iSOcountryCode = iSOcountryCode;
    }


    public void compareTo(LoadAppUserData login) {
    }

    public void compareTo(Boolean success, String authToken, int appUserId){
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", thoroughfare='" + thoroughfare +  '\'' +        //String
                ", subThoroughfare='" + subThoroughfare +  '\'' +        //String
                ", postalCode='" + postalCode +  '\'' +        //String
                ", locality='" + locality +  '\'' +        //String
                ", iSOcountryCode='" + iSOcountryCode +  '\'' +
                '}';
    }

    @Override
    public String toJson(String... omittedFields) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(this).getAsJsonObject();
        for (String str : omittedFields) {
            jsonObject.remove(str);
        }
        jsonObject.remove("endpoint");
        jsonObject.remove("id");
        return jsonObject.toString();
    }


}
