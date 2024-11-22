package com.vaninhosoftware.libraryapi.repository;

import com.vaninhosoftware.libraryapi.model.Autor;
import com.vaninhosoftware.libraryapi.model.GeneroLivro;
import com.vaninhosoftware.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest   //Anotação para subir o test da aplicação, subir o contener de dependecias, registrar as classes
public class AutorRepositoryTest {

  @Autowired    //Usada para injetar o repository na classe
  AutorRepository repository;

  @Autowired
  LivroRepository livroRepository;

  @Test
  public void salvarTest()  {   //Salvando no banco de dados
    Autor autor = new Autor();
    autor.setNome("Maria");
    autor.setNacionalidade("Brasileira");
    autor.setDataNascimento(LocalDate.of(1950, 1, 31));

    var autorSalvo = repository.save(autor);
    System.out.println("Auto Salvo: " + autorSalvo);
  }

  @Test
  public void atualizarTest() {   //Atualizando no banco de dados
    var id = UUID.fromString(("b4c50a67-ac6a-4a38-8810-a333ae7075ac"));   //Transformando uma String em Id (UUID) e salva na variavel id

    Optional<Autor> possivelAutor = repository.findById(id);    //Passei o Id e ele retorna um objeto do tipo Optinal de Autor,
                                                                //Vai verificar se existe o Autor, se existir ele atualiza
    if(possivelAutor.isPresent()) {

      Autor autorEncontrado = possivelAutor.get();
      System.out.println("Dados do Autor: ");
      System.out.println(autorEncontrado);

      autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

      repository.save(autorEncontrado);
    }
  }

  @Test
  public void listarTest()  {   //Listando os itens salvos no banco
    List<Autor> lista = repository.findAll();
    lista.forEach(System.out::println);   //Ele vai pegar cada elemento da lista e chamar o System out e exibir
  }

  @Test
  public void countTest(){    //Exibindo a quantidade de autores salvos no banco atravez do count
    System.out.println("Contagem de autores: " + repository.count());
  }

  @Test
  public void deletePorIdTest(){
    var id = UUID.fromString("b4c50a67-ac6a-4a38-8810-a333ae7075ac");
    repository.deleteById(id);
  }

  @Test
  public void deleteTest()  {
    var id = UUID.fromString("b4c50a67-ac6a-4a38-8810-a333ae7075ac");
    var maria = repository.findById(id).get();
    repository.delete(maria);
  }

  @Test
  public void salvarAutorComLivrosTest(){
    Autor autor = new Autor();
    autor.setNome("Antonio");
    autor.setNacionalidade("Amricana");
    autor.setDataNascimento(LocalDate.of(1970, 8, 5));

    Livro livro = new Livro();
    livro.setIsbn("20847-84874");
    livro.setPreco(BigDecimal.valueOf(204));
    livro.setGenero(GeneroLivro.MISTERIO);
    livro.setTitulo("O roubo da casa assombrada");
    livro.setDataPublicacao(LocalDate.of(1999, 1, 2));
    livro.setAutor(autor);

    Livro livro2 = new Livro();
    livro2.setIsbn("99999-84874");
    livro2.setPreco(BigDecimal.valueOf(650));
    livro2.setGenero(GeneroLivro.MISTERIO);
    livro2.setTitulo("O roubo da casa assombrada");
    livro2.setDataPublicacao(LocalDate.of(2000, 1, 2));
    livro2.setAutor(autor);

    autor.setLivros(new ArrayList<>());
    autor.getLivros().add(livro);
    autor.getLivros().add(livro2);

    repository.save(autor);

    //livroRepository.saveAll(autor.getLivros());
  }

  @Test
  public void listarLivrosAutor(){
    var id = UUID.fromString("36233b6f-1a98-402f-b721-e04969c2c85a");
    var autor = repository.findById(id).get();

    List<Livro> livrosLista = livroRepository.findByAutor(autor);
    autor.setLivros(livrosLista);

    autor.getLivros().forEach(System.out::println);
  }


}
