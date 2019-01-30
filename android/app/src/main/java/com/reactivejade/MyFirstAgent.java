package com.reactivejade;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import jade.content.ContentElement;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import android.os.Debug;

public class MyFirstAgent extends Agent {

  private final static Logger logger = Logger.getLogger("com.reactivejade.MyFirstAgent");

  private ReactContext reactContext;

  protected void setup() {
    Object[] args = getArguments();

    if (args != null && args.length > 0) {

      if (args[0] instanceof ReactContext) {
        reactContext = (ReactContext) args[0];
      }
    }

    addBehaviour(new TickerBehaviour(this, 2000) {
      protected void onTick() {
        logger.log(Level.INFO, "Hello!");

        WritableMap params = Arguments.createMap();

        params.putString("msg", "General Kenobi!");
        params.putString("freeMemor", String.valueOf(Runtime.getRuntime().freeMemory()));
        // params.putString("maxMemory", String.valueOf(Runtime.getRuntime().maxMemory()));
        params.putString("totalNativeMemory", String.valueOf(Debug.getNativeHeapAllocatedSize()));
        params.putString("freeNativeMemory", String.valueOf(Debug.getNativeHeapFreeSize()));
        params.putString("nativeMemory", String.valueOf(Debug.getNativeHeapSize()));

        sendEvent("testMsg", params);

        MyFirstAgent.this.getPlatformContainers();
      }
    });

  }

  protected void takeDown() {
    System.out.println("Cya!");
  }

  private void sendEvent(
      String eventName,
      @Nullable ReadableMap params) {

    reactContext
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit(eventName, params);
  }

  public void getPlatformContainers() {
    // WritableMap params = Arguments.createMap();

    // params.putString("size", "failure!");

    // sendEvent("getContainers", params);

    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);

    request.setLanguage((new SLCodec()).getName());
    request.setOntology(MobilityOntology.getInstance().getName());

    Action action = new Action(this.getAMS(), new QueryPlatformLocationsAction());

    try {
      this.getContentManager().fillContent(request, action);

      request.addReceiver(action.getActor());

      this.send(request);

      MessageTemplate messageTemplate = MessageTemplate.and(
        MessageTemplate.MatchSender(getAMS()),
        MessageTemplate.MatchPerformative(ACLMessage.INFORM)        
      );

      ACLMessage response = this.blockingReceive(messageTemplate);

      ContentElement contentElement = this.getContentManager().extractContent(response);

      Result result = (Result) contentElement;

      WritableMap params = Arguments.createMap();

      params.putInt("size", result.getItems().size());

      sendEvent("getContainers", params);
    } catch (Exception e) {
      WritableMap params = Arguments.createMap();

      params.putString("size", e.getMessage());

      sendEvent("getContainers", params);
    }

  }
}