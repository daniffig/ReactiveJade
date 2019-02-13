package hardwaresniffer;

import java.util.List;
import java.util.logging.Level;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;

public class HardwareSnifferBehaviour extends CyclicBehaviour {

  private List<Location> journey;

  public HardwareSnifferBehaviour(HardwareSnifferAgent agent, List<Location> journey) {
    super(agent);

    this.journey = journey;
  }

  @Override
  public void action() {
    HardwareSnifferAgent agent = (HardwareSnifferAgent) getAgent();

    checkMoveError();

    if (hasNextLocation()) {
      Location destination = nextLocation();

      agent.lastIntendedDestination = destination;

      agent.doMove(destination);
    } else {
      agent.removeBehaviour(this);
      
      if (agent.here().equals(agent.sourceContainer)) {
        agent.endJourney();
      } else {
        agent.doMove(agent.sourceContainer);
      }
    }
  }

  private void checkMoveError() {
    HardwareSnifferAgent agent = (HardwareSnifferAgent) getAgent();

    if (agent.lastIntendedDestination != null) {
      if (!agent.here().equals(agent.lastIntendedDestination)) {
        agent.onMoveError(
          agent.lastIntendedDestination,
          String.format(
            "an error occurred while %s intended to move to %s",
            agent.getName(),
            agent.lastIntendedDestination.getName()
          )
        );
        agent.lastIntendedDestination = null;
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