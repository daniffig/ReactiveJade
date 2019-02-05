package hardwaresniffer;

import java.lang.Runtime;

public abstract class HardwareSniffer {

  // Para implementar un constructor genérico se podría utilizar
  // java.lang.Runtime.getRuntime.getProperty("os.name")
  // y a partir del resultado devolver la instancia correcta.

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