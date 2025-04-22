package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.FileSystem;

public class RmCommand implements Command {
  @Override
  public String execute(FileSystem fileSystem, String[] args) {
    if (args.length < 2) {
      return "No file name provided";
    }

    Directory current = fileSystem.getCurrentDirectory();

    // Comprobar si es un comando recursivo para eliminar directorios
    if (args[1].equals("--recursive")) {
      if (args.length < 3) {
        return "No directory name provided";
      }

      String dirName = args[2];
      Directory dirToRemove = current.findDirectory(dirName);

      if (dirToRemove == null) {
        return "Directory: '" + dirName + "' does not exist in this directory";
      }

      current.removeDirectory(dirToRemove);
      return "'" + dirName + "' removed";
    } else {
      // Eliminar archivo
      String fileName = args[1];
      File fileToRemove = current.findFile(fileName);

      if (fileToRemove == null) {
        // Comprobar si es un directorio
        Directory dirToRemove = current.findDirectory(fileName);
        if (dirToRemove != null) {
          return "cannot remove '" + fileName + "', is a directory";
        }
        return "File: '" + fileName + "' does not exist in this directory";
      }

      current.removeFile(fileToRemove);
      return "'" + fileName + "' removed";
    }
  }
}
