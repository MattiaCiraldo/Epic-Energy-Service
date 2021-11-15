package it.mattiaciraldo.spring.controller;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import it.mattiaciraldo.spring.model.Comune;
import it.mattiaciraldo.spring.service.ComuneService;
import it.mattiaciraldo.spring.service.ProvinciaService;

@RestController
@RequestMapping("/comunecontroller")
public class ComuneController {
	@Autowired
	ProvinciaService provinciaService;

	@Autowired
	ComuneService comuneService;

	

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Comune comune) {
		comuneService.saveComune(comune);
		return new ResponseEntity<>(comune, HttpStatus.CREATED);
	}

	@GetMapping("/getall")
	public ResponseEntity<Page<Comune>> listaComuni(@RequestParam(required = false, defaultValue="0") int pageNumber, 
			@RequestParam(required = false, defaultValue="20") int pageSize) {
		Pageable p= PageRequest.of(pageNumber,pageSize);
		Page<Comune> pc = comuneService.getAllComuni(p);
		return new ResponseEntity<>(pc, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Optional<Comune> getComuneById(@PathVariable Long id) {
		Optional<Comune> comune = comuneService.getComuneById(id);
		return comune;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteComuneById(@PathVariable Long id) {
		Optional<Comune> comune = comuneService.getComuneById(id);
		if (comune.isPresent()) {
			comuneService.deleteComuneById(id);
			return new ResponseEntity<>("Cancellazione comune avvenuta con successo", HttpStatus.OK);
		}
		return new ResponseEntity<>("L'id del comune cercato non esiste", HttpStatus.NOT_FOUND);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateComune(@PathVariable Long id, @RequestBody Comune comune) {
		if (id != comune.getId()) {
			return new ResponseEntity<>("L'id non corrisponde", HttpStatus.BAD_REQUEST);
		}

		comuneService.updateComune(comune);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
