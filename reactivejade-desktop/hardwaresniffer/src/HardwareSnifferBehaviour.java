package hardwaresniffer;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.TickerBehaviour;

import java.io.FileReader;
import java.io.BufferedReader;


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
    hsAgent.sendGenericMessage("HardwareSnifferBehaviour.onTick");
    hsAgent.sendGenericMessage("I'm " + hsAgent.getName() + " and I'm on " + hsAgent.here().getName() + " at " + (new Date()));

    hsAgent.doMove(hsAgent.nextLocation());
  }
}