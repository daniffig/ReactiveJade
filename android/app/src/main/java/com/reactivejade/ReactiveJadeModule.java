// ReactiveJadeModule.java

package com.reactivejade;

import java.util.Map;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import reactivejade.ReactiveJadeEvent;
import reactivejade.ReactiveJadeEventListener;

public class ReactiveJadeModule extends ReactContextBaseJavaModule implements ReactiveJadeEventListener {

  public ReactiveJadeModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "ReactiveJade";
  }

  @Override
  public void reactiveJadeEventReceived(ReactiveJadeEvent event) {

    WritableMap params = Arguments.createMap();

    // Mapping based on https://stackoverflow.com/questions/36289315/how-can-i-pass-a-hashmap-to-a-react-native-android-callback
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
  }

  // OLD


  @Override
  public void sendFakeString(String string) {
    this.sendFakeEvent(string);
  }

  public void sendFakeEvent(String text) {
    getReactApplicationContext()
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit("log", text);
  }

}
