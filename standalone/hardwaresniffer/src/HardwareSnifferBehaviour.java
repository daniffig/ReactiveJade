package hardwaresniffer;

import java.util.List;
import java.util.logging.Level;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;

import jade.core.ContainerID;

public class HardwareSnifferBehaviour extends CyclicBehaviour {

  private List<Location> journey;

  public HardwareSnifferBehaviour(HardwareSnifferAgent agent, List<Location> journey) {
    super(agent);

    this.journey = journey;
  }

  @Override
  public void action() {
    HardwareSnifferAgent agent = (HardwareSnifferAgent) getAgent();

    if (hasNextLocation()) {      
      // agent.doMove(nextLocation());
      ContainerID location = new ContainerID();
      location.setName("Fede");

      agent.doMove(location);
    } else {
      agent.removeBehaviour(this);
      
      if (agent.here().equals(agent.sourceContainer)) {
        agent.endJourney();
      } else {
        agent.doMove(agent.sourceContainer);
      }
    }
  }

  private boolean hasNextLocation() {
    return journey.size() > 0;
  }

  private Location nextLocation() {
    return journey.remove(0);
  }
}