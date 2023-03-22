package search.support;

import com.google.gson.Gson;
import java.lang.reflect.Type;

public class GsonHelper {

    private static final Gson GSON = new Gson();

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
