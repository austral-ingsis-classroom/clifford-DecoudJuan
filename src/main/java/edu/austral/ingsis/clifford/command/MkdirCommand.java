package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.results.CommandResult;
import edu.austral.ingsis.clifford.results.ErrorCommandResult;
import edu.austral.ingsis.clifford.results.SuccessCommandResult;

import java.util.Optional;

public class MkdirCommand extends AbstractCommand {
  @Override
  protected boolean validateArgs(String[] args) {
    return args != null && args.length == 1 && args[0] != null && !args[0].isEmpty();
  }

  @Override
  protected String getUsageMessage() {
    return "No directory name provided";
  }

  @Override
  protected CommandResult executeValidated(String[] args, FileSystem fileSystem) {
    String dirName = args[0];
    Directory current = fileSystem.getCurrentDirectory();

    if (!current.isValidName(dirName)) {
      return new ErrorCommandResult(dirName + " is an invalid directory name");
    }

    if (current.findDirectory(dirName) != null || current.findFile(dirName) != null) {
      return new ErrorCommandResult("Element already exists: " + dirName);
    }

    FileSystem newFileSystem = fileSystem.addDirectory(dirName);
    return new SuccessCommandResult("'" + dirName + "' directory created", Optional.of(newFileSystem));
  }
}