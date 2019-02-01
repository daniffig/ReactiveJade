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
import jade.util.leap.Iterator;

import java.lang.StringBuffer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HardwareSnifferAgent extends ReactiveJadeAgent {

  public Vector<Location> platformContainers;
  public int currentContainerId;
  public Location sourceContainer;
  public StringBuffer collectedInfo = new StringBuffer();

  protected void setup() {
    super.setup();

    this.getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
    this.getContentManager().registerOntology(MobilityOntology.getInstance());

    this.currentContainerId = 0;
    this.sourceContainer = this.here();

    fetchPlatformContainers();

    addBehaviour(new HardwareSnifferBehaviour(this, 5000L));

    // sendLog(String.valueOf(platformContainers.size()));

    // addBehaviour(new ReactiveJadeBehaviour(this, 2000L));
    // addBehaviour(new SimpleReactiveJadeBehaviour(this));

    // ContainerID destination = new ContainerID();
    // destination.setName("Main-Container");
    // destination.setAddress("192.168.0.6:1099/JADE");


    // doMove(destination);
  }

  @Override
  protected void afterMove() {  
    sendGenericMessage("HardwareSnifferAgent.afterMove");
    notifyLocation();

    if (this.currentContainerId < this.platformContainers.size()) {
      this.currentContainerId = this.currentContainerId + 1;      
    } else {

    }
  }

  public void sendGenericMessage(String msg) {
    sendLog(msg);
    System.out.println(msg);
  }

  public void notifyLocation() {
    String msg = "I'm in " + here().getName();

    sendGenericMessage(msg);
  }

  private void fetchPlatformContainers() {
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

