package reactivejade;

import jade.core.Agent;
import jade.core.ContainerID;

import java.util.Map;
import java.util.HashMap;

public class VerySimpleAgent extends ReactiveJadeAgent {

  protected void setup() {
    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      for (Object listener : args) {
        this.addListener((ReactiveJadeEventListener) listener);
      }
    }

    ContainerID destination = new ContainerID();
    destination.setName("Xiaomi Mi 8");

    doMove(destination);
  }

  @Override
  protected void beforeMove() {
    System.out.println("Hello there!");
    ReactiveJadeMap params = new ReactiveJadeMap();

    params.putInt("value1", 1);
    params.putString("value2", "a String");
    params.putString("value3", "beforeMove");

    fireEvent(new ReactiveJadeEvent(this, "log", params));
  }

  @Override
  protected void afterMove() {
    System.out.println("Hello there!");
    ReactiveJadeMap params = new ReactiveJadeMap();

    params.putInt("value1", 1);
    params.putString("value2", "a String");
    params.putString("value3", "afterMove");

    fireEvent(new ReactiveJadeEvent(this, "log", params));
  }
}