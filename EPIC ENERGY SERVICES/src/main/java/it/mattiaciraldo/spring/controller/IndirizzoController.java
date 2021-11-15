package it.mattiaciraldo.spring.controller;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RestController;

import it.mattiaciraldo.spring.model.Cliente;
import it.mattiaciraldo.spring.model.Indirizzo;
import it.mattiaciraldo.spring.service.IndirizzoService;

@RestController
@RequestMapping("/indirizzocontroller")
public class IndirizzoController {

	@Autowired
	private IndirizzoService indirizzoServ;

	@GetMapping("/getall")
	public ResponseEntity<Page<Indirizzo>> listaIndirizzi(Pageable p) {
		Page<Indirizzo> pi = indirizzoServ.getAllIndirizzo(p);

		return new ResponseEntity<>(pi, HttpStatus.OK);
	}

	@PostMapping("/saveindirizzo")
	public ResponseEntity<?> aggiungiIndirizzo(@RequestBody Indirizzo indirizzo) {
		Indirizzo aggiunto = indirizzoServ.saveIndirizzo(indirizzo);
		return new ResponseEntity<>(aggiunto, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public Optional<Indirizzo> getIndirizzoById(@PathVariable Long id) {
		Optional<Indirizzo> trovato = indirizzoServ.getIndirizzoById(id);
		return trovato;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteIndirizzoById(@PathVariable Long id) {
		Optional<Indirizzo> daEliminare = indirizzoServ.getIndirizzoById(id);
		if (daEliminare.isPresent()) {
			indirizzoServ.deleteIndirizzoById(id);
			return new ResponseEntity<>("Cancellazione indirizzo avvenuta con successo", HttpStatus.OK);
		}
		return new ResponseEntity<>("L'id dell'indirizzo cercato non esiste", HttpStatus.NOT_FOUND);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateIndirizzo(@PathVariable Long id, @RequestBody Indirizzo indirizzo) {
		if (id != indirizzo.getId()) {
			return new ResponseEntity<>("L'id non corrisponde", HttpStatus.BAD_REQUEST);
		}
		try {
			indirizzoServ.updateIndirizzo(indirizzo);
			return new ResponseEntity<>(indirizzo,HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
