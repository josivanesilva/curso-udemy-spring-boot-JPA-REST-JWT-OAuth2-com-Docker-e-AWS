package com.vaninhosoftware.libraryapi.controller;

import com.vaninhosoftware.libraryapi.controller.dto.AutorDTO;
import com.vaninhosoftware.libraryapi.controller.dto.ErroResposta;
import com.vaninhosoftware.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.vaninhosoftware.libraryapi.exceptions.RegistroDuplicadoException;
import com.vaninhosoftware.libraryapi.model.Autor;
import com.vaninhosoftware.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor  //Ele faz a criação do construtor das classes injetadas
public class AutorController {

  private final AutorService service;

/*public AutorController(AutorService service){
    this.service = service;
  }*/  //Esse construtor nao é necessário porque usei a anotação RequiredArgsConstructor

  @PostMapping
  public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autor){
    try {
      Autor autorEntidade = autor.mapearParaAutor();
      service.salvar(autorEntidade);

      // http://localhost:8081/autores/76e7c12646146464616461
      URI location = ServletUriComponentsBuilder
          .fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(autorEntidade.getId())
          .toUri();

      return ResponseEntity.created(location).build();
    } catch (RegistroDuplicadoException e)  {
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
    var idAutor = UUID.fromString(id);
    Optional<Autor> autorOptional = service.obterPorId(idAutor);
    if(autorOptional.isPresent()){
      Autor autor = autorOptional.get();
      AutorDTO dto = new AutorDTO(
          autor.getId(),
          autor.getNome(),
          autor.getDataNascimento(),
          autor.getNacionalidade()
      );
      return ResponseEntity.ok(dto);
    }

    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> deletar(@PathVariable("id") String id){
    try {
      var idAutor = UUID.fromString(id);
      Optional<Autor> autorOptional = service.obterPorId(idAutor);

      if (autorOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

      service.deletar(autorOptional.get());

      return ResponseEntity.noContent().build();
    } catch (OperacaoNaoPermitidaException e){
        var erroResposta = ErroResposta.respostaPadrao(e.getMessage());   //esse é o erro 400
        return ResponseEntity.status(erroResposta.status()).body(erroResposta);
    }
  }

  //Querem Param
  @GetMapping
  public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
    List<Autor> resultado = service.pesquisabyExample(nome, nacionalidade);
    List<AutorDTO>  lista = resultado
        .stream()
        .map(autor -> new AutorDTO(
                autor.getId(),
            autor.getNome(),
            autor.getDataNascimento(),
            autor.getNacionalidade())
        ).collect(Collectors.toList());

      return ResponseEntity.ok(lista);
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto){

    try {
      var idAutor = UUID.fromString(id);
      Optional<Autor> autorOptional = service.obterPorId(idAutor);

      if (autorOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

      var autor = autorOptional.get();
      autor.setNome(dto.nome());
      autor.setNacionalidade(dto.nacionalidade());
      autor.setDataNascimento(dto.dataNascimento());

      service.atualizar(autor);

      return ResponseEntity.noContent().build();
    } catch (RegistroDuplicadoException e){
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    }
  }
}
