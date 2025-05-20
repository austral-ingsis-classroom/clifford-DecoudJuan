package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSystem {
  private final Directory root;
  private final Directory current;

  public FileSystem() {
    this.root = new Directory("/", null);
    this.current = this.root;
  }

  private FileSystem(Directory root, Directory current) {
    this.root = root;
    this.current = current;
  }

  public Directory getCurrentDirectory() {
    return current;
  }


  public Directory getRoot() {
    return root;
  }

  public FileSystem changeDirectory(Directory dir) {
    if (dir == null) {
      return this;
    }
    return new FileSystem(this.root, dir);
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

  public Dir
  ectory navigateTo(String path) {
    if (path == null || path.isEmpty()) {
      return current;
    }

    if (path.equals(".")) {
      return current;
    } else if (path.equals("..")) {
      return current.getParent() != null ? current.getParent() : current;
    }

    Directory startDir;
    String[] components;

    if (path.startsWith("/")) {
      startDir = root;
      path = path.substring(1);
    } else {
      startDir = current;
    }

    components = path.isEmpty() ? new String[0] : path.split("/");
    return navigateRecursive(startDir, components, 0);
  }

  private Directory navigateRecursive(Directory currentDir, String[] components, int index) {
    if (currentDir == null || index >= components.length || components[index].isEmpty()) {
      return currentDir;
    }

    if (components[index].equals("..")) {
      return navigateRecursive(currentDir.getParent() != null ? currentDir.getParent() : currentDir,
          components, index + 1);
    } else if (components[index].equals(".")) {
      return navigateRecursive(currentDir, components, index + 1);
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

  public FileSystem addFile(String name, String content) {
    if (name == null || !current.isValidName(name)) {
      return this;
    }

    // Verificar si ya existe
    if (current.findFile(name) != null || current.findDirectory(name) != null) {
      return this;
    }

    File file = new File(name, content);
    Directory updatedCurrent = current.addElement(file);

    return updateFileSystemTree(updatedCurrent);
  }

  public FileSystem addDirectory(String name) {
    if (name == null || !current.isValidName(name)) {
      return this;
    }

    // Verificar si ya existe
    if (current.findFile(name) != null || current.findDirectory(name) != null) {
      return this;
    }

    Directory newDir = new Directory(name, current);
    Directory updatedCurrent = current.addElement(newDir);

    return updateFileSystemTree(updatedCurrent);
  }

  public FileSystem removeElement(String name) {
    if (name == null) {
      return this;
    }

    Directory updatedCurrent = current.removeElement(name);
    return updateFileSystemTree(updatedCurrent);
  }

  // Actualizar el árbol del sistema de archivos
  private FileSystem updateFileSystemTree(Directory updatedDirectory) {
    // Si es el directorio raíz
    if (current.getParent() == null) {
      return new FileSystem(updatedDirectory, updatedDirectory);
    }

    return new FileSystem(this.root, updatedDirectory);
  }
}
