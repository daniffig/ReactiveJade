package hardwaresniffer;

import java.util.Date;

public class HardwareSnifferReport {

  private Date reportDate = new Date();
  private String containerName;
  private long totalPhysicalMemory;
  private long freePhysicalMemory;
  private long totalVirtualMemory;
  private long freeVirtualMemory;
  private double systemLoadAverage;
  private String operatingSystemName;
  private String virtualMachineName;

  public HardwareSnifferReport(
      String containerName,
      long totalPhysicalMemory,
      long freePhysicalMemory,
      long totalVirtualMemory,
      long freeVirtualMemory,
      double systemLoadAverage,
      String operatingSystemName,
      String virtualMachineName
  ) {
    this.containerName = containerName;
    this.totalPhysicalMemory = totalPhysicalMemory;
    this.freePhysicalMemory = freePhysicalMemory;
    this.totalVirtualMemory = totalVirtualMemory;
    this.freeVirtualMemory = freeVirtualMemory;
    this.systemLoadAverage = systemLoadAverage;
    this.operatingSystemName = operatingSystemName;
    this.virtualMachineName = virtualMachineName;
  }

  public Date getReportDate() {
    return this.reportDate;
  }

  public String getContainerName() {
    return this.containerName;
  }

  public long getTotalPhysicalMemory() {
    return this.totalPhysicalMemory;
  }

  public long getFreePhysicalMemory() {
    return this.freePhysicalMemory;
  }

  public long getTotalVirtualMemory() {
    return this.totalVirtualMemory;
  }

  public long getFreeVirtualMemory() {
    return this.freeVirtualMemory;
  }

  public double getSystemLoadAverage() {
    return this.systemLoadAverage;
  }

  public String getOperatingSystemName() {
    return this.operatingSystemName;
  }

  public String getVirtualMachineName() {
    return this.virtualMachineName;
  }
}