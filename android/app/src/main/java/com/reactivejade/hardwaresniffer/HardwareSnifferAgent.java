package hardwaresniffer;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import jade.content.ContentElement;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

import reactivejade.ReactiveJadeAgent;
import reactivejade.ReactiveJadeEvent;
import reactivejade.ReactiveJadeMap;
import reactivejade.ReactiveJadeSubscriptionService;

public class HardwareSnifferAgent extends ReactiveJadeAgent {

  private static Logger logger = Logger.getJADELogger(HardwareSnifferAgent.class.getName());

  public Date journeyStartedAt;
  public List<Location> platformContainers;
  public Location sourceContainer;

  public List<HardwareSnifferReport> reportList;

  protected void setup() {
    super.setup();

    ReactiveJadeSubscriptionService.subscribe(this.getClass().getName(), new HardwareSnifferReporter());

    startJourney();
  }

  private void startJourney() {
    this.journeyStartedAt = new Date();

    logInfo("I'm " + getName() + " and I'm starting a new adventure at " + journeyStartedAt.toString());

    getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
    getContentManager().registerOntology(MobilityOntology.getInstance());

    this.sourceContainer = here();

    this.platformContainers = fetchPlatformContainers();

    this.reportList = new ArrayList<HardwareSnifferReport>();

    this.doWait(5000L);

    addBehaviour(new HardwareSnifferBehaviour(this, platformContainers));    
  }

  @Override
  protected void afterMove() {
    if (atSourceContainer()) {
      endJourney();
    } else {
      reportList.add(buildHardwareSnifferReport());
    }
  }

  public void endJourney() {
    reportList.add(buildHardwareSnifferReport());

    Date journeyEndedAt = new Date();

    ReactiveJadeMap journeyReport = (new ReactiveJadeMap())
      .putString("agentName", getName())
      .putString("sentAt", journeyEndedAt.toString())
      .putString("elapsedTime", String.valueOf(journeyEndedAt.getTime() - journeyStartedAt.getTime()))
      .putObject("reportList", reportList);

    notifyReactiveJadeEvent(new ReactiveJadeEvent(
      this,
      "reportList",
      journeyReport
    ));

    logInfo("I'm " + getName() + " and I'm ending my last adventure at " + (new Date()).toString());

    addBehaviour(new WakerBehaviour(this, 5000L) {

      protected void onWake() {
        HardwareSnifferAgent.this.startJourney();
      }      
    });
  }

  private HardwareSnifferReport buildHardwareSnifferReport() {
    HardwareSniffer hardwareSniffer = HardwareSnifferManager.getManager().getSniffer();

    return new HardwareSnifferReport(
      here().getName(),
      hardwareSniffer.getTotalPhysicalMemorySize(),
      hardwareSniffer.getFreePhysicalMemorySize(),
      hardwareSniffer.getTotalVirtualMemorySize(),
      hardwareSniffer.getFreeVirtualMemorySize(),
      hardwareSniffer.getSystemLoadAverage(),
      hardwareSniffer.getOperatingSystemName(),
      hardwareSniffer.getVirtualMachineName()
    );    
  }

  private boolean atSourceContainer() {
    return here().equals(sourceContainer);
  }

  public void logInfo(String log) {
    notifyReactiveJadeEvent(new ReactiveJadeEvent(
      this,
      "log",
      (new ReactiveJadeMap()).putString("message", log)
    ));
  }

  private List<Location> fetchPlatformContainers() {
    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);

    request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
    request.setOntology(MobilityOntology.NAME);
    request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

    Action action = new Action(getAMS(), new QueryPlatformLocationsAction());

    try {
      getContentManager().fillContent(request, action);

      request.addReceiver(action.getActor());

      send(request);

      MessageTemplate messageTemplate = MessageTemplate.and(
        MessageTemplate.MatchSender(getAMS()),
        MessageTemplate.MatchPerformative(ACLMessage.INFORM)        
      );

      ACLMessage response = blockingReceive(messageTemplate);

      ContentElement contentElement = getContentManager().extractContent(response);

      Result result = (Result) contentElement;

      Iterator iterator = result.getItems().iterator();

      List<Location> platformContainers = new ArrayList<Location>();

      while (iterator.hasNext()) {
        Location location = (Location) iterator.next();

        if (!location.equals(here())) {
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
