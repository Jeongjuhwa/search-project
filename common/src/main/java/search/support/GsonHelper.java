package search.support;

import com.google.gson.Gson;
import java.lang.reflect.Type;

public class GsonHelper {

    private static final Gson GSON = new Gson();


    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static String toJson(Object obj, Type type) {
        return GSON.toJson(obj, type);
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        return GSON.fromJson(json, clz);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
