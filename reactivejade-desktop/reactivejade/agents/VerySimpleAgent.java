package reactivejade.agents;

import jade.core.Agent;
import jade.core.ContainerID;

public class VerySimpleAgent extends Agent {

  protected void setup() {
    ContainerID destination = new ContainerID();
    destination.setName("Main-Container");

    doMove(destination);
  }

}