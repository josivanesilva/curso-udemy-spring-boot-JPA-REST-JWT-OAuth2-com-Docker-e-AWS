package com.vaninhosoftware.libraryapi.repository;

import com.vaninhosoftware.libraryapi.model.Autor;
import com.vaninhosoftware.libraryapi.model.GeneroLivro;
import com.vaninhosoftware.libraryapi.model.Livro;
import jakarta.persistence.AttributeOverride;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

  @Autowired
  LivroRepository repository;

  @Autowired
  AutorRepository autorRepository;

  @Test
  public void salvarTest(){
    Livro livro = new Livro();
    livro.setIsbn("90887-84874");
    livro.setPreco(BigDecimal.valueOf(100));
    livro.setGenero(GeneroLivro.CIENCIA);
    livro.setTitulo("Ciencias");
    livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

    Autor autor = autorRepository.findById(UUID.fromString("dd1ebe95-1b3c-497a-bc8f-5498f78463a4")).orElse(null);

    livro.setAutor(autor);

    repository.save(livro);
  }

  @Test
  public void salvarAutorELivroTest(){
    Livro livro = new Livro();
    livro.setIsbn("90887-84874");
    livro.setPreco(BigDecimal.valueOf(100));
    livro.setGenero(GeneroLivro.FICCAO);
    livro.setTitulo("Terceiro livro");
    livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

    Autor autor = new Autor();
    autor.setNome("Jose");
    autor.setNacionalidade("Brasileira");
    autor.setDataNascimento(LocalDate.of(1950, 1, 31));

    autorRepository.save(autor);

    livro.setAutor(autor);

    repository.save(livro);
  }

  @Test
  public void salvarCascadeTest(){
    Livro livro = new Livro();
    livro.setIsbn("90887-84874");
    livro.setPreco(BigDecimal.valueOf(100));
    livro.setGenero(GeneroLivro.FICCAO);
    livro.setTitulo("Outro livro");
    livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

    Autor autor = new Autor();
    autor.setNome("João");
    autor.setNacionalidade("Brasileira");
    autor.setDataNascimento(LocalDate.of(1950, 1, 31));

    livro.setAutor(autor);

    repository.save(livro);
  }

  @Test
  public void atualizarAutorDoLivro(){
    UUID id = UUID.fromString("b269484d-c37b-4b7a-8c36-fa14309a2889");
    var livroParaAtualizar = repository.findById(id).orElse(null);

    UUID idAutor = UUID.fromString("b4c50a67-ac6a-4a38-8810-a333ae7075ac");
    Autor maria = autorRepository.findById(idAutor).orElse(null);

    livroParaAtualizar.setAutor(maria);

    repository.save(livroParaAtualizar);
  }

  @Test
  public void deletar(){
    UUID id = UUID.fromString("b269484d-c37b-4b7a-8c36-fa14309a2889");
    repository.deleteById(id);
  }

  @Test
  public void deletarCascade(){
    UUID id = UUID.fromString("4f0806f7-d807-490a-b1e6-3a05dcb2908f");
    repository.deleteById(id);
  }

  @Test
  @Transactional
  public void buscarLivroTest(){
    UUID id = UUID.fromString("ee1ada33-6c30-4f31-9213-7cd39dbd2e60");
    Livro livro = repository.findById(id).orElse(null);
    System.out.println("Livro:");
    System.out.println(livro.getTitulo());

    //System.out.println("Autor:");
    //System.out.println(livro.getAutor().getNome());
  }


  @Test
  public void pesquisarPorTituloTest(){
    List<Livro> lista = repository.findByTitulo("O roubo da casa assombrada");
    lista.forEach(System.out::println);
  }

  @Test
  public void pesquisarPorISBNTest(){
    List<Livro> lista = repository.findByIsbn("20847-84874");
    lista.forEach(System.out::println);
  }

  @Test
  public void pesquisaPorTituloEPrecoTest(){
    var preco = BigDecimal.valueOf(204.00);
    String tituloPesquisa = "O roubo da casa assombrada";

    List<Livro> lista = repository.findByTituloAndPreco(tituloPesquisa, preco);
    lista.forEach(System.out::println);
  }

  @Test
  public void listarLivrosComQueryJPQL(){
    var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
    resultado.forEach(System.out::println);

  }

  @Test
  public void listarAutoresDosLivros(){
    var resultado = repository.listarAutorsDosLivros();
    resultado.forEach(System.out::println);
  }

  @Test
  public void listarTitulosNaoRepetidosDosLivros(){
    var resultado = repository.listarNomesDiferentesLivros();
    resultado.forEach(System.out::println);
  }

  @Test
  public void listarGenerosDeLivrosAutoresBrasileiros(){
    var resultado = repository.listarGenerosAutoresBrasileiros();
    resultado.forEach(System.out::println);
  }

  @Test
  public void listarPorGeneroQueryParamTest(){
    var resultado = repository.findByGenero(GeneroLivro.MISTERIO, "preco");
    resultado.forEach(System.out::println);
  }

  @Test
  public void listarPorGeneroPositionalParamTest(){
    var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.MISTERIO, "preco");
    resultado.forEach(System.out::println);
  }

  @Test
  void deletePorGeneroTest(){
    repository.deleteByGenero(GeneroLivro.CIENCIA);
  }

@Test
  void updateDataPublicacaoTest(){
    repository.updateDataPublicacao(LocalDate.of(2000,1, 1));
  }


}