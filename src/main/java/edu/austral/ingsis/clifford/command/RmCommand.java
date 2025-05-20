package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.results.CommandResult;
import edu.austral.ingsis.clifford.results.ErrorCommandResult;
import edu.austral.ingsis.clifford.results.SuccessCommandResult;

import java.util.Optional;

public class RmCommand extends AbstractCommand {
  @Override
  protected boolean validateArgs(String[] args) {
    if (args == null || args.length == 0) {
      return false;
    }

    // Check for recursive flag
    if (args.length > 1 && args[0].equals("--recursive")) {
      return args.length == 2;
    }

    return args.length == 1;
  }

  @Override
  protected String getUsageMessage() {
    return "Usage: rm [--recursive] <name>";
  }

  @Override
  protected CommandResult executeValidated(String[] args, FileSystem fileSystem) {
    Directory current = fileSystem.getCurrentDirectory();

    // Check if it's a recursive command to remove directories
    if (args.length > 1 && args[0].equals("--recursive")) {
      String dirName = args[1];
      Directory dirToRemove = current.findDirectory(dirName);

      if (dirToRemove == null) {
        return new ErrorCommandResult("Directory: '" + dirName + "' does not exist in this directory");
      }

      FileSystem newFileSystem = fileSystem.removeElement(dirName);
      return new SuccessCommandResult("'" + dirName + "' removed", Optional.of(newFileSystem));
    } else {
      // Remove file or non-recursive directory
      String elementName = args[0];
      File fileToRemove = current.findFile(elementName);

      if (fileToRemove == null) {
        // Check if it's a directory
        Directory dirToRemove = current.findDirectory(elementName);
        if (dirToRemove != null) {
          return new ErrorCommandResult("cannot remove '" + elementName + "', is a directory");
        }
        return new ErrorCommandResult("File: '" + elementName + "' does not exist in this directory");
      }

      FileSystem newFileSystem = fileSystem.removeElement(elementName);
      return new SuccessCommandResult("'" + elementName + "' removed", Optional.of(newFileSystem));
    }
  }
}