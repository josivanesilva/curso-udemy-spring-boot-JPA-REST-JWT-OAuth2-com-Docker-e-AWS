package com.vaninhosoftware.libraryapi.service;

import com.vaninhosoftware.libraryapi.model.Autor;
import com.vaninhosoftware.libraryapi.model.GeneroLivro;
import com.vaninhosoftware.libraryapi.model.Livro;
import com.vaninhosoftware.libraryapi.repository.AutorRepository;
import com.vaninhosoftware.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

  @Autowired
  private AutorRepository autorRepository;
  @Autowired
  private LivroRepository livroRepository;

  @Transactional
  public void atualizarSemAtualizar(){
    var livro = livroRepository.findById(UUID.fromString("ee1ada33-6c30-4f31-9213-7cd39dbd2e60")).orElse(null);
    livro.setDataPublicacao(LocalDate.of(2024, 6, 1));
  }

  @Transactional
  public void execultar() {
    //Salva o autor
    Autor autor = new Autor();
    autor.setNome("Teste Francisco");
    autor.setNacionalidade("Brasileira");
    autor.setDataNascimento(LocalDate.of(1950, 1, 31));

    autorRepository.save(autor);

    //Salva o Livro
    Livro livro = new Livro();
    livro.setIsbn("90887-84874");
    livro.setPreco(BigDecimal.valueOf(100));
    livro.setGenero(GeneroLivro.FICCAO);
    livro.setTitulo("Teste Livro do Francisco");
    livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

    livro.setAutor(autor);

    livroRepository.save(livro);

    if(autor.getNome().equals("Teste Francisco")){
      throw new RuntimeException("Rollback");
    }
  }

}
