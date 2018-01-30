package com.rest.autotests.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Andrej Skeledzija 2017
 */
public class Config {

    //Logger
    private static final Logger logger = LogManager.getLogger(Config.class);

    private static final Properties PROPS = new Properties();

    static {
        try {
            PROPS.load(Config.class.getResourceAsStream("/config-main.properties"));
            String configName = System.getProperty("configName");
            if (configName != null) {
                PROPS.load(Config.class.getResourceAsStream("/config-" + configName + ".properties"));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static final String SERVER_IP = get("server.ip");
    public static final String SERVER_PORT = get("server.port");
    public static final String SERVER_API_VERSION = get("server.api.vesion");

    public static final String ACCESSKEY_KEY = get("server.accesskey.key");
    public static final String ACCESS_KEY_VALUE = get("server.accesskey.value1");
    public static final String ACCESSKEY_VALUE2 = get("server.accesskey.value2");

    public static final String MACKEY_KEY = get("server.mackey.key");


    public static final String SERVER_URL = get("server.url");
    public static final String SERVER_STATIC_TOKEN = get("server.staticToken");
    public static final String USER_EMAIL = get("user.email");
    public static final String USER_PASSWORD = get("user.password");
    public static final String USER_AUTH_TOKEN = get("user.authToken");
    public static final String USER_TEAM_USERID = get("user.teamUserId");
    public static final String USER_TEAMID = get("user.teamId");



    public static String get(String key) {
        if (!PROPS.containsKey(key)) {
            throw new IllegalStateException("Required configuration property " + key + " is not found.");
        }

        return PROPS.getProperty(key);
    }
}
