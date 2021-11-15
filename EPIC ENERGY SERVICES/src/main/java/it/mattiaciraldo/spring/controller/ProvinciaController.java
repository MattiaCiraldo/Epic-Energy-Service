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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.mattiaciraldo.spring.model.Provincia;
import it.mattiaciraldo.spring.service.ProvinciaService;

@RestController
@RequestMapping("/provinciacontroller")
public class ProvinciaController {
	@Autowired
	ProvinciaService provinciaService;
	
	@PostMapping("/save")
	public ResponseEntity<?> aggiungiProvincia(@RequestBody Provincia pro){
		Provincia provincia = provinciaService.saveProvincia(pro);
		return new ResponseEntity<>(provincia, HttpStatus.CREATED);
	}

//	@DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> eliminaProvincia(@PathVariable(required = true)Long id) {
//        return provinciaService.deleteProvinciaById(id);
//    }
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProvinciaById(@PathVariable Long id, @RequestBody Provincia prov) {
		if (id != prov.getId()) {
			return new ResponseEntity<>("L'id non corrisponde", HttpStatus.BAD_REQUEST);
		}
		try {
			provinciaService.updateProvincia(prov);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/getall")
	public ResponseEntity<Page<Provincia>> listaProvince(Pageable p){
		Page<Provincia> pp = provinciaService.getAllProvince(p);
		return new ResponseEntity<>(pp, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public Optional<Provincia> getProvinciaById(@PathVariable Long id){
		Optional<Provincia> trovata = provinciaService.getProvinciaById(id);
		return trovata;
	}
	
	

}
