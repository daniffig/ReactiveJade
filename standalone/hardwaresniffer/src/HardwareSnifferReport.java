package hardwaresniffer;

import java.io.Serializable;
import java.util.Date;

import reactivejade.ReactiveJadeMap;

public class HardwareSnifferReport implements Serializable {

  static final long serialVersionUID = 1L;

  private boolean hasError = false;
  private String errorMessage = "";

  private Date reportDate = new Date();
  private String containerName;
  private double totalPhysicalMemory;
  private double freePhysicalMemory;
  private double totalVirtualMemory;
  private double freeVirtualMemory;
  private double systemLoadAverage;
  private String operatingSystemName;
  private String virtualMachineName;

  public HardwareSnifferReport(
      Boolean hasError,
      String containerName,
      String errorMessage
  ) {
    this.hasError = hasError;
    this.containerName = containerName;
    this.errorMessage = errorMessage;
  }

  public HardwareSnifferReport(
      String containerName,
      double totalPhysicalMemory,
      double freePhysicalMemory,
      double totalVirtualMemory,
      double freeVirtualMemory,
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
    return reportDate;
  }

  public String getContainerName() {
    return containerName;
  }

  public double getTotalPhysicalMemory() {
    return totalPhysicalMemory;
  }

  public double getFreePhysicalMemory() {
    return freePhysicalMemory;
  }

  public double getTotalVirtualMemory() {
    return totalVirtualMemory;
  }

  public double getFreeVirtualMemory() {
    return freeVirtualMemory;
  }

  public double getSystemLoadAverage() {
    return systemLoadAverage;
  }

  public String getOperatingSystemName() {
    return operatingSystemName;
  }

  public String getVirtualMachineName() {
    return virtualMachineName;
  }

  public boolean hasError() {
    return hasError;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public ReactiveJadeMap toMap() {
    return (new ReactiveJadeMap())
      .putBoolean("hasError", hasError)
      .putString("errorMessage", errorMessage)
      .putString("reportDate", reportDate.toString())
      .putString("containerName", containerName)
      .putDouble("totalPhysicalMemory", totalPhysicalMemory)
      .putDouble("freePhysicalMemory", freePhysicalMemory)
      .putDouble("totalVirtualMemory", totalVirtualMemory)
      .putDouble("freeVirtualMemory", freeVirtualMemory)
      .putDouble("systemLoadAverage", systemLoadAverage)
      .putString("operatingSystemName", operatingSystemName)
      .putString("virtualMachineName", virtualMachineName);      
  }
}