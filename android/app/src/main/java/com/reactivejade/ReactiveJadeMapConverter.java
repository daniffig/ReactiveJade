package reactivejade;

import java.util.Map;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import reactivejade.ReactiveJadeMap;

public class ReactiveJadeMapConverter {
  
  // https://stackoverflow.com/questions/36289315/how-can-i-pass-a-hashmap-to-a-react-native-android-callback
  public static WritableMap toWritableMap(ReactiveJadeMap reactiveJadeMap) {

    WritableMap map = Arguments.createMap();

    for (Map.Entry<String, Object> entry : reactiveJadeMap.entrySet()) {
      switch (entry.getValue().getClass().getName()) {
        case "java.lang.Boolean":
          map.putBoolean(entry.getKey(), (Boolean) entry.getValue());
          break;
        case "java.lang.Integer":
          map.putInt(entry.getKey(), (Integer) entry.getValue());
          break;
        case "java.lang.Double":
          map.putDouble(entry.getKey(), (Double) entry.getValue());
          break;
        case "java.lang.String":
          map.putString(entry.getKey(), (String) entry.getValue());
          break;
      }
    }

    return map;
  }
}