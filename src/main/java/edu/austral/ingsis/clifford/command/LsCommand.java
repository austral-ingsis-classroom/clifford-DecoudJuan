package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.results.CommandResult;
import edu.austral.ingsis.clifford.results.ErrorCommandResult;
import edu.austral.ingsis.clifford.results.SuccessCommandResult;

import java.util.List;
import java.util.Optional;

public class LsCommand extends AbstractCommand {
  @Override
  protected boolean validateArgs(String[] args) {
    return args == null || args.length <= 1;
  }

  @Override
  protected String getUsageMessage() {
    return "Usage: ls [--ord=asc|--ord=desc]";
  }

  @Override
  protected CommandResult executeValidated(String[] args, FileSystem fileSystem) {
    Directory current = fileSystem.getCurrentDirectory();
    String order = null;

    if (args != null && args.length == 1) {
      if (args[0].equals("--ord=asc")) {
        order = "asc";
      } else if (args[0].equals("--ord=desc")) {
        order = "desc";
      } else {
        return new ErrorCommandResult("Unknown flag");
      }
    }

    List<String> elements = current.listElements(order);
    String result = !elements.isEmpty() ? String.join(" ", elements) : "";
    return new SuccessCommandResult(result, Optional.empty());
  }
}