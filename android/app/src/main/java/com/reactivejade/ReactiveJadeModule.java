// ReactiveJadeModule.java

package com.reactivejade;

// https://developer.android.com/reference/android/util/Log.html
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
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
import reactivejade.ReactiveJadeEventParser;
import reactivejade.ReactiveJadeSubscribable;
import reactivejade.ReactiveJadeSubscriptionService;

import hardwaresniffer.HardwareSnifferAgent;

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
      String agentName,
      Callback successCallback,
      Callback errorCallback
  ) {
    WritableMap params = Arguments.createMap();

    if (agent == null) {
      try {
        agent = container.createNewAgent(
          agentName,
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
    getReactApplicationContext()
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(event.getEventName(), ReactiveJadeEventParser.parseParams(event));
  }
}
