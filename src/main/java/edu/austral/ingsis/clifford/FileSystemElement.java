package edu.austral.ingsis.clifford;

public abstract class FileSystemElement {
  protected final String name;

  public FileSystemElement(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
