package com.rest.autotests.core.asserts;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rest.autotests.core.objects.BasicObject;

import java.util.ArrayList;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created by Andrej Skeledzija 2017
 */
public class Assert {

    public static void assertListHasItem(ArrayList list, BasicObject item) {
        boolean listContainsItem = false;
        for (Object basicObject : list) {
            if (((BasicObject) basicObject).getId() == item.getId()) {
                listContainsItem = true;
            }
        }
        assertTrue(listContainsItem, "List doesn't contain item");
    }

    public static void assertListNoItem(ArrayList list, BasicObject item) {
        boolean listContainsItem = false;
        for (Object basicObject : list) {
            if (((BasicObject) basicObject).getId() == item.getId()) {
                listContainsItem = true;
            }
        }
        assertFalse(listContainsItem, "List doesn't contain item");
    }

    public static void assertListItemsHasExactField(ArrayList list, String field, String value) {
        for (Object basicObject : list) {
            JsonObject jobj = new Gson().fromJson(((BasicObject) basicObject).toJson(), JsonObject.class);
            if (value.contains("[")) {
                assertEquals(jobj.get(field).getAsJsonArray().toString(), value);
            } else {
                assertEquals(jobj.get(field).getAsString(), value);
            }
        }
    }

    public static void assertListItemsContainsField(ArrayList list, String field, String value) {
        for (Object basicObject : list) {
            JsonObject jobj = new Gson().fromJson(((BasicObject) basicObject).toJson(), JsonObject.class);
            if (value.contains("[")) {
                assertObjectContainsField((BasicObject) basicObject, field, value);
            } else if (field.equals("id")) {
                assertEquals(((BasicObject) basicObject).getId() + "", value);
            } else {
                if (jobj.get(field).isJsonArray()) {
                    assertObjectContainsField((BasicObject) basicObject, field, value);
                } else {
                    assertEquals(jobj.get(field).getAsString(), value);
                }
            }
        }
    }

    public static void assertAnyListItemsContainsField(ArrayList list, String field, String value) throws Exception {
        for (Object basicObject : list) {
            JsonObject jobj = new Gson().fromJson(((BasicObject) basicObject).toJson(), JsonObject.class);
            if (jobj.get(field).getAsString().equals(value)){
                return;
            }
        }
        throw new Exception();
    }

    public static void assertListItemsNotContainsField(ArrayList list, String field, String value) {
        for (Object basicObject : list) {
            JsonObject jobj = new Gson().fromJson(((BasicObject) basicObject).toJson(), JsonObject.class);
            if (value.contains("[")) {
                assertObjectNotContainsField((BasicObject) basicObject, field, value);
            } else if (field.equals("id")) {
                assertNotEquals(((BasicObject) basicObject).getId() + "", value);
            } else {
                if (jobj.get(field).isJsonArray()) {
                    assertObjectNotContainsField((BasicObject) basicObject, field, value);
                } else {
                    assertNotEquals(jobj.get(field).getAsString(), value);
                }
            }
        }
    }

    public static void assertObjectEqualsField(BasicObject basicObject, String field, String value) {
        JsonObject jobj = new Gson().fromJson(basicObject.toJson(), JsonObject.class);
        if (value.contains("[") || value.contains("{")) {
            try {
                assertEquals(jobj.get(field).getAsJsonArray().toString(), value);
            } catch (Exception e) {
                assertEquals(toJson(jobj.get(field).toString()), value, "Problem with " + jobj.get(field));
            }
        } else {
            try {
                assertEquals(jobj.get(field).getAsString(), value);
            } catch (Exception e) {
                assertEquals(jobj.get(field), value);
            }
        }
    }

    private static String toJson(String jsonString){
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        jsonObject.remove("endpoint");
        jsonObject.remove("id");
        return jsonObject.toString();
    }

    public static void assertObjectContainsField(BasicObject basicObject, String field, String value) {
        JsonObject jobj = new Gson().fromJson(basicObject.toJson(), JsonObject.class);
        boolean isContain = false;
        try {
            for (JsonElement jsonElement : jobj.get(field).getAsJsonArray()) {
                if (jsonElement.toString().equals(value)) {
                    isContain = true;
                }
            }
        } catch (Exception e) {
            if (jobj.get(field).getAsString().equals(value)) {
                isContain = true;
            }
        }

        assertTrue(isContain);
    }

    public static void assertObjectNotContainsField(BasicObject basicObject, String field, String value) {
        JsonObject jobj = new Gson().fromJson(basicObject.toJson(), JsonObject.class);
        boolean isContain = false;
        try {
            for (JsonElement jsonElement : jobj.get(field).getAsJsonArray()) {
                if (jsonElement.toString().equals(value)) {
                    isContain = true;
                }
            }
        } catch (Exception e) {
            if (jobj.get(field).getAsString().equals(value)) {
                isContain = true;
            }
        }

        assertFalse(isContain);
    }

    public static void assertObjectsJsonNotEqualsExceptField(BasicObject basicObject1, BasicObject basicObject2, String... field) {
        JsonObject jobj1 = new Gson().fromJson(basicObject1.toJson(), JsonObject.class);
        JsonObject jobj2 = new Gson().fromJson(basicObject2.toJson(), JsonObject.class);
        jobj1.remove("__type");
        jobj2.remove("__type");

        for (int i = 0; i < field.length; i++) {
            jobj1.remove(field[i]);
            jobj2.remove(field[i]);
        }
        for (Map.Entry entry : jobj1.entrySet()) {
            assertNotEquals(((JsonElement) entry.getValue()), jobj2.get((String) entry.getKey()), "Problem is in " + entry);
        }
    }

    public static void assertObjectsJsonsEqualsExceptFields(JsonObject jobj1, JsonObject jobj2, String... field) {
        jobj1.remove("__type");
        jobj2.remove("__type");
        for (int i = 0; i < field.length; i++) {
            jobj1.remove(field[i]);
            jobj2.remove(field[i]);
        }
        for (Map.Entry entry : jobj1.entrySet()) {
            assertEquals(((JsonElement) entry.getValue()).toString(), jobj2.get((String) entry.getKey()).toString());
        }
    }

    public static void assertObjectsJsonEquals(BasicObject basicObject1, BasicObject basicObject2) {
        JsonObject jobj1 = new Gson().fromJson(basicObject1.toJson(), JsonObject.class);
        JsonObject jobj2 = new Gson().fromJson(basicObject2.toJson(), JsonObject.class);
        jobj1.remove("createdTime");
        jobj1.remove("modifiedTime");
        jobj2.remove("createdTime");
        jobj2.remove("modifiedTime");

        assertEquals(jobj1, jobj2);
    }

    public static void assertObjectsJsonEqualsExceptField(BasicObject basicObject1, BasicObject basicObject2, String... field) {
        JsonObject jobj1 = new Gson().fromJson(basicObject1.toJson(), JsonObject.class);
        JsonObject jobj2 = new Gson().fromJson(basicObject2.toJson(), JsonObject.class);
        jobj1.remove("createdTime");
        jobj1.remove("modifiedTime");
        jobj2.remove("createdTime");
        jobj2.remove("modifiedTime");

        for (int i = 0; i < field.length; i++) {
            jobj1.remove(field[i]);
            jobj2.remove(field[i]);
        }
        for (Map.Entry entry : jobj1.entrySet()) {
            assertEquals(((JsonElement) entry.getValue()), jobj2.get((String) entry.getKey()));
        }
    }
}
