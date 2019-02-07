package reactivejade;

import java.util.Map;
import java.util.HashMap;

import reactivejade.ReactiveJadeSubscribable;
import reactivejade.ReactiveJadeSubscription;

public class ReactiveJadeSubscriptionService {

  private static final Map<String, ReactiveJadeSubscription> subscriptions = new HashMap<String, ReactiveJadeSubscription>();

  public static void subscribe(String className, ReactiveJadeSubscribable subscriber) {
    ReactiveJadeSubscription subscription = findOrCreateSubscription(className);

    subscription.subscribe(subscriber);
  }

  public static void notify(ReactiveJadeEvent event) {
    String className = event.getSource().getClass().getName();

    ReactiveJadeSubscription subscription = subscriptions.get(className);

    if (subscription != null) {
      subscription.notify(event);
    }
  }

  private static ReactiveJadeSubscription findOrCreateSubscription(String className) {
    ReactiveJadeSubscription subscription = subscriptions.get(className);

    if (subscription == null) {
      subscription = new ReactiveJadeSubscription(className);

      subscriptions.put(className, subscription);
    }

    return subscription;    
  }  
}