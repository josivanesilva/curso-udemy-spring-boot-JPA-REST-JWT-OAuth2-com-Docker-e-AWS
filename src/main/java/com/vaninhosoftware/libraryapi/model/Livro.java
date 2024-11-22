package com.vaninhosoftware.libraryapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity   //Obrigatoria para dizer que esta fazendo mapeamento JPA de uma entidade.
          // Significa que ela esta mapeada no banco de dados
@Table(name = "livro")    //Pode modificar a definiçãoda entidade
@Data   //Essa anotação gera as anotações: @getter e @Setter, @ToString, @EqualsAndHashCode: gerar ele na classe , @RequiredConstructor
@ToString(exclude = "autor")
public class Livro {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.UUID)   //Gera automaticamente o id
  private UUID id;

  @Column(name = "isbn", length = 20, nullable = false)
  private String isbn;

  @Column(name = "titulo", length = 150, nullable = false)
  private String titulo;

  @Column(name = "data_publicacao")
  private LocalDate dataPublicacao;

  @Enumerated(EnumType.STRING)    //É o valor que é quardado na classe Enum, no caso uma String
  @Column(name = "genero", length = 30, nullable = false)
  private GeneroLivro genero;

  @Column(name = "preco", precision = 18, scale = 2)    //precision é a quantidade de casas antes da vigula do valor
  private BigDecimal preco;                                 //scale é a quantidade de casas depois da virgula

  @ManyToOne(     //@ManyToOne É o tipo de relacionamento, muitos "livros" para um "autor"
      //cascade = CascadeType.ALL,
    fetch = FetchType.EAGER     //EAGER é o padrão, ele traz junto, exemplo, chama o livro e traz automatico o autor
                              //LAZY ele não traz no modo cascade, exemplo quando chama o livro só vem dados do livro e não traz os do autor.
  )
  @JoinColumn(name = "id_autor")    //Anotação utilizada para relacionar a chave estrangeira na coluna livros, mapeando a coluna em questão
  private Autor autor;              //Relacionando a classe autor na classe livros, aqui é um objeto relacional

}
