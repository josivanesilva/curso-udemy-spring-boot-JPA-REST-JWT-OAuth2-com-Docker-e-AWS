package com.vaninhosoftware.libraryapi.service;

import com.vaninhosoftware.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.vaninhosoftware.libraryapi.model.Autor;
import com.vaninhosoftware.libraryapi.repository.AutorRepository;
import com.vaninhosoftware.libraryapi.repository.LivroRepository;
import com.vaninhosoftware.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor    //Ele faz a criação do construtor das classes injetadas
public class AutorService {

  private final AutorRepository repository;   //A palavra final significa que ele deve ser inicializado ja no construtor
  private final AutorValidator validator;
  private final LivroRepository livroRepository;

 /* public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository livroRepository) {
    this.repository = repository;
    this.validator = validator;
    this.livroRepository = livroRepository;
  }*/   //Esse construtor nao é necessário porque usei a anotação RequiredArgsConstructor

  public Autor salvar(Autor autor)  {
    validator.validar(autor);
    return repository.save(autor);
  }

  public void atualizar(Autor autor)  {
    if(autor.getId() == null){
      throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base.");
    }
    validator.validar(autor);
    repository.save(autor);
  }

  public Optional<Autor> obterPorId(UUID id){
    return repository.findById(id);
  }

  public void deletar(Autor autor){   //Foi criado uma execptions
    if(possuiLivro(autor)){
      throw new OperacaoNaoPermitidaException("Não é permitido excluir um Autor que possui livros cadastrados");
    }
    repository.delete(autor);
  }

  public List<Autor> pesquisa(String nome, String nacionalidade)  {
    if(nome != null && nacionalidade != null) {
      return repository.findByNomeAndNacionalidade(nome, nacionalidade);
    }

    if(nome != null)  {
      return repository.findByNome(nome);
    }

    if(nacionalidade != null) {
      return repository.findByNacionalidade(nacionalidade);
    }

    return repository.findAll();
  }

  public List<Autor> pesquisabyExample(String nome, String nacionalidade) {
    var autor = new Autor();
    autor.setNome(nome);
    autor.setNacionalidade(nacionalidade);

    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnorePaths("id", "dataNascimento", "dataCadastro")
        .withIgnoreNullValues()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example<Autor>  autorExample = Example.of(autor, matcher);
    return repository.findAll(autorExample);
  }

  public boolean possuiLivro(Autor autor){
    return livroRepository.existsByAutor(autor);
  }
}
