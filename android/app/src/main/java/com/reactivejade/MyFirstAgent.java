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
import jade.core.ContainerID;
import jade.core.Location;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import android.os.Debug;

public class MyFirstAgent extends Agent {

  private final static Logger logger = Logger.getLogger("com.reactivejade.MyFirstAgent");

  private ReactContext reactContext;

  public Vector<Location> platformContainers;
  // public Iterator platformContainers;
  public int currentContainerId;
  public Location sourceContainer;

  protected void setup() {
    Object[] args = getArguments();

    if (args != null && args.length > 0) {

      if (args[0] instanceof ReactContext) {
        reactContext = (ReactContext) args[0];
      }
    }

    // We add this to prevent "Unknown language fipa-sl" exception
    // as read on http://jade.tilab.com/pipermail/jade-develop/2007q4/011473.html
    this.getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
    this.getContentManager().registerOntology(MobilityOntology.getInstance());

    this.currentContainerId = 0;
    this.sourceContainer = this.here();

    // fetchPlatformContainers();

    // sendLog(String.valueOf(platformContainers.size()));

    // addBehaviour(new ReactiveJadeBehaviour(this, 2000L));
    // addBehaviour(new SimpleReactiveJadeBehaviour(this));

    ContainerID destination = new ContainerID();
    destination.setName("Main-Container");
    // destination.setAddress("192.168.0.6:1099/JADE");

    sendLog(destination.toString());

    doMove(destination);
  }

  // @Override
  // protected void beforeMove() {
  //   sendLog("beforeMove");
  //   super.beforeMove();
  // }

  @Override
  protected void afterMove() {
    sendLog("afterMove");

    if (currentContainerId < platformContainers.size()) {
      currentContainerId = currentContainerId + 1;
    } else {
      doMove(sourceContainer);
    }

    super.afterMove();
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

  // Helper method to send logs easily. :)
  public void sendLog(
      String log
  ) {

    WritableMap params = Arguments.createMap();

    params.putString("log", log);

    this.sendEvent("log", params);
  }

  public void fetchPlatformContainers() {
    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);

    request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
    request.setOntology(MobilityOntology.NAME);
    request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

    Action action = new Action(getAMS(), new QueryPlatformLocationsAction());

    this.platformContainers = new Vector<Location>();

    try {
      getContentManager().fillContent(request, action);

      request.addReceiver(action.getActor());

      send(request);

      MessageTemplate messageTemplate = MessageTemplate.and(
        MessageTemplate.MatchSender(getAMS()),
        MessageTemplate.MatchPerformative(ACLMessage.INFORM)        
      );

      ACLMessage response = this.blockingReceive(messageTemplate);

      ContentElement contentElement = this.getContentManager().extractContent(response);

      Result result = (Result) contentElement;

      Iterator iterator = result.getItems().iterator();

      while (iterator.hasNext()) {
        this.platformContainers.add((Location) iterator.next());
      }

      for (Location location : this.platformContainers) {
        this.sendLog(location.getName());
      }
    } catch (Exception e) {
      WritableMap params = Arguments.createMap();

      params.putString("size", e.getMessage());

      sendEvent("getContainers", params);
    }

  }
}