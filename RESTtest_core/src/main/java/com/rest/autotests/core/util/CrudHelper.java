package com.rest.autotests.core.util;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.rest.autotests.core.asserts.Assert;
import com.rest.autotests.core.objects.BasicObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by Andrej Skeledzija 2017
 */
public class CrudHelper {


    public static final int THREADS_NUMBER = 100;
    public static final int THREAD_TIMEOUT = 60 * 60 * 24;
    private static final Log log;

    static {
        log = LogFactory.getLog(CrudHelper.class);

    }

    public static Long createObjectAndReturnId(BasicObject restObject) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        Response response = given().body(restObject).when().post(restObject.endpoint);
        String id = response.body().asString();
        return Long.valueOf(id);
    }

    public static BasicObject createObject(BasicObject restObject) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        Response response = given().body(restObject).when().post(restObject.endpoint);
        String id = response.body().asString();
        System.out.println(id);
        return readSingleObject(restObject.getClass(), new JsonParser().parse(id).getAsJsonObject().get("id").getAsString());
    }

    public static BasicObject createObjectAppUserId(BasicObject restObject) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        Response response = given().body(restObject).when().post(restObject.endpoint);
        String id = response.body().asString();
        System.out.println(id);
        return readSingleObject(restObject.getClass(), new JsonParser().parse(id).getAsJsonObject().get("appUserId").getAsString());
    }

    public static BasicObject createObjectWithId(BasicObject restObject) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        Response response = given().body(restObject).when().post(restObject.endpoint);
        String id = response.body().asString();
        return readSingleObject(restObject.getClass(), id);
    }

    public static void createObjectNoResult(BasicObject restObject) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        given().body(restObject).when().post(restObject.endpoint);
    }

    public static Response createObjectAndReturnResponse(BasicObject restObject) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        return given().body(restObject).when().post(restObject.endpoint);
    }


    public static void commitMacChanges(long id) {
        when().post("/mac/" + id + "/commit").then().assertThat().statusCode(200);
    }

    public static void cancelMacChanges(long id) {
        when().post("/mac/" + id + "/cancel").then().assertThat().statusCode(200);
    }

    public static void cancelMacChanges(long id, long objectId) {
        when().post("/mac/" + id + "/cancel/" + objectId).then().assertThat().statusCode(200);
    }

    public static BasicObject createObjectWithParams(BasicObject restObject, String params) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        Response response = given().body(restObject).when().post(restObject.endpoint + params);
        String id = response.body().asString();
        return readSingleObject(restObject.getClass(), id);
    }

    public static BasicObject createObject(BasicObject restObject, Header header) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        Response response = given().body(restObject).header(header).when().post(restObject.endpoint);
        String id = response.body().asString();
        return readSingleObject(restObject.getClass(), id, header);
    }

    public static BasicObject createObjectByPut(Class restObjectClass, BasicObject restObject) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        return (BasicObject) given().body(restObject).when().put(restObject.endpoint).as(restObjectClass);
    }

    public static BasicObject createObjectByPut(Class restObjectClass, BasicObject restObject, Header header) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        return (BasicObject) given().header(header).body(restObject).when().put(restObject.endpoint + "/" + restObject.getId()).as(restObjectClass);
    }

    public static void createObjectWithId(BasicObject restObject, String id) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        given().body(restObject).when().post(restObject.endpoint + "/" + id).then().assertThat().statusCode(405).
                assertThat().body(equalTo("{\"message\":\"The requested resource does not support http method 'POST'.\"}"));
    }

    public static BasicObject createObjectWithEmptyField(BasicObject restObject, String... omittedFields) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        Response response = given().body(restObject.toJson(omittedFields)).when().post(restObject.endpoint);
        String id = response.body().asString();
        return readSingleObject(restObject.getClass(), id);
    }

    public static void createObjectWithInvalidField(BasicObject restObject, String field, String value) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        JsonObject jsonObject = new Gson().toJsonTree(restObject).getAsJsonObject();
        jsonObject.addProperty(field, value);
        jsonObject.remove("endpoint");
        jsonObject.remove("id");
        //log.info("Creating an object " + jsonObject);
        given().body(jsonObject).when().post(restObject.endpoint).then().statusCode(400);
    }

    public static void createObjectWithInvalidField(BasicObject restObject, String field, String value, String params) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        JsonObject jsonObject = new Gson().toJsonTree(restObject).getAsJsonObject();
        jsonObject.addProperty(field, value);
        jsonObject.remove("endpoint");
        jsonObject.remove("id");
        given().body(jsonObject).when().post(restObject.endpoint + params).then().statusCode(400);
    }

    public static void createInvalidObject(BasicObject restObject) {
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        JsonObject jsonObject = new Gson().toJsonTree(restObject).getAsJsonObject();
        jsonObject.remove("endpoint");
        jsonObject.remove("id");
        given().body(jsonObject).when().post(restObject.endpoint).then().statusCode(400);
    }

    public static BasicObject createObjectFromItsTypeById(BasicObject typeObject, BasicObject object) {
        //log.info("Creating typeObject ---> " + typeObject.toJson());
        //log.info("Creating an object  --->" + object.toJson());
        return (BasicObject) given().body(object).when().post(typeObject.endpoint + "/create-instance/byid/" + typeObject.getId()).as(object.getClass());
    }

    public static BasicObject createObjectFromItsTypeByName(BasicObject typeObject, BasicObject object, String name) {
        return (BasicObject) given().body(object).when().post(typeObject.endpoint + "/create-instance/byname/" + name).as(object.getClass());
    }

    public static void createObjectFromItsTypeByIdWithInvalidId(BasicObject typeObject) {
        given().when().post(typeObject.endpoint + "/create-instance/byid/123").then().assertThat().statusCode(404);
    }

    public static void createObjectFromItsTypeByNameWithInvalidName(BasicObject typeObject) {
        given().when().post(typeObject.endpoint + "/create-instance/byname/testestest").then().assertThat().statusCode(404);
    }

    public static BasicObject createObjectWithExtraField(BasicObject restObject, String field, String value) {
        JsonObject jsonObject = new Gson().toJsonTree(restObject).getAsJsonObject();
        jsonObject.addProperty(field, value);
        jsonObject.remove("endpoint");
        jsonObject.remove("id");
        System.out.println("ENDPOINT: " + restObject.endpoint );
        System.out.println("REQUEST: " + restObject.toJson());
        Response response = given().body(jsonObject).when().post(restObject.endpoint);
        String id = response.body().asString();
        return readSingleObject(restObject.getClass(), id);
    }

    public static void createObjectWithEmptyBody(Class restObjectClass, String body) {
        //log.info("Creating an object with empty body");
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }
        given().body(body).when().post(((BasicObject) result).endpoint).then().statusCode(400);
    }

    public static String createObjectWithInvalidBody(Class restObjectClass, String body) {
        //log.info("Creating an object with invalid body");
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        Response response = given().body(body).when().post(((BasicObject) result).endpoint);
        String res = "";
        if (response.asString().contains("id")) {
            res = new JsonParser().parse(response.asString()).getAsJsonObject().get("id").getAsString();
        } else {
            res = response.asString();
        }
        return res;
    }

    public static String createObjectWithInvalidBody(Class restObjectClass, String body, String params) {
        //log.info("Creating an object with invalid body");
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        Response response = given().body(body).when().post(((BasicObject) result).endpoint + params);
        return response.body().asString();
    }

    public static void createManyObjects(Class restObjectClass, int number) {
        Object object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        int count = (from(get(((BasicObject) object).endpoint).asString()).get("count"));
        if (number > count) {
            //log.info("Creating " + 5 + " objects");
            for (int i = 0; i <= number - count; i++) {
                createObject((BasicObject) object);
            }
        }
    }

    public static void createManyObjectsMultiThread(BasicObject basicObject, int number) throws InterruptedException {

        System.out.println("Number of items: " + number);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < number; i++) {
            executor.submit(() -> {
                        createObjectNoResult(basicObject);
                    }
            );
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Created successfully!");
    }

    public static <T extends BasicObject> List<Long> createManyObjectsMultiThread(List<T> objects) throws InterruptedException {

        System.out.println("Number of items: " + objects.size());
        System.out.println("Current time " + System.currentTimeMillis());
        ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        Collection<Callable<Long>> tasks = new ArrayList<>(Lists.transform(objects,
                object -> () -> {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    return createObjectAndReturnId(object);
                }));
        List<Future<Long>> results = executor.invokeAll(tasks, THREAD_TIMEOUT, TimeUnit.SECONDS);
        System.out.println("Created successfully!");

        List<Long> resultList = new ArrayList<>(Lists.transform(results, basicObjectFuture -> {
            Long result = null;
            try {
                result = basicObjectFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return result;
        }));
        return resultList;
    }

    public static List<BasicObject> createManyObjects(BasicObject basicObject, int number) {
        List<BasicObject> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            result.add(createObject(basicObject));
        }
        return result;
    }


    public static BasicObject readSingleObject(Class restObjectClass, String id) {
        //log.info("Reading single object with id " + id);
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        result = get(((BasicObject) result).endpoint + "/" + id).as(restObjectClass);
        //log.info("Recieved object is " + result.toString());
        return (BasicObject) result;
    }

    public static BasicObject readSingleObject(BasicObject object, String id) {
        return (BasicObject) get(object.endpoint + "/" + id).as(object.getClass());
    }

    public static BasicObject readSingleObject(BasicObject object, String id, Header header) {
        return (BasicObject) given().header(header).get(object.endpoint + "/" + id).as(object.getClass());
    }

    public static BasicObject readSingleObject(Class restObjectClass, String id, Header header) {
        //log.info("Reading single object with id " + id);
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        result = given().header(header).get(((BasicObject) result).endpoint + "/" + id).as(restObjectClass);
        //log.info("Recieved object is " + result.toString());
        return (BasicObject) result;
    }

    public static void readEmptyObject(Class restObjectClass, String id) {
        //log.info("Reading empty object with id " + id);
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        get(((BasicObject) result).endpoint + "/" + id).then().statusCode(404).assertThat().body(containsString("is not found"));
    }

    public static void readEmptyObject(String endpoint, String id) {
        //log.info("Reading empty object with id " + id);
        Object result = null;

        get(endpoint + "/" + id).then().statusCode(404).assertThat().body(equalTo(""));
    }

    public static void readEmptyObject(BasicObject object) {
        get(object.endpoint + "/" + object.getId()).then().statusCode(404);
    }

    public static void readEmptyObject(BasicObject object, Header header) {
        given().header(header).get(object.endpoint + "/" + object.getId()).then().statusCode(404);
    }

    public static void readEmptyObjectWithNull(Class restObjectClass, String id) {
        //log.info("Reading empty object with id " + id);
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        get(((BasicObject) result).endpoint + "/" + id).then().statusCode(200).assertThat().body(equalTo("null"));
    }

    public static ArrayList<BasicObject> readAllObjects(Class restObjectClass) {
        //log.info("Reading all objects");
        ArrayList<BasicObject> arrayList = new ArrayList<BasicObject>();
        Object object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        String response = get(((BasicObject) object).endpoint).asString();
        for (long id : (ArrayList<Long>) from(response).get("result.id")) {
            arrayList.add(readSingleObject(restObjectClass, Long.toString(id)));
        }
        return arrayList;
    }

    public static <T extends BasicObject> List<T> fetchAllObjects(Class<T> restObjectClass) {
        ArrayList<T> result = new ArrayList<>();
        T object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = (T) cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }
        String response = get(object.endpoint).asString();
        result.addAll(((ArrayList<Long>) from(response).get("result.id")).stream().map(
                id -> (T) readSingleObject(restObjectClass, Long.toString(id))).collect(Collectors.toList()));
        return result;
    }


    public static ArrayList<BasicObject> readAllObjects(BasicObject basicObject) {
        //log.info("Reading all objects");
        ArrayList<BasicObject> arrayList = new ArrayList<BasicObject>();

        String response = get(basicObject.endpoint).asString();
        for (long id : (ArrayList<Long>) from(response).get("result.id")) {
            arrayList.add(readSingleObject(basicObject, Long.toString(id)));
        }
        return arrayList;
    }

    public static Object readJsonObject(Class restObjectClass, Class classOfT) {
        //log.info("Reading all objects");
        BasicObject result;
        Object object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        return new Gson().fromJson(get(((BasicObject) object).endpoint).asString(), classOfT);
    }

    public static ArrayList<BasicObject> readAllObjectsWithPagination(Class restObjectClass, String
            pageSize, String startIndex) {
        //log.info("Reading all objects with pagination");
        ArrayList<BasicObject> arrayList = new ArrayList<BasicObject>();
        Object object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        for (long id : (ArrayList<Long>) from(get(((BasicObject) object).endpoint + "?pageSize=" + pageSize + "&pageStartIndex=" + startIndex).asString()).get("result.id")) {
            arrayList.add(readSingleObject(restObjectClass, Long.toString(id)));
        }
        return arrayList;
    }

    public static void readAllObjectsWithInvalidPagination(Class restObjectClass, String pageSize, String startIndex) {
        //log.info("Reading all objects with pagination");
        ArrayList<BasicObject> arrayList = new ArrayList<BasicObject>();
        Object object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        get(((BasicObject) object).endpoint + "?pageSize=" + pageSize + "&pageStartIndex=" + startIndex).then().statusCode(404);
    }

    public static ArrayList<BasicObject> readAllObjectsWithFilterAndPagination(Class restObjectClass, String filter, String pageSize, String startIndex) {
        //log.info("Reading all objects");
        ArrayList<BasicObject> arrayList = new ArrayList<BasicObject>();
        Object object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        for (long id : (ArrayList<Long>) from(get(((BasicObject) object).endpoint + "?" + filter + "&pageSize=" + pageSize + "&pageStartIndex=" + startIndex).asString()).get("result.id")) {
            arrayList.add(readSingleObject(restObjectClass, Long.toString(id)));
        }
        return arrayList;
    }

    public static ArrayList<BasicObject> readAllObjectsWithFilter(Class restObjectClass, String filter) {
        //log.info("Reading all objects");
        ArrayList<BasicObject> arrayList = new ArrayList<BasicObject>();
        Object object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        for (long id : (ArrayList<Long>) from(get(((BasicObject) object).endpoint + "?" + filter).asString()).get("result.id")) {
            arrayList.add(readSingleObject(restObjectClass, Long.toString(id)));
        }
        return arrayList;
    }

    public static void readAllObjectsWithWrongFilter(Class restObjectClass, String filter) {
        Object object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        get(((BasicObject) object).endpoint + "?" + filter).then().statusCode(400);
    }

    public static void readAllObjectsWithInvalidFilter(Class restObjectClass, String filter) {
        //log.info("Reading all objects");
        ArrayList<BasicObject> arrayList = new ArrayList<BasicObject>();
        Object object = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            object = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        get(((BasicObject) object).endpoint + "?" + filter).then().statusCode(404);

    }

    public static BasicObject deleteSingleObject(Class restObjectClass, String id) {
        //log.info("Removing single object with id " + id);
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        return (BasicObject) delete(((BasicObject) result).endpoint + "/" + id).as(restObjectClass);
        /*Response response = delete(((BasicObject) result).endpoint + "/" + id);
        response.then().assertThat().statusCode(200);
        return (BasicObject) response.as(restObjectClass);*/
    }

    public static void deleteNotAllowedObject(BasicObject object) {
        delete(object.endpoint + "/" + object.getId()).then().assertThat().statusCode(400);
    }

    public static void deleteNotAllowedObject(BasicObject object, Header header) {
        given().header(header).delete(object.endpoint + "/" + object.getId()).then().assertThat().statusCode(400);
    }

    public static BasicObject deleteSingleObject(BasicObject object) {
        Response response = delete(object.endpoint + "/" + object.getId());
        response.then().assertThat().statusCode(200);
        return (BasicObject) response.as(object.getClass());
    }

    public static BasicObject deleteSingleObject(BasicObject object, Header header) {
        Response response = given().header(header).delete(object.endpoint + "/" + object.getId());
        response.then().assertThat().statusCode(200);
        return (BasicObject) response.as(object.getClass());
    }

    public static BasicObject deleteSingleObject(Class restObjectClass, String id, Header header) {
        //log.info("Removing single object with id " + id);
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        return (BasicObject) given().header(header).when().delete(((BasicObject) result).endpoint + "/" + id).as(restObjectClass);
    }

    public static void deleteNotAllowedObject(Class restObjectClass, String id) {
        //log.info("Removing single object with id " + id);
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        delete(((BasicObject) result).endpoint + "/" + id).then().statusCode(400).body(containsString("one or more strong references"));
    }

    public static void deleteEmptyObject(Class restObjectClass, String id) {
        //log.info("Removing single object with id " + id);
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        delete(((BasicObject) result).endpoint + "/" + id).then().statusCode(404);
    }

    public static void deleteAllObjects(Class restObjectClass) {
        //log.info("Removing all objects");
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        delete(((BasicObject) result).endpoint).then().statusCode(200);
    }

    public static void deleteAllObjectsNotAllowed(Class restObjectClass) {
        //log.info("Removing all objects");
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        delete(((BasicObject) result).endpoint).then().statusCode(405).assertThat().body(equalTo("{\"message\":\"The requested resource does not support http method 'DELETE'.\"}"));
    }

    public static BasicObject updateSingleFieldWithValueByPatch(Class restObjectClass, String id, String field, String value) {
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        JsonParser parser = new JsonParser();
        JsonObject jsonObject;
        if (value.contains("[") || value.contains("{")) {
            jsonObject = (JsonObject) parser.parse("{\"" + field + "\":" + value + "}");
        } else {
            jsonObject = (JsonObject) parser.parse("{\"" + field + "\":\"" + value + "\"}");
        }
        //log.info("Creating an object " + jsonObject);
        Response response = given().body(jsonObject).when().patch(((BasicObject) result).endpoint + "/" + id);
        return (BasicObject) getObjectAndPropagateError(response, restObjectClass);
    }

    public static BasicObject updateSingleFieldWithValueByPatch(Class restObjectClass, String id, String field, String value, Header header) {
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        JsonParser parser = new JsonParser();
        JsonObject jsonObject;
        if (value.contains("[") || value.contains("{")) {
            jsonObject = (JsonObject) parser.parse("{\"" + field + "\":" + value + "}");
        } else {
            jsonObject = (JsonObject) parser.parse("{\"" + field + "\":\"" + value + "\"}");
        }
        //log.info("Creating an object " + jsonObject);
        result = given().body(jsonObject).header(header).when().patch(((BasicObject) result).endpoint + "/" + id).as(restObjectClass);

        return (BasicObject) result;
    }

    public static BasicObject updateSingleFieldWithValueByPut(Class restObjectClass, String id, String field, String value) {
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }
        JsonParser parser = new JsonParser();
        JsonObject jsonObject;
        if (value.contains("[")) {
            jsonObject = (JsonObject) parser.parse("{\"" + field + "\":" + value + "}");
        } else {
            jsonObject = (JsonObject) parser.parse("{" + field + ":" + value + "}");
        }
        jsonObject.addProperty("id", id);
        Response response = given().body(jsonObject).when().put(((BasicObject) result).endpoint + "/" + id);
        return (BasicObject) getObjectAndPropagateError(response, restObjectClass);
    }

    public static BasicObject updateSingleFieldWithValueByPut(Class restObjectClass, String id, String field, String value, String mandatoryField, long[] mandatoryValue) {
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }
        JsonParser parser = new JsonParser();
        JsonObject jsonObject;
        if (value.contains("[")) {
            jsonObject = (JsonObject) parser.parse("{\"" + field + "\":" + value + "}");
        } else {
            jsonObject = (JsonObject) parser.parse("{" + field + ":" + value + "}");
        }
        jsonObject.addProperty("id", id);
        jsonObject.add(mandatoryField, new Gson().toJsonTree(mandatoryValue));
        Response response = given().body(jsonObject).when().put(((BasicObject) result).endpoint + "/" + id);
        return (BasicObject) getObjectAndPropagateError(response, restObjectClass);
    }

    private static Object getObjectAndPropagateError(Response response, Class restObjectClass) {
        Object result = null;
        try {
            result = response.as(restObjectClass);
        } catch (IllegalStateException e) {
            throw new RuntimeException(response.asString(), e);

        }
        return result;
    }

    public static BasicObject updateSingleFieldWithValueByPut(Class restObjectClass, String id, String field, String value, Header header) {
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }

        JsonParser parser = new JsonParser();
        JsonObject jsonObject;
        if (value.contains("[")) {
            jsonObject = (JsonObject) parser.parse("{\"" + field + "\":" + value + "}");
        } else {
            jsonObject = (JsonObject) parser.parse("{\"" + field + "\":\"" + value + "\"}");
        }
        jsonObject.addProperty("id", id);
        //log.info("Creating an object " + jsonObject);
        result = given().body(jsonObject).header(header).when().put(((BasicObject) result).endpoint + "/" + id).as(restObjectClass);

        return (BasicObject) result;
    }

    public static BasicObject updateAllFieldsWithValueByPut(String id, BasicObject basicObject) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(basicObject.toJson());
        jsonObject.addProperty("id", id);

        log.info("PUT ------>" + jsonObject);
        return given().body(jsonObject).when().put((basicObject).endpoint + "/" + id).as(basicObject.getClass());
    }

    public static Response updateAllFieldsWithValueByPutAndReturnResponse(String id, BasicObject basicObject) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(basicObject.toJson());
        jsonObject.addProperty("id", id);
        //log.info("Creating an object " + jsonObject);
        return given().body(jsonObject).when().put((basicObject).endpoint + "/" + id);
    }

    public static BasicObject updateAllFieldsWithValueByPut(String id, BasicObject basicObject, Header header) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(basicObject.toJson());
        jsonObject.addProperty("id", id);
        //log.info("Creating an object " + jsonObject);
        return given().body(jsonObject).header(header).when().put((basicObject).endpoint + "/" + id).as(basicObject.getClass());
    }

    public static BasicObject updateAllFieldsWithValueByPatch(String id, BasicObject basicObject) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(basicObject.toJson());

        log.info("Creating an object " + jsonObject);
        return given().body(jsonObject).when().patch((basicObject).endpoint + "/" + id).as(basicObject.getClass());
    }

    public static Response updateAllFieldsWithValueByPatchAndReturnResponse(String id, BasicObject basicObject) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(basicObject.toJson());

        //log.info("Creating an object " + jsonObject);
        return given().body(jsonObject).when().patch((basicObject).endpoint + "/" + id);
    }

    public static BasicObject updateAllFieldsWithValueByPatch(String id, BasicObject basicObject, Header header) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(basicObject.toJson());

        //log.info("Creating an object " + jsonObject);
        return given().body(jsonObject).header(header).when().patch((basicObject).endpoint + "/" + id).as(basicObject.getClass());
    }

    public static void updateWithEmptyBodyByPatch(Class restObjectClass, String id) {
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }
        BasicObject basicObject = readSingleObject(restObjectClass, id);
        BasicObject basicObject1 = (BasicObject) given().body("{}").when().patch(((BasicObject) result).endpoint + "/" + id).as(restObjectClass);
        Assert.assertObjectsJsonEquals(basicObject, basicObject1);
    }

    public static void updateWithEmptyBodyByPut(Class restObjectClass, String id) {
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }
        given().body("").when().put(((BasicObject) result).endpoint + "/" + id).then().assertThat()
                .body(containsString("null or empty"));
    }

    public static void updateNotAllowedObject(BasicObject object) {
        given().body(object.toJson()).patch(object.endpoint + "/" + object.getId()).then().assertThat().statusCode(400);
    }

    public static void updateNotAllowedObject(BasicObject object, Header header) {
        given().body(object.toJson()).header(header).patch(object.endpoint + "/" + object.getId()).then().assertThat().statusCode(400);
    }

    public static String postDdr(Class restObjectClass, String body) {
        Object result = null;
        try {
            Class<?> clazz = Class.forName(restObjectClass.getName());
            Constructor<?> cons = clazz.getConstructor();
            result = cons.newInstance();
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
        }
        //log.info("POST ddr" + ((BasicObject) result).endpoint);
        //log.info(body);
        Response response = given().body(body).post("ddr/" + ((BasicObject) result).endpoint);
//        return (DdrImportResult<Contact>) response.body().as(DdrImportResult.class);
        return response.body().asString();
    }

    public static String postDdr(BasicObject basicObject, String body) {
        Response response = given().body(body).post("ddr" + basicObject.endpoint);
//        return (DdrImportResult<Contact>) response.body().as(DdrImportResult.class);
        return response.body().asString();
    }


    public static void getDdrStatusOnCancel(String id) {
        get("ddr/" + id + "/status").then().assertThat().body(equalTo("\"DDR Import is not found\""));
    }

    public static void commitDdr(String id) {
        post("ddr/" + id + "/commit");
    }

    public static String downloadDdrResult(String id) {
        return get("ddr/" + id + "/download-results").asString();
    }

    public static void cancelDdr(String id) {
        post("ddr/" + id + "/cancel");
    }
}
