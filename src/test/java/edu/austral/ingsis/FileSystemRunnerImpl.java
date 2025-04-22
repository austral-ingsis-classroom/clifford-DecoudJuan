package edu.austral.ingsis;

import edu.austral.ingsis.clifford.CommandExecutor;
import java.util.List;

public class FileSystemRunnerImpl implements FileSystemRunner {
  private final CommandExecutor executor = new CommandExecutor();

  public List<String> executeCommands(List<String> commands) {
    return executor.executeCommands(commands);
  }
}
