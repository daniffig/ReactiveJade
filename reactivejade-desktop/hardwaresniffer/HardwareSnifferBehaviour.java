package hardwaresniffer;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.TickerBehaviour;

import java.io.FileReader;
import java.io.BufferedReader;


import java.util.Date;

// http://jade.tilab.com/doc/api/jade/core/behaviours/TickerBehaviour.html
public class HardwareSnifferBehaviour extends TickerBehaviour {

  private HardwareSnifferAgent hsAgent;

  public HardwareSnifferBehaviour(Agent agent, long period) {
    super(agent, period);

    this.hsAgent = (HardwareSnifferAgent) agent;
  }

  @Override
  protected void onTick() {
    hsAgent.sendGenericMessage("HardwareSnifferBehaviour.onTick");
    hsAgent.sendGenericMessage("I'm " + hsAgent.getName() + " and I'm on " + hsAgent.here().getName() + " at " + (new Date()));
    // hsAgent.notifyLocation();

    hsAgent.sendGenericMessage(System.getProperty("os.name"));

    HardwareSniffer hs = new LinuxHardwareSniffer();

    hsAgent.sendGenericMessage(String.valueOf(hs.getTotalPhysicalMemorySize()));
    hsAgent.sendGenericMessage(String.valueOf(hs.getFreePhysicalMemorySize()));

    // HardwareSniffer hs = new HardwareSniffer.Linux();



    // this.sniffHardware();
    // this.sniffOperatingSystem();

    hsAgent.doMove(hsAgent.nextLocation());
  }

  private void sniffHardware() {
    String meminfo = "/proc/meminfo";

    try {
      FileReader localFileReader = new FileReader(meminfo);
      BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);

      hsAgent.sendGenericMessage(localBufferedReader.readLine());

      localBufferedReader.close();
    } catch (Exception e) {
      hsAgent.sendGenericMessage(e.getMessage());
    }
  }

  private void sniffOperatingSystem() {
    // hsAgent.sendGenericMessage("HardwareSnifferBehaviour.sniffOperatingSystem1");

    // try {
    //   OperatingSystemMXBean OS = ManagementFactory.getOperatingSystemMXBean();
    //   Class.forName("OperatingSystemMXBean");
    //   // Class.forName("ManagementFactory");

    //   hsAgent.sendGenericMessage("Existe!");

    // } catch (Exception e) {
    //   hsAgent.sendGenericMessage(e.getMessage());
    // }

    // hsAgent.sendGenericMessage("HardwareSnifferBehaviour.sniffOperatingSystem2");
    // hsAgent.sendGenericMessage(String.valueOf(OS.getSystemLoadAverage()));
    // hsAgent.sendGenericMessage(String.valueOf(OS.getAvailableProcessors()));
  }
}