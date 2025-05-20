package edu.austral.ingsis.clifford.results;

import edu.austral.ingsis.clifford.FileSystem;

import java.util.Optional;

public class ErrorCommandResult implements CommandResult {
  private final String errorMessage;

  public ErrorCommandResult(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public boolean isSuccess() {
    return false;
  }

  @Override
  public String message() {
    return errorMessage;
  }

  @Override
  public Optional<FileSystem> newFileSystem() {
    return Optional.empty();
  }
}
