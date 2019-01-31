package reactivejade;

import java.util.ArrayList;
import java.util.List;

import reactivejade.ReactiveJadeEventListener;

public interface ReactiveJadeEventEmitter {

  public final List<ReactiveJadeEventListener> listeners = new ArrayList<ReactiveJadeEventListener>();

  public void addListener(ReactiveJadeEventListener listener);
  public void fireEvent(ReactiveJadeEvent event);
}