package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSystem {
  private final Directory root = new Directory("/", null);
  private Directory current = root;

  public FileSystem() {
    // Inicializa el sistema de archivos con el directorio raíz
  }

  public Directory getCurrentDirectory() {
    return current;
  }

  public Directory getRoot() {
    return root;
  }

  public void changeDirectory(Directory dir) {
    if (dir != null) {
      current = dir;
    }
  }

  public String getCurrentPath() {
    List<String> path = new ArrayList<>();
    Directory currentDir = current;

    while (currentDir != null && !currentDir.getName().equals("/")) {
      path.add(currentDir.getName());
      currentDir = currentDir.getParent();
    }

    Collections.reverse(path);
    return "/" + String.join("/", path);
  }

  // Método auxiliar para navegar a un directorio a partir de una ruta
  public Directory navigateTo(String path) {
    if (path.equals(".")) {
      return current;
    } else if (path.equals("..")) {
      return current.getParent() != null ? current.getParent() : current;
    }

    Directory startDir;
    String[] components;

    if (path.startsWith("/")) {
      startDir = root;
      components = path.substring(1).split("/");
    } else {
      startDir = current;
      components = path.split("/");
    }

    return navigateRecursive(startDir, components, 0);
  }

  private Directory navigateRecursive(Directory currentDir, String[] components, int index) {
    if (index >= components.length || components[index].isEmpty()) {
      return currentDir;
    }

    Directory nextDir = currentDir.findDirectory(components[index]);
    if (nextDir == null) {
      return null;
    }

    if (index == components.length - 1) {
      return nextDir;
    } else {
      return navigateRecursive(nextDir, components, index + 1);
    }
  }
}
