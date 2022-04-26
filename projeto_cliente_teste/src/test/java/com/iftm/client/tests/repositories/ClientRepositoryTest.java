package com.iftm.client.tests.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
			long id = 1;
			repositorio.deleteById(id);
			Assertions.assertFalse(repositorio.existsById(id));
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
			Assertions.assertThrows(Exception.class, ()->{repositorio.deleteById(id);});
			
			
		}

		
}
