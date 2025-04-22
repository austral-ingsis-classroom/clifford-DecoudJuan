package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import java.util.List;

public class LsCommand implements Command {
  @Override
  public String execute(FileSystem fileSystem, String[] args) {
    Directory current = fileSystem.getCurrentDirectory();
    String order = null;

    if (args.length > 1) {
      if (args[1].equals("--ord=asc")) {
        order = "asc";
      } else if (args[1].equals("--ord=desc")) {
        order = "desc";
      } else {
        return "Unknown flag";
      }
    }

    List<String> elements = current.listElements(order);
    return !elements.isEmpty() ? String.join(" ", elements) : "";
  }
}
