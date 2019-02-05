package reactivejade;

public interface ReactiveJadeEventListener {

  public void reactiveJadeEventReceived(ReactiveJadeEvent event);
  public void sendFakeString(String string);
}