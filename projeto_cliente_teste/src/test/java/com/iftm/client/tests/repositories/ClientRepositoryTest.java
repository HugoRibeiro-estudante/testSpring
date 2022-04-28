package com.iftm.client.tests.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;

@DataJpaTest
public class ClientRepositoryTest {
		
		@Autowired
		private ClientRepository repositorio;
		
		/**
		 * Cenário de teste 1
		 * Objetivo: Verificar se a exclusão realmente apaga um registro existente.
			monta o cenário, 
				- arquivo import.sql carrega o cenário (clientes cadastrados)
				- definir o id de um cliente que exista em import.sql
			executa a ação
				- executar o método de exclusão por id
				- executar o método de buscar por id
			e valida a saída.
				- verificar se o resultado do método de busca é falso
		 */
		@Test
		public void testarSeDeleteApagaClienteComIdExistente() {
			//cenário
			long id = 1;
			repositorio.deleteById(id);
			Optional<Client> resposta = repositorio.findById(id);
			Assertions.assertFalse(resposta.isPresent());
			
		}
		
		/**
		 * Cenário de teste 2
		 * Objetivo: Verificar se a exclusão retorna um erro quando um id não existente é informado.
			monta o cenário, 
				- arquivo import.sql carrega o cenário (clientes cadastrados)
				- definir o id de um cliente que não exista em import.sql
			executa a ação
				- executar o método de exclusão por id
			e valida a saída.
				- verificar se ocorre o erro: EmptyResultDataAccessException
		 */	
		@Test
		public void testarSeDeleteRetornaExceptionCasoIdNaoExiste() {
			long id = 10000;
			Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{repositorio.deleteById(id);});
		}

		/**
		  Cenário de teste 3.
		  Objetivo: Verificar se a exclusão de todos elementos realmente apaga todos os registros do Banco de dados.
			monta o cenário,
				- arquivo import.sql carrega o cenário (clientes cadastrados)
			executa a ação
				- executar o método de exclusão de todos registros
			e valida a saída.
				- consultar todos os registros do banco e verificar se retorna vazio.
		 */
		@Test 
		public void testarSeDeleteApagaTodosRegistros() {
			repositorio.deleteAll();
			List<Client> resultado = repositorio.findAll();			
			Assertions.assertTrue(resultado.isEmpty());
		}
		
		/**
		 Cenário de teste 4
		 Objetivo: Verificar se a exclusão de uma entidade existente no banco de dados realmente ocorre.
			monta o cenário,
				- arquivo import.sql carrega o cenário (clientes cadastrados)
				- id de um cliente que existe em import.sql
			executa a ação
				- executa o método encontrar por id para retornar a entidade do	cliente com id informado.
				- executa o método apagar a entidade
				- executar novamente o método encontrar por id e verificar se o retorno dele é vazio
			e valida a saída.
				- verifica se o retorno do método encontrar por id é vazio.
		 */
		@Test
		public void testarSeDeleteApagaUmRegistroComIDExistente() {
			long id = 4;
			Optional<Client> cliente = repositorio.findById(id);
			repositorio.delete(cliente.get());
			Optional<Client> resultado = repositorio.findById(id);
			Assertions.assertTrue(resultado.isEmpty());
		}
}
