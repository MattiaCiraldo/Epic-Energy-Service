package it.mattiaciraldo.spring.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.validation.constraints.AssertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import it.mattiaciraldo.spring.model.Cliente;

class ClienteServiceTest {
	Cliente cliente;
	Pageable p;
	
	@Autowired
	ClienteService clienteService;
	
	@BeforeEach
	void setup() {
		p= Pageable.unpaged();
		 cliente = new Cliente();	
		 
		 

		
	}

	@Test
	void testGetAllClienti() {

	}

	@Test
	void testSaveCliente() {
		clienteService.saveCliente(cliente);
		assertEquals(0, clienteService.getAllClienti(p));

		
	}

	@Test
	void testDeleteCliente() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateCliente() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByParteDelNome() {
		fail("Not yet implemented");
	}

}
