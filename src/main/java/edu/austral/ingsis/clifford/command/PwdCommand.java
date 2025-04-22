package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.FileSystem;

public class PwdCommand implements Command {
  @Override
  public String execute(FileSystem fileSystem, String[] args) {
    return fileSystem.getCurrentPath();
  }
}
