package com.vaninhosoftware.libraryapi.controller.common;

import com.vaninhosoftware.libraryapi.controller.dto.ErroCampo;
import com.vaninhosoftware.libraryapi.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice   //Capturar as exeç~es e dar uma resposata Rest, uma resposata de erro tratada ou resposata de sucesso
public class GlobalExceptionHandler {

  //Visto na aula 93
  @ExceptionHandler(MethodArgumentNotValidException.class)   //Anotaçã que captura o erro (O erro deve ser passado na frente da anotação) e joga no parametro do metodo
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)   //Serve para mapear o retorno do metodo, nesse caso o retorno é sempre o mesmo erro, então uso o retorno do erro na frente da anotação
  public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e)  {
    List<FieldError> fieldErrors = e.getFieldErrors();
    List<ErroCampo> listaErros = fieldErrors
            .stream()
            .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
            .collect(Collectors.toList());
    return new ErroResposta(
           HttpStatus.UNPROCESSABLE_ENTITY.value(),
           "Errode validação",
           listaErros);
  }
}
