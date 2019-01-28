package com.reactivejade;

import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

public class MyFirstAgent extends Agent {

  private final static Logger logger = Logger.getLogger("com.reactivejade.MyFirstAgent");

  private ReactContext reactContext;
  private Callback successCallback;

  protected void setup() {
    Object[] args = getArguments();

    logger.log(Level.WARNING, "setup");

    if (args != null && args.length > 0) {

      if (args[0] instanceof ReactContext) {
        reactContext = (ReactContext) args[0];
      }

      if (args[1] != null) {
        successCallback = (Callback) args[1];
      }
    }

    // System.out.println("Hello there! from " + getAID().getName());

    addBehaviour(new TickerBehaviour(this, 100) {
      protected void onTick() {
        WritableMap params = Arguments.createMap();

        params.putString("msg", "General Kenobi!");

        sendEvent("testMsg", params);
      }
    });

  }

  protected void takeDown() {
    System.out.println("Cya!");
  }

  private void sendEvent(
      String eventName,
      @Nullable WritableMap params) {

    reactContext
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit(eventName, params);
  }
}