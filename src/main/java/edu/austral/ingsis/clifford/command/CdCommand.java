package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.FileSystem;

public class CdCommand implements Command {
  @Override
  public String execute(FileSystem fileSystem, String[] args) {
    if (args.length < 2) {
      return "You must specify the directory of destination";
    }

    String path = args[1];
    Directory current = fileSystem.getCurrentDirectory();

    // Caso especial para "."
    if (path.equals(".")) {
      return "moved to directory '" + current.getName() + "'";
    }

    // Caso especial para ".."
    if (path.equals("..")) {
      if (current.getParent() != null) {
        fileSystem.changeDirectory(current.getParent());
        return "moved to directory '" + current.getParent().getName() + "'";
      }
      return "moved to directory '" + current.getName() + "'";
    }

    // Navegación a un directorio
    Directory targetDir = fileSystem.navigateTo(path);

    if (targetDir == null) {
      // Verificar si es un archivo en vez de un directorio
      if (path.contains("/")) {
        return "Route " + path + " does not exist";
      } else {
        File file = current.findFile(path);
        if (file != null) {
          return "The name given is a file, not a directory";
        }
        return "'" + path + "' directory does not exist";
      }
    }

    fileSystem.changeDirectory(targetDir);
    return "moved to directory '" + targetDir.getName() + "'";
  }
}
