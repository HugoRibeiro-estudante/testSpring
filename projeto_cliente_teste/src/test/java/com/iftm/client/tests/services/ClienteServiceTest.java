package com.iftm.client.tests.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.iftm.client.repositories.ClientRepository;
import com.iftm.client.services.ClientService;

@ExtendWith(SpringExtension.class)
public class ClienteServiceTest {
	@InjectMocks
	private ClientService servico;
	
	@Mock
	private ClientRepository rep;
}
