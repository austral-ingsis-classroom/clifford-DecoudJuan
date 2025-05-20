package edu.austral.ingsis.clifford.results;

import edu.austral.ingsis.clifford.FileSystem;

import java.util.Optional;

// Interfaz para resultados de comandos
public interface CommandResult {
  boolean isSuccess();
  String message();
  Optional<FileSystem> newFileSystem();
}

