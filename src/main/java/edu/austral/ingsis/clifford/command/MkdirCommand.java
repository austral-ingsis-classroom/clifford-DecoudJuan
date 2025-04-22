package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;

public class MkdirCommand implements Command {
  @Override
  public String execute(FileSystem fileSystem, String[] args) {
    if (args.length < 2) {
      return "No directory name provided";
    }

    if (args.length > 2) {
      return "Directory name cannot include spaces";
    }

    String dirName = args[1];
    Directory current = fileSystem.getCurrentDirectory();

    if (!current.isValidName(dirName)) {
      return dirName + " is an invalid directory name";
    }

    Directory newDir = new Directory(dirName, current);
    current.addDirectory(newDir);

    return "'" + dirName + "' directory created";
  }
}
