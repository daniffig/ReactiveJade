package com.reactivejade;

import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jade.core.behaviours.OneShotBehaviour;


import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleReactiveJadeBehaviour extends OneShotBehaviour {

  private final static Logger logger = Logger.getLogger("com.reactivejade.ReactiveJadeBehaviour");

  public SimpleReactiveJadeBehaviour(Agent agent) {
    super(agent);
  }

  public void action() {
    MyFirstAgent agent = (MyFirstAgent) getAgent();

    agent.sendLog("i'm in");

    ContainerID destination = new ContainerID();
    destination.setName("Main-Container");
    destination.setAddress("192.168.0.6:1099/JADE");

    agent.sendLog(destination.toString());

    agent.doMove(destination);
  }

  // public SimpleReactiveJadeBehaviour(Agenthttp://jade.tilab.com/pipermail/jade-develop/2010q2/015719.html agent, long period) {
  //   super(agent, period);
  // }

  // protected void onTick() {
  //   logger.log(Level.INFO, "ReactiveJadeBehaviour.onTick");

  //   // We should parametrize this, but this hack will work.
  //   MyFirstAgent agent = (MyFirstAgent) getAgent();

  //   agent.sendLog("ReactiveJadeBehaviour.onTick");

  //   ContainerID dest = new ContainerID();
  //   dest.setName("Main-Container");
  //   // dest.setAddress("192.168.0.6");
  //   // dest.setPort("1099");

  //   agent.sendLog(dest.toString());

  //   // agent.sendLog("I want to move to " + destinationContainer.getName());
  //   // agent.sendLog("I want to move to " + destinationContainer.getID());
  //   // agent.sendLog("I want to move to " + destinationContainer.getAddress());
  //   // agent.sendLog(dest.toString());

  //   agent.doMove(dest);

  //   return;

  //   // agent.sendLog(String.valueOf(agent.getAgentState()));

  //   // if (agent.currentContainerId < agent.platformContainers.size()) {
  //   //   Location destinationContainer = agent.platformContainers.get(1);

  //   //   ContainerID dest = new ContainerID();
  //   //   dest.setName("Main-Container");
  //   //   dest.setAddress("192.168.0.6");
  //   //   dest.setPort("1099");

  //   //   // agent.sendLog("I want to move to " + destinationContainer.getName());
  //   //   // agent.sendLog("I want to move to " + destinationContainer.getID());
  //   //   // agent.sendLog("I want to move to " + destinationContainer.getAddress());
  //   //   // agent.sendLog(dest.toString());

  //   //   agent.doMove(dest);
  //   // } else {
  //   //   agent.sendLog("loop completed!");
  //   // }

  //   // WritableMap params = Arguments.createMap();

  //   // params.putString("msg", "onTick!");
  //   // // params.putString("freeMemor", String.valueOf(Runtime.getRuntime().freeMemory()));
  //   // // params.putString("maxMemory", String.valueOf(Runtime.getRuntime().maxMemory()));
  //   // // params.putString("totalNativeMemory", String.valueOf(Debug.getNativeHeapAllocatedSize()));
  //   // // params.putString("freeNativeMemory", String.valueOf(Debug.getNativeHeapFreeSize()));
  //   // // params.putString("nativeMemory", String.valueOf(Debug.getNativeHeapSize()));

  //   // sendEvent("testMsg", params);

  //   // int currentContainerId = MyFirstAgent.this.currentContainerId;
  //   // Vector<Location> platformContainers = MyFirstAgent.this.platformContainers;

  //   // if (currentContainerId < platformContainers.size()) {
  //   //   MyFirstAgent.this.sendLog("I'm in " + String.valueOf(currentContainerId));  

  //   //   MyFirstAgent.this.currentContainerId = MyFirstAgent.this.currentContainerId + 1;

  //   //   doMove(platformContainers.get(currentContainerId));
  //   // } else {
  //   //   MyFirstAgent.this.sendLog("I'm in " + String.valueOf(currentContainerId)); 

  //   //   MyFirstAgent.this.currentContainerId = 0;
  //   // }
  // }
}