package xyz.akedia.android.moodleonmobile.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
/**
 * * Created by arnavkansal on 15/02/16.
 */
public class Utils {
    public static LinkedHashMap[] jsonArrayToHashMap(JSONArray jsonArray) throws JSONException {
        LinkedHashMap[] HashMapArray;
        HashMapArray = new LinkedHashMap[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            HashMapArray[i] = jsonObjectToHashMap(jsonArray.getJSONObject(i));
        }
        return HashMapArray;
    }
    public static LinkedHashMap<String,String> jsonObjectToHashMap(JSONObject jsonObject) throws JSONException {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        Iterator<String> keysItr = jsonObject.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            String value = jsonObject.getString(key);
            map.put(key, value);
        }
        return map;
    }

    public static int findinCoursesArray(Object value, LinkedHashMap[] Data) {
        for (int i = 0; i < Data.length; i++) {
            if(Data[i].get("code") == value) return i;
        }
        return -1;
    }
}
