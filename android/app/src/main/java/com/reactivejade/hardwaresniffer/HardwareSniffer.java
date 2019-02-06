package hardwaresniffer;

import java.lang.Runtime;

public abstract class HardwareSniffer {

  public abstract double getSystemLoadAverage();
  public abstract long getTotalPhysicalMemorySize();
  public abstract long getFreePhysicalMemorySize();

  // Estos dos métodos los podemos implementar acá ya que utilizan la clase Runtime,
  // disponible en las implementaciones de JVM y Dalvik VM (Android)
  
  public long getTotalVirtualMemorySize() {
    return Runtime.getRuntime().totalMemory();
  }

  public long getFreeVirtualMemorySize() {
    return Runtime.getRuntime().freeMemory();
  }
  
}