package reactivejade;

import java.util.ArrayList;
import java.util.List;

import reactivejade.ReactiveJadeSubscribable;;

public class ReactiveJadeSubscription {

  private String className;
  private List<ReactiveJadeSubscribable> subscribers = new ArrayList<ReactiveJadeSubscribable>();

  public ReactiveJadeSubscription(String className) {
    super();

    this.className = className;
  }

  public void subscribe(ReactiveJadeSubscribable subscriber) {
    this.subscribers.add(subscriber);
  }

  public void notify(ReactiveJadeEvent event) {
    for (ReactiveJadeSubscribable subscriber : subscribers) {
      subscriber.receiveReactiveJadeEvent(event);
    }      
  }
}