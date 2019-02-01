package hardwaresniffer;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

import jade.core.behaviours.TickerBehaviour;
// http://jade.tilab.com/doc/api/jade/core/behaviours/TickerBehaviour.html
public class HardwareSnifferBehaviour extends TickerBehaviour {

  private HardwareSnifferAgent hsAgent;

  public HardwareSnifferBehaviour(Agent agent, long period) {
    super(agent, period);

    this.hsAgent = (HardwareSnifferAgent) agent;
  }

  @Override
  protected void onTick() {
    hsAgent.sendGenericMessage("HardwareSnifferBehaviour.onTick");
    hsAgent.notifyLocation();

    if (hsAgent.currentContainerId < hsAgent.platformContainers.size()) {
      hsAgent.sendGenericMessage("Im in!");
      hsAgent.sendGenericMessage(String.valueOf(hsAgent.currentContainerId));
      hsAgent.sendGenericMessage(String.valueOf(hsAgent.platformContainers.size()));
      hsAgent.doMove(hsAgent.platformContainers.get(hsAgent.currentContainerId));
    } else {
      stop();
    }
  }
}