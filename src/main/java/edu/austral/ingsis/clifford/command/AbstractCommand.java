package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.results.CommandResult;
import edu.austral.ingsis.clifford.results.ErrorCommandResult;

public abstract class AbstractCommand implements Command{

  @Override
  public CommandResult execute(String[] args, FileSystem fileSystem) {
    if (!validateArgs(args)) {
      return new ErrorCommandResult(getUsageMessage());
    }

    return executeValidated(args, fileSystem);
  }

  protected abstract boolean validateArgs(String[] args);
  protected abstract CommandResult executeValidated(String[] args, FileSystem fileSystem);
  protected abstract String getUsageMessage();
}
