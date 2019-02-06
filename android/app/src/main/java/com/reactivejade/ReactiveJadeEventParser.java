package reactivejade;

import java.util.Map;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import reactivejade.ReactiveJadeEvent;

public class ReactiveJadeEventParser {
  
  // https://stackoverflow.com/questions/36289315/how-can-i-pass-a-hashmap-to-a-react-native-android-callback
  public static WritableMap parseParams(ReactiveJadeEvent event) {

    WritableMap params = Arguments.createMap();

    if (event.hasParams()) {
      for (Map.Entry<String, Object> entry : event.getParams().entrySet()) {
        switch (entry.getValue().getClass().getName()) {
          case "java.lang.Boolean":
            params.putBoolean(entry.getKey(), (Boolean) entry.getValue());
            break;
          case "java.lang.Integer":
            params.putInt(entry.getKey(), (Integer) entry.getValue());
            break;
          case "java.lang.Double":
            params.putDouble(entry.getKey(), (Double) entry.getValue());
            break;
          case "java.lang.String":
            params.putString(entry.getKey(), (String) entry.getValue());
            break;
        }
      }
    }

    return params;
  }
}