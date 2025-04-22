package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.FileSystem;

public class TouchCommand implements Command {
  @Override
  public String execute(FileSystem fileSystem, String[] args) {
    if (args.length < 2) {
      return "No file name provided";
    }

    if (args.length > 2) {
      return "File name cannot include spaces";
    }

    String fileName = args[1];
    Directory current = fileSystem.getCurrentDirectory();

    if (!current.isValidName(fileName)) {
      return fileName + " is an invalid file name";
    }

    File newFile = new File(fileName);
    current.addFile(newFile);

    return "'" + fileName + "' file created";
  }
}
