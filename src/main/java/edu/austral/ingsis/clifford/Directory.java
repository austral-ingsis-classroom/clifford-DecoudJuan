package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Directory extends FileSystemElement {
  private final Directory parent;
  private final List<FileSystemElement> children;

  public Directory(String name, Directory parent) {
    super(name);
    this.parent = parent;
    this.children = Collections.emptyList();
  }

  // Constructor privado para inmutabilidad
  private Directory(String name, Directory parent, List<FileSystemElement> children) {
    super(name);
    this.parent = parent;
    this.children = List.copyOf(children);
  }

  public Directory getParent() {
    return parent;
  }

  public List<FileSystemElement> getChildren() {
    return children;
  }

  public Directory addElement(FileSystemElement element) {
    if (element == null || !isValidName(element.getName())) {
      return this;
    }

    // Verificar si ya existe un elemento con el mismo nombre
    for (FileSystemElement child : children) {
      if (child.getName().equals(element.getName())) {
        return this;
      }
    }

    List<FileSystemElement> newChildren = new ArrayList<>(children);
    newChildren.add(element);

    return new Directory(this.name, this.parent, newChildren);
  }

  public Directory removeElement(String elementName) {
    if (elementName == null) {
      return this;
    }

    List<FileSystemElement> newChildren = new ArrayList<>();
    boolean removed = false;

    for (FileSystemElement element : children) {
      if (!element.getName().equals(elementName)) {
        newChildren.add(element);
      } else {
        removed = true;
      }
    }

    if (!removed) {
      return this;
    }

    return new Directory(this.name, this.parent, newChildren);
  }

  public Directory findDirectory(String name) {
    if (name == null) {
      return null;
    }

    for (FileSystemElement element : children) {
      if (element instanceof Directory && element.getName().equals(name)) {
        return (Directory) element;
      }
    }
    return null;
  }

  public File findFile(String name) {
    if (name == null) {
      return null;
    }

    for (FileSystemElement element : children) {
      if (element instanceof File && element.getName().equals(name)) {
        return (File) element;
      }
    }
    return null;
  }

  public List<String> listElements(String order) {
    List<String> elementNames = new ArrayList<>();

    for (FileSystemElement element : children) {
      elementNames.add(element.getName());
    }

    if (order != null) {
      if (order.equals("asc")) {
        elementNames.sort(Comparator.naturalOrder());
      } else if (order.equals("desc")) {
        elementNames.sort(Comparator.reverseOrder());
      }
    }

    return Collections.unmodifiableList(elementNames);
  }

  public boolean isValidName(String name) {
    return name != null && !name.contains("/") && !name.contains(" ");
  }
}