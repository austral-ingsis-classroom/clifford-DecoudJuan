package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.FileSystem;

public interface Command {
  String execute(FileSystem fileSystem, String[] args);
}
