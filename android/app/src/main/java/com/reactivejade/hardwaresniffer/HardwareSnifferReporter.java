package hardwaresniffer;

import java.lang.System;
import java.util.List;
import java.util.logging.Level;

import android.util.Log;

import jade.util.Logger;

import reactivejade.ReactiveJadeEvent;
import reactivejade.ReactiveJadeMap;
import reactivejade.ReactiveJadeSubscribable;

import hardwaresniffer.HardwareSnifferReport;

public class HardwareSnifferReporter implements ReactiveJadeSubscribable {

  private static Logger LOGGER = Logger.getJADELogger(HardwareSnifferReporter.class.getName());
  private static String BR = System.lineSeparator();
  private static String TAB = "\t";
  private static String DIVIDER = "----------";
  private static double MiB = 1024.0;

  public void receiveReactiveJadeEvent(ReactiveJadeEvent event) {
    ReactiveJadeMap params = event.getParams();

    switch (event.getEventName()) {
      case "reportList":
        printReportList(params);
        break;
      case "log":
        LOGGER.log(Level.INFO, (String) params.get("message"));
        break;
    }
  }

  @SuppressWarnings("unchecked")
  private void printReportList(ReactiveJadeMap params) {
    Log.w("ReactNative", "HardwareSnifferReporter > printReportList");

    String header = 
      String.format("Reported by %s at %s taking %sms",
        params.get("agentName"), params.get("sentAt"), params.get("elapsedTime")
      ) + BR;

    String reportList = "";

    for (HardwareSnifferReport report : (List<HardwareSnifferReport>) params.get("reportList")) {
      Log.w("ReactNative", "HardwareSnifferReporter > printReportList > " + report.getContainerName());
      if (report.hasError()) {
        Log.w("ReactNative", "HardwareSnifferReporter > printReportList > report has error");
        reportList = reportList +
          TAB + "Container.Name: " + report.getContainerName() + BR +
          TAB + "Report.Date: " + report.getReportDate().toString() + BR +
          TAB + "Report.hasError: " + String.valueOf(report.hasError()) + BR +
          TAB + "Report.errorMessage: " + report.getErrorMessage() + BR;
      } else {
        reportList = reportList +
          TAB + "Container.Name: " + report.getContainerName() + BR +
          TAB + "Report.Date: " + report.getReportDate().toString() + BR +
          TAB + "Report.hasError: " + String.valueOf(report.hasError()) + BR +
          TAB + "PhysicalMemory.Total: " + formatMemory(report.getTotalPhysicalMemory()) + BR +
          TAB + "PhysicalMemory.Free: " + formatMemory(report.getFreePhysicalMemory()) + BR +
          TAB + "VirtualMemory.Total: " + formatMemory(report.getTotalVirtualMemory()) + BR +
          TAB + "VirtualMemory.Free: " + formatMemory(report.getFreeVirtualMemory()) + BR +
          TAB + "System.LoadAverage: " + String.valueOf(report.getSystemLoadAverage()) + BR +
          TAB + "OperatingSystem.Name: " + report.getOperatingSystemName() + BR +
          TAB + "VirtualMachine.Name: " + report.getVirtualMachineName() + BR;
      }      
      
      reportList = reportList + 
        TAB + DIVIDER + BR;  
    }

    LOGGER.log(Level.INFO, header + reportList);
  }

  private String formatMemory(Double memory) {
    return String.format("%.2f MiB", memory / MiB);
  }


}