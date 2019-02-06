package hardwaresniffer;

import hardwaresniffer.HardwareSniffer;

public abstract class HardwareSnifferManager {

  private static HardwareSnifferManagerImpl instance;
  protected static HardwareSniffer hsInstance;

  public static HardwareSnifferManagerImpl getManager() {

    if (instance == null) {
      instance = new HardwareSnifferManagerImpl();
    }

    return instance;
  }

  public abstract HardwareSniffer getSniffer();
}