package com.rest.autotests.core.util;

import com.rest.autotests.core.util.filters.MyFilter;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.System.getProperty;

/**
 * Created by Andrej Skeledzija 2017
 */
public class ConnConfig {

    //Logger
    private static final Logger logger = LogManager.getLogger(ConnConfig.class);

    private static volatile ConnConfig instance;
    private String serverIp;
    private String serverPort;
    private String apiVersion;
    private String accessKeyValue;
    private String accessKeyKey;
    private ContentType contentType;
    private MyFilter filter;

    //COBE
    private String serverUrl;
    private String serverStaticToken;
    private String userEmail;
    private String userPassword;
    private String userAuthToken;
    private String userteamUserId;
    private String userteamId;


    public static ConnConfig getInstance() {
        ConnConfig localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnConfig.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnConfig();
                }
            }
        }
        return localInstance;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public ConnConfig setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }


    public String getServerStaticToken() {
        return serverStaticToken;
    }

    public ConnConfig setServerStaticToken(String serverStaticToken) {
        this.serverStaticToken = serverStaticToken;
        return this;
    }



    public String getUserEmail() {
        return userEmail;
    }

    public ConnConfig setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }


    public String getUserPassword() {
        return userPassword;
    }

    public ConnConfig setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public String getUserAuthToken() {
        return userAuthToken;
    }

    public ConnConfig setUserAuthToken(String userAuthToken) {
        this.userAuthToken = userAuthToken;
        return this;
    }

    public String getUserteamUserId() {
        return userteamUserId;
    }

    public ConnConfig setUserteamUserId(String userteamUserId) {
        this.userteamUserId = userteamUserId;
        return this;
    }


    public String getUserteamId() {
        return userteamId;
    }

    public ConnConfig setUserteamId(String userteamId) {
        this.userteamId = userteamId;
        return this;
    }




    public String getServerIp() {
        return serverIp;
    }

    public ConnConfig setServerIp(String serverIp) {
        this.serverIp = serverIp;
        return this;
    }

    public String getServerPort() {
        return serverPort;
    }

    public ConnConfig setServerPort(String serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public ConnConfig setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public String getAccessKeyValue() {
        return accessKeyValue;
    }

    public ConnConfig setAccessKeyValue(String accessKeyValue) {
        this.accessKeyValue = accessKeyValue;
        return this;
    }

    public String getAccessKeyKey() {
        return accessKeyKey;
    }

    public ConnConfig setAccessKeyKey(String accessKeyKey) {
        this.accessKeyKey = accessKeyKey;
        return this;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public ConnConfig setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public MyFilter getFilter() {
        return filter;
    }

    public ConnConfig setFilter(MyFilter filter) {
        this.filter = filter;
        return this;
    }

    /**
     * Default method to use
     */
    public void configurateHTTP() {

        if (getProperty("server.ip") != null && !getProperty("server.ip").isEmpty()) {
            serverIp = getProperty("server.ip");
        }

        if (getProperty("server.port") != null && !getProperty("server.port").isEmpty()) {
            serverPort = getProperty("server.port");
        }

        if (getProperty("server.key") != null && !getProperty("server.key").isEmpty()) {
            accessKeyValue = getProperty("server.key");
        }

        RestAssured.baseURI = "http://" + serverIp;
        RestAssured.port = Integer.parseInt(serverPort);
        RestAssured.basePath = "/irm/rest/" + apiVersion;
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader(accessKeyKey, accessKeyValue);
        builder.setContentType(contentType);
        RestAssured.requestSpecification = builder.build();
        RestAssured.replaceFiltersWith(filter);
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                new ObjectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON)
        );
    }


    public void configurateHTTPS() {


        if (getProperty("server.url") != null && !getProperty("server.url").isEmpty()) {
            serverUrl = getProperty("server.url");
        }

        if (getProperty("server.staticToken") != null && !getProperty("server.staticToken").isEmpty()) {
            serverStaticToken = getProperty("server.staticToken");
        }

        if (getProperty("user.email") != null && !getProperty("user.email").isEmpty()) {
            userEmail = getProperty("user.email");
        }

        if (getProperty("user.password") != null && !getProperty("user.password").isEmpty()) {
            userPassword = getProperty("user.password");
        }

        if (getProperty("user.authToken") != null && !getProperty("user.authToken").isEmpty()) {
            userAuthToken = getProperty("user.authToken");
        }

        if (getProperty("user.teamUserId") != null && !getProperty("user.teamUserId").isEmpty()) {
            userteamUserId = getProperty("user.teamUserId");
        }

        if (getProperty("user.teamId") != null && !getProperty("user.teamId").isEmpty()) {
            userteamId = getProperty("user.teamId");
        }

        RestAssured.baseURI = "https://"+ serverUrl;
        RestAssured.port = Integer.parseInt(serverPort);
        RestAssured.basePath = apiVersion;
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Authorization",userAuthToken);
        builder.setContentType(contentType);
        RestAssured.requestSpecification = builder.build();
        RestAssured.replaceFiltersWith(filter);
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                new ObjectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON)
        );
    }


    /**
     * HTTP Defaults come From Config properties file
     */
    public ConnConfig setDefaultConfigurationHTTP() {
        this.setServerIp(Config.SERVER_IP).setServerPort(Config.SERVER_PORT).setApiVersion(Config.SERVER_API_VERSION)
                .setAccessKeyKey(Config.ACCESSKEY_KEY).setAccessKeyValue(Config.ACCESS_KEY_VALUE)
                .setContentType(ContentType.JSON).setFilter(new MyFilter());
        System.out.println(this.toString());
        return this;
    }



    /**
     * HTTPS Defaults come From Config properties file
     */
    public ConnConfig setDefaultConfigurationHTTPS() {
        this.setServerUrl(Config.SERVER_URL)
                .setServerPort(Config.SERVER_PORT)
                .setApiVersion(Config.SERVER_API_VERSION)
                .setServerStaticToken(Config.SERVER_STATIC_TOKEN)
                .setUserEmail(Config.USER_EMAIL)
                .setUserPassword(Config.USER_PASSWORD)
                .setUserAuthToken(Config.USER_AUTH_TOKEN)
                .setUserteamUserId(Config.USER_TEAM_USERID)
                .setUserteamId(Config.USER_TEAMID)
                .setContentType(ContentType.JSON)
                .setFilter(new MyFilter());
        System.out.println(this.toString());
        return this;
    }

    @Override
    public String toString() {
        return String.format(" URL: " + "%n " +
                " " + serverUrl + ":" + serverPort + apiVersion + "%n " +
                "HEADERS: " + "%n " +
                " Authorization = " + userAuthToken + "%n " +
                " Content-Type  = " + contentType + "%n " );
    }
}