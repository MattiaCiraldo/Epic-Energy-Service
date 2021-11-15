package it.mattiaciraldo.spring.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import it.mattiaciraldo.spring.model.Cliente;
import it.mattiaciraldo.spring.service.ClienteService;

@RestController
@RequestMapping("/clientecontroller")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping("/savecliente")
	public ResponseEntity<?> aggiungiCliente(@RequestBody Cliente cliente) {
		clienteService.saveCliente(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.CREATED);
	}
	

	@DeleteMapping("/deletecliente/{id}")
	public ResponseEntity<?> eliminaCliente(@PathVariable(required = true) Long id) {
		clienteService.deleteCliente(id);
		return new ResponseEntity<>("Utente Eliminato con successo ",HttpStatus.OK);

	}

	@PutMapping("/updatecliente")
	public ResponseEntity<?> updateCliente(@RequestBody Cliente cliente) {
		clienteService.updateCliente(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
	@GetMapping("/getall")
	public ResponseEntity<Page<Cliente>> listaClienti(Pageable p) {
		Page<Cliente> cliente = clienteService.getAllClienti(p);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@GetMapping(value = "/ordfattura", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cliente>> orderByFatturatoAnnuale(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "2") Integer size,
			@RequestParam(defaultValue = "fatturatoAnnuale") String sort) {
		List<Cliente> list = clienteService.findAllClientePageSizeSort(page, size, sort);
		return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "/ordnome", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cliente>> orderByNomeContatto(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "2") Integer size, @RequestParam(defaultValue = "nomeContatto") String sort) {
		List<Cliente> list = clienteService.findAllClientePageSizeSort(page, size, sort);
		return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK);

	}

	@GetMapping(value = "/datains", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cliente>> orderByDataInserimento(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "2") Integer size,
			@RequestParam(defaultValue = "dataInserimento") String sort) {
		List<Cliente> list = clienteService.findAllClientePageSizeSort(page, size, sort);
		return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK);

	}

	@GetMapping(value = "/datault", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cliente>> orderByDataUltimoContatto(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "2") Integer size,
			@RequestParam(defaultValue = "dataUltimoContatto") String sort) {
		List<Cliente> list = clienteService.findAllClientePageSizeSort(page, size, sort);
		return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK);

	}

	@GetMapping("/fatturato_annuale_massimo")
	public ResponseEntity<Page<Cliente>> getListaClientiPerFatturatoAnnuale(@RequestParam Double fatturatoAnnuale,
			Pageable p) {
		Page<Cliente> cliente = clienteService.findByFatturatoAnnuale(fatturatoAnnuale, p);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@GetMapping("/data_inserimento/{data}")
	public ResponseEntity<?> getClienteByDataInserimento(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data, Pageable p) {
		Page<Cliente> cliente = clienteService.findByDataInserimento(data, p);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@GetMapping("/data_ultimo_contatto/{data}")
	public ResponseEntity<?> getClienteByDataUltimoContatto(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data, Pageable p) {
		Page<Cliente> cliente = clienteService.findByDataUltimoContatto(data, p);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@GetMapping("/findbynome")
	public ResponseEntity<?> findByNomeContainingIgnoreCase(@RequestParam String nomeContatto, Pageable p) {
		Page<Cliente> cliente = clienteService.findByParteDelNome(nomeContatto, p);
		return new ResponseEntity<>(cliente, HttpStatus.OK);

	}

}
