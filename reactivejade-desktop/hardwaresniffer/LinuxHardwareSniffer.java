package hardwaresniffer;

import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.System;

public class LinuxHardwareSniffer implements HardwareSniffer {

  String proc_cpuinfo = "/proc/cpuinfo";
  String proc_meminfo = "/proc/meminfo";

  @Override
  public long getTotalPhysicalMemorySize() {
    try {
      FileReader localFileReader = new FileReader(proc_meminfo);
      BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);

      localBufferedReader.readLine();

      // hsAgent.sendGenericMessage(localBufferedReader.readLine());

      localBufferedReader.close();

      return 14L;
    } catch (Exception e) {
      return 15L;
      // hsAgent.sendGenericMessage(e.getMessage());
    }
  }

  @Override
  public long getFreePhysicalMemorySize() {
    return 10L;
  }

  
}