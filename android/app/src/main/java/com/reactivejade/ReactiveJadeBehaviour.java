package com.reactivejade;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.TickerBehaviour;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ReactiveJadeBehaviour extends TickerBehaviour {

  private final static Logger logger = Logger.getLogger("com.reactivejade.ReactiveJadeBehaviour");

  public ReactiveJadeBehaviour(Agent agent, long period) {
    super(agent, period);
  }

  protected void onTick() {
    logger.log(Level.INFO, "ReactiveJadeBehaviour.onTick");

    // We should parametrize this, but this hack will work.
    MyFirstAgent agent = (MyFirstAgent) getAgent();

    agent.sendLog("ReactiveJadeBehaviour.onTick");

    if (agent.currentContainerId < agent.platformContainers.size()) {
      Location destinationContainer = agent.platformContainers.get(agent.currentContainerId);

      agent.sendLog("I want to move to " + destinationContainer.getName());

      agent.doMove(destinationContainer);
    } else {
      agent.sendLog("loop completed!");
    }

    // WritableMap params = Arguments.createMap();

    // params.putString("msg", "onTick!");
    // // params.putString("freeMemor", String.valueOf(Runtime.getRuntime().freeMemory()));
    // // params.putString("maxMemory", String.valueOf(Runtime.getRuntime().maxMemory()));
    // // params.putString("totalNativeMemory", String.valueOf(Debug.getNativeHeapAllocatedSize()));
    // // params.putString("freeNativeMemory", String.valueOf(Debug.getNativeHeapFreeSize()));
    // // params.putString("nativeMemory", String.valueOf(Debug.getNativeHeapSize()));

    // sendEvent("testMsg", params);

    // int currentContainerId = MyFirstAgent.this.currentContainerId;
    // Vector<Location> platformContainers = MyFirstAgent.this.platformContainers;

    // if (currentContainerId < platformContainers.size()) {
    //   MyFirstAgent.this.sendLog("I'm in " + String.valueOf(currentContainerId));  

    //   MyFirstAgent.this.currentContainerId = MyFirstAgent.this.currentContainerId + 1;

    //   doMove(platformContainers.get(currentContainerId));
    // } else {
    //   MyFirstAgent.this.sendLog("I'm in " + String.valueOf(currentContainerId)); 

    //   MyFirstAgent.this.currentContainerId = 0;
    // }
  }
}