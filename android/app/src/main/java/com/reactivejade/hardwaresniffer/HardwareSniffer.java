package hardwaresniffer;

import java.lang.Runtime;
import java.lang.System;

public abstract class HardwareSniffer {

  public abstract double getSystemLoadAverage();
  public abstract long getTotalPhysicalMemorySize();
  public abstract long getFreePhysicalMemorySize();

  // Estos métodos los podemos implementar acá ya que utilizan las clases System y Runtime,
  // disponibles en las implementaciones de JVM y Dalvik VM (DVM)
  
  public long getTotalVirtualMemorySize() {
    return Runtime.getRuntime().totalMemory();
  }

  public long getFreeVirtualMemorySize() {
    return Runtime.getRuntime().freeMemory();
  }

  public String getOperatingSystemName() {
    return System.getProperty("os.name");
  }

  public String getVirtualMachineName() {
    return System.getProperty("java.vm.name");
  }  
}