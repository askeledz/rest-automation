package com.rest.autotests.core.webservice.appuser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rest.autotests.core.objects.*;

/**
 * Created by Andrej Skeledzija 2017
 *
 */
public class LoadAppUserData extends BasicObject {

    private AppUser appUser;

    public LoadAppUserData(AppUser appUser) {
        this.appUser = appUser;
    }

    public LoadAppUserData() {
        this.endpoint = "/appUser/loadAppUserData";

    }

    public void compareTo(LoadAppUserData login) {
    }

    public void compareTo(Boolean success, String authToken, int appUserId){
    }

    @Override
    public String toString() {
        return null;
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
