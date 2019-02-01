package reactivejade;

import java.util.HashMap;

public class ReactiveJadeMap extends HashMap<String, Object> {

  private static final long serialVersionUID = 1L;

  public ReactiveJadeMap() {
    super();
  }

  public void putBoolean(String key, Boolean value) {
    put(key, value);
  }

  public void putInt(String key, Integer value) {
    put(key, value);
  }

  public void putDouble(String key, Double value) {
    put(key, value);
  }

  public void putString(String key, String value) {
    put(key, value);
  }
}