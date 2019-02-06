package hardwaresniffer;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.util.Logger;

public class HardwareSnifferBehaviour extends CyclicBehaviour {

  private static Logger logger = Logger.getJADELogger(HardwareSnifferAgent.class.getName());

  private List<Location> journey;

  public HardwareSnifferBehaviour(HardwareSnifferAgent agent, List<Location> journey) {
    super(agent);

    this.journey = journey;
  }

  @Override
  public void action() {
    logger.log(Level.INFO, "HardwareSnifferBehaviour.action");
    logger.log(Level.INFO, "HardwareSnifferBehaviour.action > journey.size=" + String.valueOf(journey.size()));

    HardwareSnifferAgent agent = (HardwareSnifferAgent) getAgent();

    if (hasNextLocation()) {
      logger.log(Level.INFO, "HardwareSnifferBehaviour.action > hasNextLocation");

      agent.doMove(nextLocation());
    } else {
      agent.removeBehaviour(this);

      if (agent.here().equals(agent.sourceContainer)) {
        logger.log(Level.INFO, "HardwareSnifferBehaviour.action > noMoves");

      } else {
        agent.doMove(agent.sourceContainer);
      }
    }

    // System.out.println(String.valueOf(journey.hasNext()));
    // hsAgent.sendGenericMessage("HardwareSnifferBehaviour.onTick");
    // hsAgent.sendGenericMessage("I'm " + hsAgent.getName() + " and I'm on " + hsAgent.here().getName() + " at " + (new Date()));


    // HardwareSniffer hs = HardwareSnifferManager.getManager().getSniffer();

    // hsAgent.sendGenericMessage("Total physical: " + String.valueOf(hs.getTotalPhysicalMemorySize()));
    // hsAgent.sendGenericMessage("Free physical: " + String.valueOf(hs.getFreePhysicalMemorySize()));

    // hsAgent.sendGenericMessage("Total virtual: " + String.valueOf(hs.getTotalVirtualMemorySize()));
    // hsAgent.sendGenericMessage("Free virtual: " + String.valueOf(hs.getFreeVirtualMemorySize()));

    // hsAgent.sendGenericMessage("System load: " + String.valueOf(hs.getSystemLoadAverage()));

    // agent.doMove(agent.nextLocation());
  }

  private boolean hasNextLocation() {
    return journey.size() > 0;
  }

  private Location nextLocation() {
    return journey.remove(0);
  }
}