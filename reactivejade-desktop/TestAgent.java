import jade.core.Agent;
import jade.core.ContainerID;

public class TestAgent extends Agent {

  protected void setup() {
    System.out.println("Hello from " + getAID().getName());

    ContainerID destination = new ContainerID();
    destination.setName("Xiaomi Mi 8");

    doMove(destination);
  }

  protected void afterMove() {
    System.out.println("Moved from " + getAID().getName());
  }

}
