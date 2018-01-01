package com.rest.autotests.core.test;

import com.rest.autotests.core.util.Config;
import com.rest.autotests.core.util.filters.MyFilter;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;

import static java.lang.System.getProperty;

/**
 * Created by Andrej Skeledzija 2017
 */
public class TestSuite {

    public void beforeSuite() {
//        String serverIp, serverPort, accessKeyValue;
//        if (getProperty("server.ip") != null && !getProperty("server.ip").isEmpty()) {
//            serverIp = getProperty("server.ip");
//        } else {
//            serverIp = Config.SERVER_IP;
//        }
//
//        if (getProperty("server.port") != null && !getProperty("server.port").isEmpty()) {
//            serverPort = getProperty("server.port");
//        } else {
//            serverPort = Config.SERVER_PORT;
//        }
//
//        if (getProperty("server.key") != null && !getProperty("server.key").isEmpty()) {
//            accessKeyValue = getProperty("server.key");
//        } else {
//            accessKeyValue = Config.ACCESS_KEY_VALUE;
//        }
//
//
//        RestAssured.baseURI = "http://" + serverIp;
//        RestAssured.port = Integer.parseInt(serverPort);
//        RestAssured.basePath = "/irm/rest/" + Config.SERVER_API_VERSION;
//
//        RequestSpecBuilder builder = new RequestSpecBuilder();
//        builder.addHeader(Config.ACCESSKEY_KEY, accessKeyValue);
//        builder.setContentType(ContentType.JSON);
//        RestAssured.requestSpecification = builder.build();
//
//        RestAssured.replaceFiltersWith(new MyFilter());


        String serverUrl, serverStaticToken, userEmail, userPassword, userAuthToken, userteamUserId, userteamId;
            if (getProperty("server.url") != null && !getProperty("server.url").isEmpty()) {
                serverUrl = getProperty("server.url");
            }else {
              serverUrl = Config.SERVER_URL;
            }

            if (getProperty("server.staticToken") != null && !getProperty("server.staticToken").isEmpty()) {
                serverStaticToken = getProperty("server.staticToken");
            }else {
                serverStaticToken = Config.SERVER_STATIC_TOKEN;
            }

            if (getProperty("user.email") != null && !getProperty("user.email").isEmpty()) {
                userEmail = getProperty("user.email");
            }else {
                userEmail = Config.USER_EMAIL;
            }

            if (getProperty("user.password") != null && !getProperty("user.password").isEmpty()) {
                userPassword = getProperty("user.password");
            }else {
                userPassword = Config.USER_PASSWORD;
            }

            if (getProperty("user.authToken") != null && !getProperty("user.authToken").isEmpty()) {
                userAuthToken = getProperty("user.authToken");
            }else {
                userAuthToken = Config.USER_AUTH_TOKEN;
            }

            if (getProperty("user.teamUserId") != null && !getProperty("user.teamUserId").isEmpty()) {
                userteamUserId = getProperty("user.teamUserId");
            }else {
                userteamUserId = Config.USER_TEAM_USERID;
            }

            if (getProperty("user.teamId") != null && !getProperty("user.teamId").isEmpty()) {
                userteamId = getProperty("user.teamId");
            }else {
                userteamId = Config.USER_TEAMID;
            }


            RestAssured.baseURI = serverUrl;
            RequestSpecBuilder builder = new RequestSpecBuilder();
            builder.addHeader(serverStaticToken, userAuthToken);
            builder.setContentType(ContentType.JSON);
            RestAssured.requestSpecification = builder.build();
            RestAssured.replaceFiltersWith(new MyFilter());


    }

    public void afterSuite() {

    }
}
