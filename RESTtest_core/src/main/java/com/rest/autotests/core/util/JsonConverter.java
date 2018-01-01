package com.rest.autotests.core.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.rest.autotests.core.objects.BasicObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrej Skeledzija 2017
 */
public class JsonConverter {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();


    public static final <T extends BasicObject> List<T> toObjectListFromFile(final File file) throws IOException {
        List<String> objectsAsString = FileUtils.readLines(file);
        Pattern pattern = Pattern.compile("\\{[^\\{\\}]*\\}");
        Matcher matcher = pattern.matcher(Joiner.on("").join(objectsAsString));
        objectsAsString.clear();
        while (matcher.find()){
            objectsAsString.add(matcher.group());
        }
        List<T> result = new ArrayList<>();
        objectsAsString.forEach(object -> {
            try {
                Class<?> clazz = Class.forName(getClassNameAsString(object));
                result.add(gson.fromJson(object, (Type) clazz));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    private static String getClassNameAsString(String object) throws IOException {
        Matcher matcher;
        matcher = Pattern.compile("\"__type\":\\s*?\"\\w*\"").matcher(object);
        matcher.find();
        String className = matcher.group().replaceAll("(.*type)|([^\\p{Upper}\\w])", "");
        String objectAbsolutePath = ((File) FileUtils.listFiles(new File("./../COBEtest_core"),
                new RegexFileFilter(className+".java"),
                DirectoryFileFilter.DIRECTORY).toArray()[0]).getAbsoluteFile().getCanonicalPath();
        matcher = Pattern.compile("com.*").matcher(objectAbsolutePath);
        matcher.find();
        return matcher.group().replaceAll("\\\\", ".").replaceAll(".java", "").replaceAll("/", ".");
    }


    public static <T extends BasicObject> String toJson(T object, String... omittedFields) {
        JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
        for (String str : omittedFields) {
            jsonObject.remove(str);
        }
        return jsonObject.toString();
    }

    public static <T extends BasicObject> String toJson(List<T> objects) {
        List<String> jsonList = Lists.transform(objects, object -> gson.toJsonTree(object).getAsJsonObject().toString());
        String jsonAsString = Joiner.on(",").join(jsonList);
        return "["+jsonAsString+"]";
    }
}
