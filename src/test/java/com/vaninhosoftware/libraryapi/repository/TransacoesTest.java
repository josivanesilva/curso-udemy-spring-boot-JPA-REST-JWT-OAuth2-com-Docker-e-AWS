package com.vaninhosoftware.libraryapi.repository;

import com.vaninhosoftware.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

  @Autowired
  TransacaoService transacaoService;

  /**
   * Commit -> confirmar as alterações
   * Rollback -> desfazer as alterações
   */
  @Test
  void transacaoSimples(){
    transacaoService.execultar();
  }

  @Test
  void transacaoEstadoManaged(){
    transacaoService.atualizarSemAtualizar();
  }
}
