package it.mattiaciraldo.spring.controller;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import it.mattiaciraldo.spring.model.Fattura;
import it.mattiaciraldo.spring.service.FatturaService;

@RestController
@RequestMapping("/fatturacontroller")
public class FatturaController {

	@Autowired
	private FatturaService fatturaService;

	@GetMapping("/getall")
	public ResponseEntity<Page<Fattura>> listaFatture(Pageable p) {
		Page<Fattura> fattura = fatturaService.getAllFatture(p);
		return new ResponseEntity<>(fattura, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Optional<Fattura> getFatturaById(@PathVariable Long id) {
		Optional<Fattura> fattura = fatturaService.getFatturaById(id);
		return fattura;
	}

	@PostMapping("/savefattura")
	public ResponseEntity<?> aggiungiFattura(@RequestBody Fattura fattura) {
		fatturaService.aggiungiFattura(fattura);
		return new ResponseEntity<>(fattura,HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteFatturaById(@PathVariable Long id) {
		Optional<Fattura> fattura = fatturaService.getFatturaById(id);
		if (fattura.isPresent()) {
			fatturaService.deleteFatturaById(id);
			return new ResponseEntity<>("Cancellazione fattura avvenuta con successo", HttpStatus.OK);
		}
		return new ResponseEntity<>("L'id della fattura cercata non esiste", HttpStatus.NOT_FOUND);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateFattura(@PathVariable Long id, @RequestBody Fattura fattura) {
		if (id != fattura.getId()) {
			return new ResponseEntity<>("L'id non corrisponde", HttpStatus.BAD_REQUEST);
		}
		try {
			fatturaService.updateFattura(fattura);
			return new ResponseEntity<>(fattura, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> getFatturaByClienteId(@PathVariable Long id, Pageable p) {
		Page<Fattura> fattura = fatturaService.getFattureByCliente(id, p);

		return new ResponseEntity<>(fattura, HttpStatus.OK);
	}

	@GetMapping("/data/{data}")
	public ResponseEntity<?> getFatturaByData(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
			Pageable p) {
		Page<Fattura> pfDto = fatturaService.getFatturaByData(data, p);
		return new ResponseEntity<>(pfDto, HttpStatus.OK);
	}

	@GetMapping("/stato_fattura/{id}")
	public ResponseEntity<?> getFatturaByStatoFatturaId(@PathVariable Long id, Pageable p) {
		Page<Fattura> fattura = fatturaService.getFattureByIdStatoFattura(id, p);

		return new ResponseEntity<>(fattura, HttpStatus.OK);
	}

	@GetMapping("/anno/{anno}")
	public ResponseEntity<?> getFattureByAnno(@PathVariable int anno, Pageable p) {
		Page<Fattura> fattura = fatturaService.getFattureByAnno(anno, p);
		return new ResponseEntity<>(fattura, HttpStatus.OK);
	}

	@GetMapping("/range_importo/{importoMinimo}/{importoMassimo}")
	public ResponseEntity<Page<Fattura>> getListaFatturePerImporto(@PathVariable Double importoMinimo,
			@PathVariable Double importoMassimo, Pageable p) {
		Page<Fattura> fattura = fatturaService.findByRangeImportoBetween(importoMinimo, importoMassimo, p);
		return new ResponseEntity<>(fattura, HttpStatus.OK);
	}

}
