package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.results.CommandResult;
import edu.austral.ingsis.clifford.results.ErrorCommandResult;
import edu.austral.ingsis.clifford.results.SuccessCommandResult;

import java.util.Optional;

public class CdCommand extends AbstractCommand {
  @Override
  protected boolean validateArgs(String[] args) {
    return args != null && args.length == 1;
  }

  @Override
  protected String getUsageMessage() {
    return "You must specify the directory of destination";
  }

  @Override
  protected CommandResult executeValidated(String[] args, FileSystem fileSystem) {
    String path = args[0];
    Directory current = fileSystem.getCurrentDirectory();

    // Special case for "."
    if (path.equals(".")) {
      return new SuccessCommandResult("moved to directory '" + current.getName() + "'", Optional.empty());
    }

    // Special case for ".."
    if (path.equals("..")) {
      if (current.getParent() != null) {
        FileSystem newFileSystem = fileSystem.changeDirectory(current.getParent());
        return new SuccessCommandResult("moved to directory '" + current.getParent().getName() + "'",
            Optional.of(newFileSystem));
      }
      return new SuccessCommandResult("moved to directory '" + current.getName() + "'", Optional.empty());
    }

    // Navigate to a directory
    Directory targetDir = fileSystem.navigateTo(path);

    if (targetDir == null) {
      // Check if it's a file instead of a directory
      String finalComponent = path;
      if (path.contains("/")) {
        // Extract the last component of the path
        String[] pathComponents = path.split("/");
        finalComponent = pathComponents[pathComponents.length - 1];

        // Try to navigate to the parent directory
        String parentPath = path.substring(0, path.lastIndexOf('/'));
        Directory parentDir = parentPath.isEmpty() ? current : fileSystem.navigateTo(parentPath);

        if (parentDir != null) {
          // Check if the last component is a file
          File file = parentDir.findFile(finalComponent);
          if (file != null) {
            return new ErrorCommandResult("The name given is a file, not a directory");
          }
        }

        return new ErrorCommandResult("Route " + path + " does not exist");
      } else {
        File file = current.findFile(finalComponent);
        if (file != null) {
          return new ErrorCommandResult("The name given is a file, not a directory");
        }
        return new ErrorCommandResult("'" + finalComponent + "' directory does not exist");
      }
    }

    FileSystem newFileSystem = fileSystem.changeDirectory(targetDir);
    return new SuccessCommandResult("moved to directory '" + targetDir.getName() + "'",
        Optional.of(newFileSystem));
  }

}