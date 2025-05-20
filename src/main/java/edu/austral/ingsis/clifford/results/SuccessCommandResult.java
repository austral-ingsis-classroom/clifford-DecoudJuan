package edu.austral.ingsis.clifford.results;

import edu.austral.ingsis.clifford.FileSystem;

import java.util.Optional;

public record SuccessCommandResult(String message,
                                   Optional<FileSystem> newFileSystem) implements CommandResult {

  @Override
  public boolean isSuccess() {
    return true;
  }
}
