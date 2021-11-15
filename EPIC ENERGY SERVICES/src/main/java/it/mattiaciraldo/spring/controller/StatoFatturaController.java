package it.mattiaciraldo.spring.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
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

import it.mattiaciraldo.spring.model.StatoFattura;
import it.mattiaciraldo.spring.service.StatoFatturaService;

@RestController
@RequestMapping("/statofatture")
public class StatoFatturaController {

	@Autowired
	private StatoFatturaService statoFatturaService;

	@GetMapping("/getall")
	public ResponseEntity<List<StatoFattura>> listaStatoFattura() {
		List<StatoFattura> lsf = statoFatturaService.getAllStatoFatture();
		return new ResponseEntity<>(lsf, HttpStatus.OK);
	}

	@PostMapping("/savestatofattura")
	public ResponseEntity<?> aggiungiStatoFattura(@RequestBody StatoFattura statoFattura) {
		statoFatturaService.aggiungiStatoFattura(statoFattura);
		return new ResponseEntity<>(statoFattura, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public Optional<StatoFattura> getStatoFatturaById(@PathVariable Long id) {
		Optional<StatoFattura> statoFattura = statoFatturaService.getStatoFatturaById(id);
		return statoFattura;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteStatoFatturaByid(@PathVariable Long id) {
		Optional<StatoFattura> statoFattura = statoFatturaService.getStatoFatturaById(id);
		if (statoFattura.isPresent()) {
			statoFatturaService.deleteStatoFatturaById(id);
			return new ResponseEntity<>("Cancellazione Stato Fattura OK", HttpStatus.OK);
		}
		return new ResponseEntity<>("Stato Fattura NotFound", HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStatoFattura(@PathVariable Long id, @RequestBody StatoFattura statoFattura) {
		if (id != statoFattura.getId()) {
			return new ResponseEntity<>("L id non è lo stesso", HttpStatus.BAD_REQUEST);
		}
		try {
			statoFatturaService.updateStatoFattura(statoFattura);
			return new ResponseEntity<>("Modifica avvenuta con successo", HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
