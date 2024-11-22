package com.vaninhosoftware.libraryapi.exceptions;

public class RegistroDuplicadoException  extends RuntimeException{
  public RegistroDuplicadoException(String mensagem)  {
    super(mensagem);
  }
}
