package reactivejade;

import java.lang.SecurityException;
import java.util.logging.Handler;

import jade.core.Agent;

import jade.core.Location;
import jade.core.mobility.AgentMobilityService;
import jade.util.Logger;

import reactivejade.ReactiveJadeEvent;
import reactivejade.ReactiveJadeEventEmitter;
import reactivejade.ReactiveJadeSubscriptionService;

public abstract class ReactiveJadeAgent extends Agent implements ReactiveJadeEventEmitter {

  public void notifyReactiveJadeEvent(ReactiveJadeEvent event) {
    ReactiveJadeSubscriptionService.notify(event);
  }

  public void addLogHandler(Handler handler) {
    Logger logger = Logger.getJADELogger(AgentMobilityService.NAME);

    try {
      logger.addHandler(handler);
    } catch (SecurityException se) {

    }
  }

  public abstract void onMoveError(Location destination, String errorMessage);
}