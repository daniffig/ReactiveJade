package hardwaresniffer;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import jade.content.ContentElement;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

import reactivejade.ReactiveJadeAgent;
import reactivejade.ReactiveJadeEvent;
import reactivejade.ReactiveJadeMap;

import hardwaresniffer.HardwareSnifferBehaviour;

public class HardwareSnifferAgent extends ReactiveJadeAgent {

  private static Logger logger = Logger.getJADELogger(HardwareSnifferAgent.class.getName());

  // public Vector<Location> platformContainers;
  public List<Location> platformContainers;
  public int nextContainerIndex;
  public Location sourceContainer;

  public List<HardwareSnifferReport> reportList;

  // public StringBuffer collectedInfo = new StringBuffer();

  protected void setup() {
    super.setup();

    logInfo("HardwareSnifferAgent.setup");

    this.getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
    this.getContentManager().registerOntology(MobilityOntology.getInstance());

    this.sourceContainer = this.here();
    this.platformContainers = this.fetchPlatformContainers();
    this.nextContainerIndex = 0;

    this.reportList = new ArrayList<HardwareSnifferReport>();

    addBehaviour(new HardwareSnifferBehaviour(this, platformContainers));
    //   this,
    //   2000L
    // ));
  }

  @Override
  protected void afterMove() {  
    // sendGenericMessage("HardwareSnifferAgent.afterMove");
    // notifyLocation();

    if (this.sourceContainer.equals(this.here())) {
      // sendGenericMessage("I'm back at home!");

      this.nextContainerIndex = 0;
    } else {
      this.nextContainerIndex = this.nextContainerIndex + 1;
    }
  }

  public void logInfo(String log) {
    logger.log(Level.INFO, log);

    notifyReactiveJadeEvent(new ReactiveJadeEvent(
      this,
      "log",
      (new ReactiveJadeMap()).putString("message", log)
    ));
  }

  public void notifyLocation() {
    String msg = "I'm in " + here().getName();

    logInfo(msg);
  }

  public Location nextLocation() {
    logInfo("HardwareSnifferAgent.nextLocation");

    Location nextLocation = null;

    if (this.nextContainerIndex < this.platformContainers.size()) {
      nextLocation = this.platformContainers.get(this.nextContainerIndex);
    } else {
      nextLocation = this.sourceContainer;
    }

    // this.sendGenericMessage(nextLocation.toString());

    return nextLocation;
  }

  // private Vector<Location> fetchPlatformContainers() {
  private List<Location> fetchPlatformContainers() {
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

      List<Location> platformContainers = new ArrayList<Location>();
      // Vector<Location> platformContainers = new Vector<Location>();

      while (iterator.hasNext()) {
        Location location = (Location) iterator.next();

        if (!location.equals(this.here())) {
          platformContainers.add(location);
        }
      }

      return platformContainers;
    } catch (Exception e) {
      logInfo(e.getMessage());

      return null;
    }
  }
}

