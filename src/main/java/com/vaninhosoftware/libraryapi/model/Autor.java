package com.vaninhosoftware.libraryapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity //Obrigatoria para dizer que esta fazendo mapeamento JPA de uma entidade.
        // Significa que ela esta mapeada no banco de dados
@Table(name = "autor")  //Pode modificar a definiçãoda entidade
@Getter
@Setter
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class)    //Necessário para as @LastModifiedDate  @CreatedDate funcionarem
public class Autor {

  @Id
  @Column(name = "id")   //
  @GeneratedValue(strategy = GenerationType.UUID) //Gera automaticamente o id
  private UUID id;

  @Column(name = "nome", length = 100, nullable = false)  //length é até quantos caraceres que é aceito.....
  private String nome;                                     // nullable é que não pode ser null


  @Column(name = "data_nascimento", nullable = false)   // nullable é que não pode ser null
  private LocalDate dataNascimento;

  @Column(name = "nacionalidade", length = 50, nullable = false)    //length é até quantos caraceres que é aceito.....
  private String nacionalidade;                                     // nullable é que não pode ser null

  @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)    //Um "Autor" para muitos "Livros". //mappedBy = mapeado por (coloca o nome da entidade que esta dentro da classe, no caso "autor"
      //, cascade = CascadeType.ALL
  private List<Livro> livros;    //Listar os livros do autor.

  @CreatedDate  //Cria a data altomatico quando for cadastrar um novo,
                // OBRIGATORIO colocar antes do construtot da classe o @EntityListeners(AuditingEntityListener.class)
  @Column(name = "data_cadastro")
  private LocalDateTime dataCadastro;

  @LastModifiedDate  //altera a data altomatico quando for atualizarum,
                    // OBRIGATORIO colocar antes do construtot da classe o @EntityListeners(AuditingEntityListener.class)
                   //OBRIGATORIO colocar na classe Application o @EnableJpaAuditing
  @Column(name = "data_atualizacao")
  private LocalDateTime dataAtualizacao;

  @Column(name = "id_usuario")
  private UUID idUsuario;

  //Os Métodos Getter e Setter foram gerados automaticamente pelas anotações @Getter e @Setter.

}
