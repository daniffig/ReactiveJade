// AgentModule.java

package com.reactivejade;

import reactivejade.*;
import hardwaresniffer.*;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.facebook.react.bridge.Callback;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import jade.android.MicroRuntimeService;
import jade.android.MicroRuntimeServiceBinder;
import jade.core.MicroRuntime;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentModule extends ReactContextBaseJavaModule implements ReactiveJadeEventListener {

  private final static Logger logger = Logger.getLogger("com.reactivejade.AgentModule");
  
  private AgentContainer agentContainer;
  private AgentController agentController;

  public AgentModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "AgentComponent";
  }

  @ReactMethod
  public void killAgent(
      Callback callback
  ) {

  }

  @ReactMethod
  public void stop(
      String agentName,
      Callback errorCallback,
      Callback successCallback
  ) {
    if (this.agentContainer == null) {
      errorCallback.invoke("agentContainer is null");
    } else {
      try {
        this.agentContainer.kill();
        this.agentContainer = null;
        this.agentController = null;

        errorCallback.invoke("container successfully stopped");
      } catch (Exception e) {
        errorCallback.invoke(e.getMessage());
      }
    }
  }

  private AgentContainer startContainer(
      String mainHost,
      String mainPort
  ) {
    Profile profile = new ProfileImpl();
    profile.setParameter(Profile.MAIN, Boolean.FALSE.toString());
    profile.setParameter(Profile.CONTAINER_NAME, "Xiaomi Mi 8");
    profile.setParameter(Profile.MAIN_HOST, mainHost);
    profile.setParameter(Profile.MAIN_PORT, mainPort);
    profile.setParameter(Profile.JVM, Profile.ANDROID);

    return Runtime.instance().createAgentContainer(profile);
  }

  @ReactMethod
  public void start(
      String mainHost,
      String mainPort,
      Callback errorCallback,
      Callback successCallback) {

    if (this.agentContainer == null) {
      this.agentContainer = this.startContainer(mainHost, mainPort);
    } else {
      errorCallback.invoke(this.agentContainer.getPlatformName());
    }


    if (this.agentController == null) {
      try {
        // this.agentController = this.agentContainer.createNewAgent(
        //   "myFirstAgent",
        //   MyFirstAgent.class.getName(),
        //   new Object[] {
        //     getReactApplicationContext()
        //   }
        // );

        this.agentController = this.agentContainer.createNewAgent(
          "hardwareSnifferAgent",
          HardwareSnifferAgent.class.getName(),
          new Object[] {
            this
          }
        );

        this.agentController.start();

        // successCallback.invoke(this.agentController.getName());
      } catch (Exception e) {
        errorCallback.invoke(e.getMessage());
      }
    }
  }

  @Override
  public void sendFakeString(String string) {
    this.sendFakeEvent(string);
  }

  public void sendFakeEvent(String text) {
    getReactApplicationContext()
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit("log", text);
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

    getReactApplicationContext()
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(event.getEventName(), params);;
  }

  @ReactMethod
  public void getContainers(
      Callback callback
  ) {
  }
}


