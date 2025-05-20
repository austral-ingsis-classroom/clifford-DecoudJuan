package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.results.CommandResult;

public interface Command {
  CommandResult execute(String[] args, FileSystem fileSystem);
}
