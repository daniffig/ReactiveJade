package reactivejade;

import java.util.EventObject;
import java.util.Map;

public class ReactiveJadeEvent extends EventObject {

  private static final long serialVersionUID = 1L;

  private String eventName;
  private Map<String, Object> params;

  public ReactiveJadeEvent(Object source, String eventName, Map<String, Object> params) {
    super(source);

    this.eventName = eventName;
    this.params = params;
  }

  public String getEventName() {
    return this.eventName;
  }

  public Map<String, Object> getParams() {
    return this.params;
  }

  public Boolean hasParams() {
    return getParams() != null;
  }

}
