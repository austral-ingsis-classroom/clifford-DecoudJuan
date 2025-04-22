package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.command.CdCommand;
import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.command.LsCommand;
import edu.austral.ingsis.clifford.command.MkdirCommand;
import edu.austral.ingsis.clifford.command.PwdCommand;
import edu.austral.ingsis.clifford.command.RmCommand;
import edu.austral.ingsis.clifford.command.TouchCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandExecutor {
  private final FileSystem fileSystem = new FileSystem();
  private final Map<String, Command> commands = new HashMap<>();

  public CommandExecutor() {
    // Registrar todos los comandos disponibles
    registerCommand("ls", new LsCommand());
    registerCommand("cd", new CdCommand());
    registerCommand("touch", new TouchCommand());
    registerCommand("mkdir", new MkdirCommand());
    registerCommand("rm", new RmCommand());
    registerCommand("pwd", new PwdCommand());
  }

  public void registerCommand(String name, Command command) {
    commands.put(name, command);
  }

  public List<String> executeCommands(List<String> commandLines) {
    List<String> results = new ArrayList<>();
    for (String commandLine : commandLines) {
      String result = inputCommand(commandLine);
      results.add(result);
    }
    return results;
  }

  public String inputCommand(String input) {
    if (input == null || input.isEmpty()) {
      return "Empty input";
    }

    String[] args = input.split(" ");
    String commandName = args[0];

    Command command = commands.get(commandName);
    if (command == null) {
      return "Command '" + commandName + "' does not exist";
    }

    return command.execute(fileSystem, args);
  }
}
