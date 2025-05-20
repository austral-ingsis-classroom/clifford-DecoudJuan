package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.command.*;
import edu.austral.ingsis.clifford.results.CommandResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandExecutor {
  private FileSystem fileSystem;
  private final Map<String, Command> commands = new HashMap<>();

  public CommandExecutor() {
    this.fileSystem = new FileSystem();
    registerDefaultCommands();
  }

  private void registerDefaultCommands() {
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

    String[] parts = input.split(" ", 2);
    String commandName = parts[0];

    Command command = commands.get(commandName);
    if (command == null) {
      return "Command '" + commandName + "' does not exist";
    }

    String[] args = parts.length > 1 ? parseArgs(parts[1]) : new String[0];

    CommandResult result = command.execute(args, fileSystem);

    // Update the file system if needed
    result.newFileSystem().ifPresent(fs -> this.fileSystem = fs);

    return result.message();
  }

  private String[] parseArgs(String argsStr) {
    if (argsStr == null || argsStr.trim().isEmpty()) {
      return new String[0];
    }
    return argsStr.trim().split("\\s+");
  }
}