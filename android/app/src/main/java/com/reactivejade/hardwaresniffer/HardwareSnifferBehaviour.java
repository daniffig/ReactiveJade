package hardwaresniffer;

import java.util.List;
import java.util.logging.Level;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;

import jade.core.mobility.AgentMobilitySlice;

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
      Location destination = nextLocation();

      try {
        if (isDestinationReachable(destination)) {
          agent.doMove(nextLocation());
        } else {
          agent.onMoveError(destination, String.format(
            "Container %s is not reachable.",
            destination.getName()
          ));
        }
      } catch (Exception e) {
        agent.onMoveError(destination, e.getMessage());
      }
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

  private boolean isDestinationReachable(Location destination) throws Exception {
    HardwareSnifferAgent agent = (HardwareSnifferAgent) getAgent();
    
    return agent.getMobHelper().getSlice(destination.getName()) != null;
  }
}