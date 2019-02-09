package hardwaresniffer;

import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.System;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Documentacion sobre como utilizar la informacion disponible en /proc
// https://linux.die.net/man/5/proc
// http://www.brendangregg.com/blog/2017-08-08/linux-load-averages.html

public class LinuxHardwareSniffer extends HardwareSniffer {

  // Documentacion util sobre expresiones regulares en Java
  // http://www.vogella.com/tutorials/JavaRegularExpressions/article.html

  // Para realizar pruebas con expresiones regulares en Java
  // https://www.freeformatter.com/java-regex-tester.html

  private static final String PROC_CPUINFO = "/proc/cpuinfo";
  private static final String PROC_LOADAVG = "/proc/loadavg";
  private static final String PROC_MEMINFO = "/proc/meminfo";
  private static final Pattern MEMTOTAL_REGEX = Pattern.compile("^MemTotal:\\s*(\\d+)\\skB$");
  private static final Pattern MEMFREE_REGEX = Pattern.compile("^MemFree:\\s*(\\d+)\\skB$");
  private static final Pattern LOADAVG_REGEX = Pattern.compile("^(\\d+\\.\\d+)\\s(\\d+\\.\\d+)\\s(\\d+\\.\\d+)\\s(\\d+\\/\\d+)\\s(\\d+)$");

  // Finalmente, esto no lo usamos, pero conservamos el codigo
  // en caso de agregar alguna funcionalidad en el futuro.
  private final List<String> cpuinfo;

  public LinuxHardwareSniffer() {
    super();

    this.cpuinfo = this.populateList(PROC_CPUINFO);
  }

  @Override
  public double getSystemLoadAverage() {

    List<String> loadavg = populateList(PROC_LOADAVG);

    double systemLoadAverage = 0.0;

    if (loadavg != null) {
      // Leemos la unica linea, que devuelve el promedio de carga del sistema
      // en los ultimos 1, 5, y 15 minutos.
      Matcher matcher = LOADAVG_REGEX.matcher(loadavg.get(0));

      // Devolvemos el primer valor, que corresponde con el promedio de carga del sistema
      // en el ultimo minuto.
      if (matcher.find()) {
        systemLoadAverage = Double.parseDouble(matcher.group(1));
      }
    }

    return systemLoadAverage;
  }

  @Override
  public double getTotalPhysicalMemorySize() {

    // Si bien la memoria fisica de un sistema no suele cambiar,
    // en el caso en que trabajemos sobre maquinas virtuales (por ejemplo, AWS)
    // puede ocurrir que exista una asignacion de memoria on-the-fly.
    // Es por ello que volvemos a consultar el archivo PROC_MEMINFO.
    List<String> meminfo = populateList(PROC_MEMINFO);

    double totalPhysicalMemory = 0L;

    // Leemos la primera linea, que corresponde con la memoria fisica total,
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

    // Leemos la segunda linea, que corresponde con la memoria fisica libre,
    // y extraemos el valor expresador en KiB.
    Matcher matcher = MEMFREE_REGEX.matcher(meminfo.get(1));

    if (matcher.find()) {
      freePhysicalMemory = Double.parseDouble(matcher.group(1));
    }

    return freePhysicalMemory;
  }

  // Cargamos la informacion de los archivos de sistema en una lista,
  // de manera tal de poder acceder facilmente a los datos disponibles
  // en cada linea.
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
      // TODO      
    }

    return list;
  }
  
}