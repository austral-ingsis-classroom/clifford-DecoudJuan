package edu.austral.ingsis.clifford;

public class File extends FileSystemElement {
  private final String content;

  public File(String name, String content) {
    super(name);
    this.content = content != null ? content : "";
  }

  public String getContent() {
    return content;
  }

  // Supports modifying content while maintaining immutability pattern
  public File withContent(String newContent) {
    if (newContent == null) {
      return this;
    }
    return new File(this.name, newContent);
  }
}
