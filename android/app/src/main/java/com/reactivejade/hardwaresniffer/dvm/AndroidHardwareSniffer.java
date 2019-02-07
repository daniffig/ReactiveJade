package hardwaresniffer;

import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.System;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Documentación sobre cómo utilizar la información disponible en /proc
// https://linux.die.net/man/5/proc
// http://www.brendangregg.com/blog/2017-08-08/linux-load-averages.html

public class AndroidHardwareSniffer extends HardwareSniffer {

  // Documentación útil sobre expresiones regulares en Java
  // http://www.vogella.com/tutorials/JavaRegularExpressions/article.html

  // Para realizar pruebas con expresiones regulares en Java
  // https://www.freeformatter.com/java-regex-tester.html

  private static final String PROC_CPUINFO = "/proc/cpuinfo";
  private static final String PROC_MEMINFO = "/proc/meminfo";
  private static final Pattern MEMTOTAL_REGEX = Pattern.compile("^MemTotal:\\s*(\\d+)\\skB$");
  private static final Pattern MEMFREE_REGEX = Pattern.compile("^MemFree:\\s*(\\d+)\\skB$");

  private final List<String> cpuinfo;

  public AndroidHardwareSniffer() {
    super();
    
    this.cpuinfo = populateList(PROC_CPUINFO);
  }

  @Override
  public double getSystemLoadAverage() {

    double systemLoadAverage = 0.0;

    systemLoadAverage = 14.14;

    return systemLoadAverage;
  }

  @Override
  public double getTotalPhysicalMemorySize() {

    // Si bien la memoria física de un sistema no suele cambiar,
    // en el caso en que trabajemos sobre máquinas virtuales (por ejemplo, AWS)
    // puede ocurrir que exista una asignación de memoria on-the-fly.
    // Es por ello que volvemos a consultar el archivo PROC_MEMINFO.
    List<String> meminfo = populateList(PROC_MEMINFO);

    double totalPhysicalMemory = 0.0;

    // Leemos la primera línea, que corresponde con la memoria física total,
    // y extraemos el valor expresador en KiB.
    Matcher matcher = MEMTOTAL_REGEX.matcher(meminfo.get(0));

    if (matcher.find()) {
      totalPhysicalMemory = Double.parseDouble(matcher.group(1));
    }

    return totalPhysicalMemory;
  }

  @Override
  public double getFreePhysicalMemorySize() {

    List<String> meminfo = populateList(PROC_MEMINFO);

    double freePhysicalMemory = 0.0;

    // Leemos la segunda línea, que corresponde con la memoria física libre,
    // y extraemos el valor expresador en KiB.
    Matcher matcher = MEMFREE_REGEX.matcher(meminfo.get(1));

    if (matcher.find()) {
      freePhysicalMemory = Double.parseDouble(matcher.group(1));
    }

    return freePhysicalMemory;
  }

  // Cargamos la información de los archivos de sistema en una lista,
  // de manera tal de poder acceder fácilmente a los datos disponibles
  // en cada línea.
  private List<String> populateList(String file) {

    List<String> list = new ArrayList<String>();

    try {
      FileReader localFileReader = new FileReader(file);
      BufferedReader localBufferedReader = new BufferedReader(localFileReader);

      String line;

      while ((line = localBufferedReader.readLine()) != null) {
        list.add(line);
      }

      localBufferedReader.close();

    } catch (Exception e) {
      
    }

    return list;
  }
  
}