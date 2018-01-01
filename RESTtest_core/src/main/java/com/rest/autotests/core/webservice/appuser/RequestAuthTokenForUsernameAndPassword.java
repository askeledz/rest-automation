package com.rest.autotests.core.webservice.appuser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rest.autotests.core.objects.BasicObject;

import static org.testng.Assert.assertEquals;

/**
 * Created by Andrej Skeledzija 2017
 */
public class RequestAuthTokenForUsernameAndPassword extends BasicObject {

    private String email;
    private String password;

    private boolean success;
    private String authToken;
    private int appUserId;

    public RequestAuthTokenForUsernameAndPassword(String email, String password) {
        this.email = email;
        this.password = password;
        this.endpoint = "/appUser/requestAuthTokenForUsernameAndPassword";
    }


    public RequestAuthTokenForUsernameAndPassword() {
        this.endpoint = "/appUser/requestAuthTokenForUsernameAndPassword";

    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public void compareTo(RequestAuthTokenForUsernameAndPassword login) {
        assertEquals(this.isSuccess(), true, "Success");
        assertEquals(this.getAppUserId(), login.getAppUserId(), "appUserId are differ");
        assertEquals(this.getAuthToken(), login.getAuthToken(), "authToken are differ");

    }

    public void compareTo(Boolean success, String authToken, int appUserId){
        assertEquals(this.isSuccess(), true, "Success");
        assertEquals(this.getAppUserId(), appUserId, "appUserId are differ");
        assertEquals(this.getAuthToken(), authToken, "authToken are differ");
    }

    @Override
    public String toString() {
        return "requestAuthTokenForUsernameAndPassword{" +
                "email=" + email +
                ", password=" + password +
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
