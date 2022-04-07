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
		 */
		@Test
		public void testarSeDeleteApagaClienteComIdExistente() {
			long id = 1;
			repositorio.deleteById(id);
			Assertions.assertFalse(repositorio.existsById(id));
		}

}
