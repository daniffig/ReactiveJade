package hardwaresniffer;

import java.lang.Runtime;
import java.lang.System;

public abstract class HardwareSniffer {

  public abstract double getSystemLoadAverage();
  public abstract double getTotalPhysicalMemorySize();
  public abstract double getFreePhysicalMemorySize();

  private static double KiB = 1024.0;

  // Estos metodos los podemos implementar aca ya que utilizan las clases System y Runtime,
  // disponibles en las implementaciones de JVM y Dalvik VM (DVM)
  
  public double getTotalVirtualMemorySize() {
    return Runtime.getRuntime().totalMemory() / KiB;
  }

  public double getFreeVirtualMemorySize() {
    return Runtime.getRuntime().freeMemory() / KiB;
  }

  public String getOperatingSystemName() {
    return System.getProperty("os.name");
  }

  public String getVirtualMachineName() {
    return System.getProperty("java.vm.name");
  }  
}