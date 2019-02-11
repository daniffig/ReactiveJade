package reactivejade.desktop;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;

public class Boot {

  public static void main(String[] args) {
    System.out.println("Boot > main");
    
    Profile profile = new ProfileImpl();
    profile.setParameter(Profile.PLATFORM_ID, "HardwareSnifferPlatform");
    profile.setParameter(Profile.CONTAINER_NAME, "TestContainer");
    profile.setParameter(Profile.MAIN_HOST, "localhost");
    profile.setParameter(Profile.GUI, Boolean.TRUE.toString());

    Runtime.instance().createMainContainer(profile);
  }
}