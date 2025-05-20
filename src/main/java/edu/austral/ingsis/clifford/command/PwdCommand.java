package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.results.CommandResult;
import edu.austral.ingsis.clifford.results.SuccessCommandResult;

import java.util.Optional;

public class PwdCommand extends AbstractCommand {
  @Override
  protected boolean validateArgs(String[] args) {
    return args == null || args.length == 0;
  }

  @Override
  protected String getUsageMessage() {
    return "pwd takes no arguments";
  }

  @Override
  protected CommandResult executeValidated(String[] args, FileSystem fileSystem) {
    return new SuccessCommandResult(fileSystem.getCurrentPath(), Optional.empty());
  }
}