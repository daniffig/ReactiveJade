package reactivejade;

import java.util.EventObject;

public class ReactiveJadeEvent extends EventObject {

  private static final long serialVersionUID = 1L;

  private String eventName;
  private Object params;

  public ReactiveJadeEvent(Object source, String eventName, Object params) {
    super(source);

    this.eventName = eventName;
    this.params = params;
  }

  public String getEventName() {
    return this.eventName;
  }

  public Object getParams() {
    return this.params;
  }

}
