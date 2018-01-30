package com.rest.autotests.core.util;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.rest.autotests.core.objects.BasicObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

/**
 * Created by Andrej Skeledzija 2017
 */
public class Utils {

    //Logger
    private static final Logger logger = LogManager.getLogger(Utils.class);

    private Utils() {

    }

    public static double doubleRound(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static Object[][] getPercentOfTestData(Object[][] testData, int coveragePercent) {
        int resultLength = testData.length * coveragePercent / 100;
        Object[][] result = new Object[resultLength][];

        Random randomGenerator = new Random();
        for (int i = 0; i < resultLength; i++) {
            int index = randomGenerator.nextInt(testData.length);
            result[i] = testData[index];
        }

        return result;
    }

    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        if (s.equals(""))
            return isValidInteger;
        try {
            Integer.parseInt(s);
            isValidInteger = true;
        } catch (NumberFormatException ex) {
        }

        return isValidInteger;
    }

    public static boolean isIntegerList(List<String> list) {
        for (String s : list) {
            try {
                Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        return true;
    }

    public static JsonElement getFieldValue(BasicObject basicObject, String field) {
        return new Gson().fromJson(basicObject.toJson(), JsonObject.class).get(field);
    }

    public static BasicObject castObjectToAnother(BasicObject castToObject, String castFromObject) {
        return (BasicObject) new Gson().fromJson(castFromObject, castToObject.getClass());
    }

    public static List<Long> getIdsAsList(List<? extends BasicObject> objects){
        return Lists.transform(objects, basicObject -> basicObject.getId());
    }

    public static long [] getIdsAsArray(List<? extends BasicObject> objects){
        List<Long> idsAsList = getIdsAsList(objects);
        return ArrayUtils.toPrimitive(idsAsList.toArray(new Long[idsAsList.size()]));
    }

    public static long [] getIds(List<Long> idsAsList){
        return ArrayUtils.toPrimitive(idsAsList.toArray(new Long[idsAsList.size()]));
    }

    public static void switchUser(String accessKey){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader(Config.ACCESSKEY_KEY, accessKey);
        builder.setContentType(ContentType.JSON);
        RestAssured.requestSpecification = builder.build();
    }
}
