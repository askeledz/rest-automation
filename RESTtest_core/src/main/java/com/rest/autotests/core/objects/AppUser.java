package com.rest.autotests.core.objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rest.autotests.core.webservice.appuser.LoadAppUserData;

/**
 * Created by Andrej Skeledzija 2017
 */
public class AppUser extends BasicObject{

    private int appUserId;  ///appUserId": 6572,
    private String firstName; //"firstName": "Martin",
    private String lastName;  //     "lastName": "Snajdr",
    //Todo
    //private Birthday birthday; //        "birthday": null,
    //private Phone phone;       //       "phone": null,
    private String email;      //     "email": "test0001@gmail.com",
    private PushActivation pushActivation;
    private CurrentTeamUser currentTeamUser;
    private TeamUsers teamUsers;

    public AppUser() {
        this.endpoint = "/appUser/?????????";

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
