package reactivejade;

import jade.core.Agent;
import jade.core.ContainerID;

import java.util.Observable;

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
      listener.reactiveJadeEventReceived(event);
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

    fireEvent(new ReactiveJadeEvent(this, "log", "VerySimpleAgent.setup"));

    ContainerID destination = new ContainerID();
    destination.setName("Main-Container");

    doMove(destination);
  }

  @Override
  protected void afterMove() {
    fireEvent(new ReactiveJadeEvent(this, "log", "afterMove"));
  }
}