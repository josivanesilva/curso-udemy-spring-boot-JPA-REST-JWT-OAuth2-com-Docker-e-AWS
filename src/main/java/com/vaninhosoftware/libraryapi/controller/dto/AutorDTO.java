package com.vaninhosoftware.libraryapi.controller.dto;

import com.vaninhosoftware.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
    UUID id,

    @NotBlank(message = "campo obrigatório")   //É usado para String, dizendo que não pode vim nula e nem vazia "Obrigatorio passar na requisição o comando
    @Size(min = 2, max = 100, message = "campo fora do tamanho padrão")
    String nome,

    @NotNull(message = "campo obrigatório")
    @Past(message = "não pode ser uma data futura")
    LocalDate dataNascimento,

    @NotBlank(message = "campo obrigatório")
    @Size(min = 2, max = 50, message = "campo fora do tamanho padrão")
    String nacionalidade) {

  public Autor mapearParaAutor(){
    Autor autor = new Autor();
    autor.setNome(this.nome);
    autor.setDataNascimento(this.dataNascimento);
    autor.setNacionalidade(this.nacionalidade);
    return autor;
  }
}
