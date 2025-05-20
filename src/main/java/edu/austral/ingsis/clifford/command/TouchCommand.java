package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.results.CommandResult;
import edu.austral.ingsis.clifford.results.ErrorCommandResult;
import edu.austral.ingsis.clifford.results.SuccessCommandResult;

import java.util.Optional;

public class TouchCommand extends AbstractCommand {
  @Override
  protected boolean validateArgs(String[] args) {
    return args != null && args.length == 1 && args[0] != null && !args[0].isEmpty();
  }

  @Override
  protected String getUsageMessage() {
    return "No file name provided";
  }

  @Override
  protected CommandResult executeValidated(String[] args, FileSystem fileSystem) {
    String fileName = args[0];
    Directory current = fileSystem.getCurrentDirectory();

    if (!current.isValidName(fileName)) {
      return new ErrorCommandResult(fileName + " is an invalid file name");
    }

    if (current.findFile(fileName) != null || current.findDirectory(fileName) != null) {
      return new ErrorCommandResult("Element already exists: " + fileName);
    }

    // Create file with empty content
    FileSystem newFileSystem = fileSystem.addFile(fileName, "");
    return new SuccessCommandResult("'" + fileName + "' file created", Optional.of(newFileSystem));
  }
}