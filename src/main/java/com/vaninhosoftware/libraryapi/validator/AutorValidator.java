package com.vaninhosoftware.libraryapi.validator;

import com.vaninhosoftware.libraryapi.exceptions.RegistroDuplicadoException;
import com.vaninhosoftware.libraryapi.model.Autor;
import com.vaninhosoftware.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

  private AutorRepository repository;

  public AutorValidator(AutorRepository repository) {
    this.repository = repository;
  }

  public void validar(Autor autor)  {
    if(existeAutorCadastrado(autor))  {
        throw new RegistroDuplicadoException("Autor já cadastrado");
    }
  }

  private boolean existeAutorCadastrado(Autor autor)  {
    Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
        autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
    );

    if(autor.getId() == null){
      return autorEncontrado.isPresent();
    }

    return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
  }


}
