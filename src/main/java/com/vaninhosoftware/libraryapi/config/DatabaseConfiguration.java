package com.vaninhosoftware.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

  @Value("${spring.datasource.url}")
  String url;
  @Value("${spring.datasource.username}")
  String username;
  @Value("${spring.datasource.password}")
  String passoword;
  @Value("${spring.datasource.driver-class-name}")
  String driver;

  //ESSE DATASOURCE É SIMPLES, É UMA IMPLEMENTAÇÃO BASICA, PROVIDA PELO SPRING DATA JPA, NÃO SENDO RECOMENDADA PARA USO EM PRODUÇÃO
  //@Bean
  public DataSource dataSource()  {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(passoword);
    ds.setDriverClassName(driver);
    return ds;
  }

  //DATASOURCE RECOMENDADO, POIS COM ELE CASO NECESSARIO PODE LIBERAR ACESSO PARA MAIS USUARIOS, FAZENDO UM GERENCIAMENTO DE UM PUT DE CONEXÇÂO PARA ATENDER A TODOS OS USUARIOS.
  @Bean
  public DataSource hikariDataSource(){
    HikariConfig config = new HikariConfig();
    config.setUsername(username);
    config.setPassword(passoword);
    config.setDriverClassName(driver);
    config.setJdbcUrl(url);

    config.setMaximumPoolSize(10);//maximo de conecções liberadas
    config.setMinimumIdle(1); //tamanho inicial de pool
    config.setPoolName("library-db-pool");
    config.setMaxLifetime(600000);  //600 mil ms (10 minutos)
    config.setConnectionTimeout(100000); //timeout para conseguir uma conexão
    config.setConnectionTestQuery("select 1");  // query de teste

    return new HikariDataSource(config);
  }
}
