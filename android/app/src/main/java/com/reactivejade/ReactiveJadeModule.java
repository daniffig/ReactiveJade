// ReactiveJadeModule.java

package com.reactivejade;

import java.util.List;
import java.util.UUID;

// https://developer.android.com/reference/android/util/Log.html
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

import reactivejade.ReactiveJadeEvent;
import reactivejade.ReactiveJadeMap;
import reactivejade.ReactiveJadeMapConverter;
import reactivejade.ReactiveJadeSubscribable;
import reactivejade.ReactiveJadeSubscriptionService;

import hardwaresniffer.HardwareSnifferAgent;
import hardwaresniffer.HardwareSnifferReport;

public class ReactiveJadeModule extends ReactContextBaseJavaModule implements ReactiveJadeSubscribable {

  private AgentContainer container;
  private AgentController agent;

  public ReactiveJadeModule(ReactApplicationContext reactContext) {
    super(reactContext);

    ReactiveJadeSubscriptionService.subscribe(HardwareSnifferAgent.class.getName(), this);
  }

  @Override
  public String getName() {
    return "ReactiveJade";
  }

  private String generateAgentName(String containerName) {
    return String.format(
      "HSAgent-%s-%s",
      containerName,
      UUID.randomUUID().toString().substring(0, 7)
    );
  }

  @ReactMethod
  public void startContainer(
      String containerName,
      String mainHost,
      String mainPort,
      Callback successCallback,
      Callback errorCallback
  ) {
    WritableMap params = Arguments.createMap();

    if (container == null) {
      Profile profile = new ProfileImpl();
      profile.setParameter(Profile.MAIN, Boolean.FALSE.toString());
      profile.setParameter(Profile.CONTAINER_NAME, containerName);
      profile.setParameter(Profile.MAIN_HOST, mainHost);
      profile.setParameter(Profile.MAIN_PORT, mainPort);
      profile.setParameter(Profile.JVM, Profile.ANDROID);

      container = Runtime.instance().createAgentContainer(profile);

      try {
        params.putString("assignedContainerName", container.getContainerName());
        params.putString("message", "container succesfully created with name " + container.getContainerName());

        successCallback.invoke(params);
      } catch (ControllerException e) {
        params.putString("message", e.getMessage());
  
        errorCallback.invoke(params);
      }
    } else {
      params.putString("message", "an error occurred while creating the container");

      errorCallback.invoke(params);
    }
  }

  @ReactMethod
  public void stopContainer(
      Callback successCallback,
      Callback errorCallback
  ) {
    WritableMap params = Arguments.createMap();

    if (container == null) {
      params.putString("message", "an error occurred while stopping the container");

      errorCallback.invoke(params);
    } else {
      try {
        container.kill();

        container = null;

        params.putString("message", "container succesfully stopped");

        successCallback.invoke(params);
      } catch (StaleProxyException e) {
        params.putString("message", e.getMessage());

        errorCallback.invoke(params);
      }
    }
  }

  @ReactMethod
  public void startAgent(
      String containerName,
      Callback successCallback,
      Callback errorCallback
  ) {
    WritableMap params = Arguments.createMap();

    if (agent == null) {
      try {
        agent = container.createNewAgent(
          generateAgentName(containerName),
          HardwareSnifferAgent.class.getName(),
          new Object[] {
            this
          }
        );

        agent.start();

        params.putString("assignedAgentName", agent.getName());
        params.putString("message", "agent succesfully started with name " + agent.getName());

        successCallback.invoke(params);
      } catch (StaleProxyException e) {
        params.putString("message", e.getMessage());

        errorCallback.invoke(params);
      }
    } else {
      params.putString("message", "an error occurred while starting the agent");

      errorCallback.invoke(params);
    }
  }

  @ReactMethod
  public void stopAgent(
    Callback successCallback,
    Callback errorCallback
  ) {
    WritableMap params = Arguments.createMap();

    if (agent == null) {
      params.putString("message", "an error occurred while stopping the agent");

      errorCallback.invoke(params);
    } else {
      try {
        agent.kill();

        agent = null;

        params.putString("message", "agent succesfully stopped");

        successCallback.invoke(params);
      } catch (StaleProxyException e) {
        params.putString("message", e.getMessage());

        errorCallback.invoke(params);
      }
    }
  }

  @Override
  public void receiveReactiveJadeEvent(ReactiveJadeEvent event) {
    switch (event.getEventName()) {
      case "log":
        notifyJSModule(event);
        break;
      case "reportList":
        notifyReportList(event.getParams());
        break;
    }
  }

  private void notifyJSModule(ReactiveJadeEvent event) {
    getReactApplicationContext()
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(event.getEventName(), ReactiveJadeMapConverter.toWritableMap(event.getParams()));
  }

  private void notifyReportList(ReactiveJadeMap reactiveJadeMap) {
    WritableMap params = Arguments.createMap();

    params.putString("containerName", (String) reactiveJadeMap.get("containerName"));
    params.putString("elapsedTime", (String) reactiveJadeMap.get("elapsedTime"));

    WritableArray array = Arguments.createArray();

    for (HardwareSnifferReport report : ((List<HardwareSnifferReport>) reactiveJadeMap.get("reportList"))) {
      array.pushMap(ReactiveJadeMapConverter.toWritableMap(report.toMap()));
    }

    params.putArray("reportList", array);

    getReactApplicationContext()
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit("reportList", params);
  }
}
