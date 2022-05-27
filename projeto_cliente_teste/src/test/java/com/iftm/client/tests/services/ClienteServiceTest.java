package com.iftm.client.tests.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.iftm.client.repositories.ClientRepository;
import com.iftm.client.services.ClientService;
import com.iftm.client.services.exceptions.ResourceNotFoundException;

// para testar camada de serviço utilize essa notação, que carrega o contexto com os recursos do Spring boot
@ExtendWith(SpringExtension.class)
public class ClienteServiceTest {
		
	@InjectMocks
	private ClientService servico;
	
	@Mock
	private ClientRepository rep;
	
	/**
	 * Atividade A6
	 * Cenário de Teste
	 * Entrada:
	 * 		- idExistente: 2
	 * Resultado:
	 * 		- void
	 */
	@Test
	public void testarApagarRetornaNadaQuandoIDExiste() {
		//construir cenário
		//entrada
		Long idExistente = 2l;
		//configurar Mock
		Mockito.doNothing().when(rep).deleteById(idExistente);
		//executar o teste
		Assertions.assertDoesNotThrow(()->{servico.delete(idExistente);});
		//verificar as execuções da classe mock e de seus métodos
		Mockito.verify(rep, Mockito.times(1)).deleteById(idExistente);
	}
	
	/**
	 * Atividade A6
	 * Cenário de Teste : id não existe e retorna exception
	 * Entrada:
	 * 		- idExistente: 1000
	 * Resultado:
	 * 		- ResourceNotFoundException
	 */
	@Test
	public void testarApagarRetornaExceptionQuandoIDNaoExiste() {
		//construir cenário
		//entrada
		Long idNaoExistente = 1000l;
		//configurar Mock
		Mockito.doThrow(EmptyResultDataAccessException.class).when(rep).deleteById(idNaoExistente);
		//executar o teste
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{servico.delete(idNaoExistente);});
		//verificar as execuções da classe mock e de seus métodos
		Mockito.verify(rep, Mockito.times(1)).deleteById(idNaoExistente);
	}	
	
	/**
	 * Atividade A6
	 * Cenário de Teste : método findByIncomeGreaterThan retorna a página com clientes corretos
	 * Entrada:
	 * 		- Paginação:
	 * 			- Pagina = 1;
	 * 			- 2
	 * 			- Asc
	 * 			- Income
	 * 		- Income: 4800.00
	 * 		- Clientes:
        {
            "id": 8,
            "name": "Toni Morrison",
            "cpf": "10219344681",
            "income": 10000.0,
            "birthDate": "1940-02-23T07:00:00Z",
            "children": 0
        }
	 * Resultado:
	 * 		Page com o cliente acima
	 */
	@Test
	public void testarApagarRetornaExceptionQuandoIDNaoExiste2() {
		//construir cenário
		//entrada
		PageRequest pageRequest = PageRequest.of(1, 2, Direction.valueOf("ASC"), "income");
		Double entrada = 4800.00;

		//Page<Client> pag = new PageImpl<>(lista, pageRequest, tamanho_lista);

		//configurar Mock

		//executar o teste

		//verificar as execuções da classe mock e de seus métodos
	}		
}
