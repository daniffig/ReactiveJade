package hardwaresniffer;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.TickerBehaviour;

import java.util.Date;

// http://jade.tilab.com/doc/api/jade/core/behaviours/TickerBehaviour.html
public class HardwareSnifferBehaviour extends TickerBehaviour {

  private HardwareSnifferAgent hsAgent;

  public HardwareSnifferBehaviour(Agent agent, long period) {
    super(agent, period);

    this.hsAgent = (HardwareSnifferAgent) agent;
  }

  @Override
  protected void onTick() {
    // hsAgent.sendGenericMessage("HardwareSnifferBehaviour.onTick");
    // hsAgent.sendGenericMessage("I'm " + hsAgent.getName() + " and I'm on " + hsAgent.here().getName() + " at " + (new Date()));

    HardwareSniffer hs = HardwareSnifferManager.getManager().getSniffer();

    // hsAgent.sendGenericMessage("Total physical: " + String.valueOf(hs.getTotalPhysicalMemorySize()));
    // hsAgent.sendGenericMessage("Free physical: " + String.valueOf(hs.getFreePhysicalMemorySize()));

    // hsAgent.sendGenericMessage("Total virtual: " + String.valueOf(hs.getTotalVirtualMemorySize()));
    // hsAgent.sendGenericMessage("Free virtual: " + String.valueOf(hs.getFreeVirtualMemorySize()));

    // hsAgent.sendGenericMessage("System load: " + String.valueOf(hs.getSystemLoadAverage()));

    hsAgent.doMove(hsAgent.nextLocation());
  }
}