package reactivejade;

import jade.core.Agent;

import reactivejade.ReactiveJadeEvent;
import reactivejade.ReactiveJadeEventEmitter;
import reactivejade.ReactiveJadeSubscriptionService;

public abstract class ReactiveJadeAgent extends Agent implements ReactiveJadeEventEmitter {

  public void notifyReactiveJadeEvent(ReactiveJadeEvent event) {
    ReactiveJadeSubscriptionService.notify(event);
  }
}