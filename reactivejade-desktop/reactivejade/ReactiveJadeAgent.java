package reactivejade;

import jade.core.Agent;
import jade.core.ContainerID;

public abstract class ReactiveJadeAgent extends Agent implements ReactiveJadeEventEmitter {

  @Override
  public final void addListener(ReactiveJadeEventListener listener) {
    listeners.add(listener);
  }

  @Override
  public final void fireEvent(ReactiveJadeEvent event) {
    for (ReactiveJadeEventListener listener : listeners) {
      listener.reactiveJadeEventReceived(event);
    }
  }

  // FIXME
  // We should allow to send more arguments than listeners,
  // they should also be allowed as a property.
  protected void setup() {
    super.setup();

    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      for (Object listener : args) {
        this.addListener((ReactiveJadeEventListener) listener);
      }
    }
  }
}