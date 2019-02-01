package reactivejade;

import jade.core.Agent;
import jade.core.ContainerID;

import java.util.Observable;
import java.util.Map;
import java.util.HashMap;

public class VerySimpleAgent extends Agent implements ReactiveJadeEventEmitter {

  @Override
  public void addListener(ReactiveJadeEventListener listener) {
    listeners.add(listener);
  }

  @Override
  public void fireEvent(ReactiveJadeEvent event) {
    for (ReactiveJadeEventListener listener : listeners) {
      ((ReactiveJadeEventListener) listener).sendFakeString("VerySimpleAgent.fireEvent");
      ((ReactiveJadeEventListener) listener).sendFakeString(event.getEventName());
      ((ReactiveJadeEventListener) listener).reactiveJadeEventReceived(event);
    }
  }

  protected void setup() {
    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      for (Object listener : args) {
        this.addListener((ReactiveJadeEventListener) listener);
        ((ReactiveJadeEventListener) listener).sendFakeString("VerySimpleAgent.addListener");
      }
    }


    System.out.println("Setup there!");


    Map<String, Object> params = new HashMap<String, Object>();

    params.put("value1", 1);
    params.put("value2", "a String");

    fireEvent(new ReactiveJadeEvent(this, "log", params));

    ContainerID destination = new ContainerID();
    destination.setName("Main-Container");

    doMove(destination);
  }

  @Override
  protected void afterMove() {
    System.out.println("Hello there!");
    Map<String, Object> params = new HashMap<String, Object>();

    params.put("value1", 1);
    params.put("value2", "a String");

    fireEvent(new ReactiveJadeEvent(this, "log", params));
  }
}