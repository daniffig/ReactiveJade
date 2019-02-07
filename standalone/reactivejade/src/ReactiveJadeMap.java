package reactivejade;

import java.util.HashMap;

public class ReactiveJadeMap extends HashMap<String, Object> {

  private static final long serialVersionUID = 1L;

  public ReactiveJadeMap() {
    super();
  }

  public ReactiveJadeMap putBoolean(String key, Boolean value) {
    put(key, value);

    return this;
  }

  public ReactiveJadeMap putInt(String key, Integer value) {
    put(key, value);

    return this;
  }

  public ReactiveJadeMap putDouble(String key, Double value) {
    put(key, value);

    return this;
  }

  public ReactiveJadeMap putString(String key, String value) {
    put(key, value);

    return this;
  }

  public ReactiveJadeMap putObject(String key, Object value) {
    put(key, value);

    return this;
  }
}