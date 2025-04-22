package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Directory extends FileSystemElement {
  private final Directory parent;
  private final List<Directory> directories = new ArrayList<>();
  private final List<File> files = new ArrayList<>();
  private final List<String> all = new ArrayList<>();

  public Directory(String name, Directory parent) {
    super(name);
    this.parent = parent;
  }

  public Directory getParent() {
    return parent;
  }

  public List<Directory> getDirectories() {
    return directories;
  }

  public List<File> getFiles() {
    return files;
  }

  public List<String> getAllElements() {
    return all;
  }

  public void addFile(File file) {
    files.add(file);
    all.add(file.getName());
  }

  public void addDirectory(Directory directory) {
    directories.add(directory);
    all.add(directory.getName());
  }

  public void removeFile(File file) {
    files.remove(file);
    all.remove(file.getName());
  }

  public void removeDirectory(Directory directory) {
    directories.remove(directory);
    all.remove(directory.getName());
  }

  public Directory findDirectory(String name) {
    for (Directory dir : directories) {
      if (dir.getName().equals(name)) {
        return dir;
      }
    }
    return null;
  }

  public File findFile(String name) {
    for (File file : files) {
      if (file.getName().equals(name)) {
        return file;
      }
    }
    return null;
  }

  public List<String> listElements(String order) {
    List<String> result = new ArrayList<>();

    for (String element : all) {
      if (!result.contains(element)) {
        result.add(element);
      }
    }

    if (order != null) {
      if (order.equals("asc")) {
        result.sort(Comparator.naturalOrder());
      } else if (order.equals("desc")) {
        result.sort(Comparator.reverseOrder());
      }
    }

    return result;
  }

  public boolean isValidName(String name) {
    return !name.contains("/") && !name.contains(" ");
  }
}
