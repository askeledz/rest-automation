package com.rest.autotests.core.objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by Andrej Skeledzija 2017
 */
public abstract class BasicObject {

    public transient String endpoint = "";
    public transient long id;


    public BasicObject() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toJson(String ... omittedFields){
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
