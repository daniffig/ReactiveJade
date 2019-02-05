package hardwaresniffer;

public class HardwareSnifferManagerImpl extends HardwareSnifferManager {

  @Override
  public HardwareSniffer getSniffer() {
    if (hsInstance == null) {
      hsInstance = new LinuxHardwareSniffer();
    }

    return hsInstance;
  }

}