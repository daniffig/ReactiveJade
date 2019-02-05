package reactivejade;

import java.util.EventObject;

public class ReactiveJadeEvent extends EventObject {

  private static final long serialVersionUID = 1L;

  private String eventName;
  private ReactiveJadeMap params;

  public ReactiveJadeEvent(Object source, String eventName, ReactiveJadeMap params) {
    super(source);

    this.eventName = eventName;
    this.params = params;
  }

  public String getEventName() {
    return this.eventName;
  }

  public ReactiveJadeMap getParams() {
    return this.params;
  }

  public Boolean hasParams() {
    return getParams() != null;
  }
}
