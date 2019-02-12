package hardwaresniffer;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jade.core.ContainerID;

import hardwaresniffer.HardwareSnifferAgent;

public class HardwareSnifferLogHandler extends ConsoleHandler {

  private static final Pattern UNREACHABLE_DESTINATION_ERROR_REGEX =
    Pattern.compile("^Destination (.*) does not exist or does not support mobility$");    

  private HardwareSnifferAgent agent;

  public HardwareSnifferLogHandler(HardwareSnifferAgent agent) {
    super();

    this.agent = agent;
  }

  public void publish(LogRecord record) {
    String errorMessage = record.getMessage();

    Matcher matcher = UNREACHABLE_DESTINATION_ERROR_REGEX.matcher(errorMessage);

    if (matcher.find()) {
      agent.onMoveError(
        new ContainerID(matcher.group(1), null),
        errorMessage
      );
    }
    
    super.publish(record);
  }

}