package hardwaresniffer;

import reactivejade.*;

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

import java.lang.Runtime;
import java.lang.StringBuffer;
import java.util.Vector;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.lang.Package;
import java.lang.System;

import java.io.File;

import java.io.FileReader;

public class HardwareSnifferAgent extends ReactiveJadeAgent {

  public Vector<Location> platformContainers;
  public int nextContainerIndex;
  public Location sourceContainer;
  public StringBuffer collectedInfo = new StringBuffer();

  protected void setup() {
    super.setup();

    this.getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
    this.getContentManager().registerOntology(MobilityOntology.getInstance());

    this.sourceContainer = this.here();
    this.platformContainers = this.fetchPlatformContainers();
    this.nextContainerIndex = 0;


    sendGenericMessage(System.getProperty("java.vm.name"));

    Package pkg = Package.getPackage("android.os");

    if (pkg == null) {
      sendGenericMessage("android.os not found");
    } else {
      sendGenericMessage("android.os found!");

      // sendGenericMessage(String.valueOf(Class.forName("android.os.Build").SDK_INT));
    }

    File file = new File("/proc/loadavg");

    if (file.exists()) {
      sendGenericMessage("exists!");
    } else {
      sendGenericMessage("doesnt exist");
    }

    try {
      FileReader fileReader = new FileReader("/proc/meminfo");

      fileReader.read();
    } catch (Exception e) {
      sendGenericMessage(e.getMessage());
    }

    addBehaviour(new HardwareSnifferBehaviour(
      this,
      2000L
    ));
  }

  @Override
  protected void afterMove() {  
    sendGenericMessage("HardwareSnifferAgent.afterMove");
    notifyLocation();

    if (this.sourceContainer.equals(this.here())) {
      sendGenericMessage("I'm back at home!");

      this.nextContainerIndex = 0;
    } else {
      this.nextContainerIndex = this.nextContainerIndex + 1;
    }
  }

  public void sendGenericMessage(String msg) {
    sendLog(msg);
    System.out.println(msg);
    // LOGGER.log(Level.INFO, msg);
  }

  public void notifyLocation() {
    String msg = "I'm in " + here().getName();

    sendGenericMessage(msg);
  }

  public Location nextLocation() {
    this.sendGenericMessage("HardwareSnifferAgent.nextLocation");

    Location nextLocation = null;

    if (this.nextContainerIndex < this.platformContainers.size()) {
      nextLocation = this.platformContainers.get(this.nextContainerIndex);
    } else {
      nextLocation = this.sourceContainer;
    }

    // this.sendGenericMessage(nextLocation.toString());

    return nextLocation;
  }

  private Vector<Location> fetchPlatformContainers() {
    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);

    request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
    request.setOntology(MobilityOntology.NAME);
    request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

    Action action = new Action(this.getAMS(), new QueryPlatformLocationsAction());

    try {
      this.getContentManager().fillContent(request, action);

      request.addReceiver(action.getActor());

      send(request);

      MessageTemplate messageTemplate = MessageTemplate.and(
        MessageTemplate.MatchSender(this.getAMS()),
        MessageTemplate.MatchPerformative(ACLMessage.INFORM)        
      );

      ACLMessage response = this.blockingReceive(messageTemplate);

      ContentElement contentElement = this.getContentManager().extractContent(response);

      Result result = (Result) contentElement;

      Iterator iterator = result.getItems().iterator();

      Vector<Location> platformContainers = new Vector<Location>();

      while (iterator.hasNext()) {
        Location location = (Location) iterator.next();

        if (!location.equals(this.here())) {
          platformContainers.add(location);
        }
      }

      return platformContainers;
    } catch (Exception e) {
      sendGenericMessage(e.getMessage());

      return null;
    }
  }

  private void sendLog(String log) {
    fireEvent(new ReactiveJadeEvent(
      this,
      "log",
      (new ReactiveJadeMap()).putString("message", log)
    ));
  }

}

