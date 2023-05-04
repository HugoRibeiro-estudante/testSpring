package com.iftm.client.tests.repositories;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ClientRepositoryTest {
		//private List<Client> clientesCadastrados;
		
		@Autowired
		private ClientRepository repositorio;

		/*
		@BeforeEach
		private void setupAll() {
			clientesCadastrados = repositorio.findAll();
		}
		*/
		
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
			assertFalse(resposta.isPresent());
			
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
			assertThrows(EmptyResultDataAccessException.class, ()->{repositorio.deleteById(id);});
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
			assertTrue(resultado.isEmpty());
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
			assertTrue(resultado.isEmpty());
		}
		
		/*
		 Cenário de Teste 5
		 Objetivo: Verificar se um cliente pode ser excluído pelo cpf.
			monta o cenário,
				- arquivo import.sql carrega o cenário (clientes cadastrados)
				- cpf de um cliente cadastrado (10919444522)
			executa a ação
				- executar um método para excluir um cliente pelo cpf (não existe ainda).
				- buscar um cliente pelo cpf (não existe)
			e valida a saída.
				- a busca deve retornar vazia.	
		 */
		@Test
		public void testarSeDeleteApagaUmClientePorCpfExiste() {
			String cpfExistente = "10619244881";
			repositorio.deleteByCpf(cpfExistente);
			System.out.println(repositorio.findAll().size());
			Optional<Client> resultado = repositorio.findByCpf(cpfExistente);
			assertTrue(resultado.isEmpty());
		}
		
		@Test
		public void TestarApagarSalariosMaioresQueValorExistente() {
			double salarioI=2000;
			repositorio.deleteByIncomeGreaterThan(salarioI);
			List<Client> resultado = repositorio.findByIncomeGreaterThan(salarioI);
			assertTrue(resultado.isEmpty());
		}
		
		/**
		 * Exemplo de sala 01
		 * Cenário de Teste - buscar clientes que tenham CPF iniciados com determinado número.
		 * 
		 */
		@Test
		void testaBuscaClientesInicioCPFQueExiste() {
			// definir cenário
			String parteCpf = "104";			
			int tamanhoEsperado = 2;
			String cpfEsperados[] = {"10419244771", "10419344882"};

			//execução do método que está sendo testado
			List<Client> listaClientes = repositorio.findByCpfStartingWith(parteCpf);
			
			//comparação
			// existe elementos na lista
			assertFalse(listaClientes.isEmpty());
			assertEquals(tamanhoEsperado, listaClientes.size());
			for (int i = 0; i < cpfEsperados.length; i++) {
				assertEquals(cpfEsperados[i], listaClientes.get(i).getCpf());	
			}			
			
		}
		
		/**
		 * Exemplo de sala 02
		 * Cenário de Teste - buscar clientes que tenham no mínimo X filhos
		 * 
		 */
		@Test
		void testaBuscaClientesQuantidadeMinimaFilhos() {
			//definir uma variável com a quantidade de filhos// propor algum método de repositorio que recebe a String e retorna uma lista de clientes
			// comparar se a lista retornada é diferente de vazio
			// comparar se os elementos retornados estão corretos.
		}


		@Test
		@DisplayName("Testa o metodo que retorna o cliente com nome existente")
		public void testaBuscarClientePorNomeExistente(){

			String nome = "Jose Saramago";
			Optional<Client> cliente = repositorio.findByName(nome.toLowerCase());
			assertFalse(cliente.isEmpty());
			assertEquals(nome,cliente.get().getName());

		}

		@Test
		@DisplayName("Testa o metodo que retorna o cliente com nome inexistente")
		public void testaBuscarClientePorNomeInexistente(){

			String nome = "Hugo";
			Optional<Client> cliente = repositorio.findByName(nome.toLowerCase());
			assertFalse(cliente.isPresent());
		}

				/*
		INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Jose Saramago', '10239254871', 5000.0, TIMESTAMP WITH TIME ZONE '1996-12-23T07:00:00Z', 0);
		INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Jorge Amado', '10204374161', 2500.0, TIMESTAMP WITH TIME ZONE '1918-09-23T07:00:00Z', 0);
		 */

		@Test // VOLTAR DEPOIS ----------------------
		@DisplayName("Testa o metodo que retorna varios clientes com parte de nome existente")
		public void testaBuscarClientesPorParteDoNomeExistente(){

			//Arrange
			String parte = " Jo";
			String clientesEsperados[] ={"Jose Saramago","Jorge Amado"};
			//Act
			List<Client> clientList = repositorio.findByNameStartingWith(parte);
			//Assign
			assertFalse(clientList.isEmpty());
			assertEquals(2, clientList.size());

		}


		@Test
		@DisplayName("Testa o metodo que retorna varios clientes com parte de nome inexistente")
		public void testaBuscarClientesPorParteDoNomeInexistente(){

			String parte = " xsz";
			List<Client> clientList = repositorio.findByNameStartingWith(parte.toLowerCase());
			assertTrue(clientList.isEmpty());
			assertEquals(0, clientList.size());

		}

		@Test
		@DisplayName("Testa o metodo que retorna varios clientes passando vazio")
		public void testaBuscarClientePassandoVazio(){
			String parte = "";
			List<Client> clientList = repositorio.findByNameStartingWith(parte);
			assertEquals(12, clientList.size());
		}

		@Test
		@DisplayName("Testar o método que retorna vários cliente com salários superiores a um valor")
		public void testaClientesComSalariosMaiorQue(){

			double wage = 2000;
			List<Client> clientList = repositorio.findByIncomeGreaterThan(wage);
			assertEquals(9, clientList.size());
			assertThat(clientList).isNotEmpty();
			Assertions.assertThat(clientList.get(0).getIncome()).isEqualTo(2500);

		}

		@Test
		@DisplayName("Testar o método que retorna vários cliente com salários inferiores a um valor")
		public void testaClientesComSalariosMenoresQue(){

			double wage = 2000;
			List<Client> clientList = repositorio.findByIncomeLessThan(wage);
			assertEquals(3, clientList.size());
//			assertEquals(clientList.get(0).getIncome());
			assertThat(clientList).isNotEmpty();
			Assertions.assertThat(clientList.get(0).getIncome()).isEqualTo(1500);
		}

		@Test
		@DisplayName("Testar o método que retorna vários cliente com salários entre dois valores")
		public void testaClientesComSalariosEntre(){

			double min = 2000;
			double max = 4000;

			List<Client> clientList = repositorio.findByIncomeBetween(min, max);
			assertEquals(4, clientList.size());
			assertThat(clientList).isNotEmpty();
			Assertions.assertThat(clientList.get(0).getIncome()).isEqualTo(2500);

		}


		@Test
		@DisplayName("Testar o método que retorna vários cliente baseado na sua data de aniversário.")
		public void testaBuscarClientesPelaDataDeAniversario(){

			Instant dataI = Instant.parse("2017-12-25T20:30:50Z");
			Instant dataT = Instant.now();
			List<Client> clientList = repositorio.findByBirthDateBetween(dataI, dataT);
			assertThat(clientList).isNotEmpty();
			Assertions.assertThat(clientList.get(0).getId()).isEqualTo(1);


		}

		@Test
		@DisplayName("Testar o update (save) de um cliente. Modifique o nome, o salário e o aniversário e\n" +
				"utilize os métodos criados anteriormente para verificar se realmente foram\n" +
				"modificados.")
		public void TestarSalvarNoBancoDeDados(){

			Optional<Client> cliente = repositorio.findById(1L);
			assertTrue(cliente.isPresent());

			cliente.get().setName("Novo Nome");
			cliente.get().setIncome(2500.00);
			cliente.get().setBirthDate(Instant.parse("2000-05-04T10:30:00Z"));

			Optional<Client> name = repositorio.findByName("Novo Nome");


			List<Client> income = repositorio.findByIncomeGreaterThan(2000);
			assertEquals(10, income.size());

			List<Client> birth = repositorio.findByBirthDateBetween(Instant.parse("2017-12-25T20:30:50Z"),Instant.now());

			assertEquals(0,birth.size());

		}


}
