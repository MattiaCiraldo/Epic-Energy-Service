package it.mattiaciraldo.spring.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.mattiaciraldo.spring.model.Fattura;
import it.mattiaciraldo.spring.repository.FatturaRepository;

@Service
public class FatturaService {
	
	@Autowired
	private FatturaRepository fatturaRepository;
	
	public Page<Fattura> getAllFatture(Pageable p){
		return fatturaRepository.findAll(p);
	}
	
	public Fattura aggiungiFattura(Fattura fattura) {
		return fatturaRepository.save(fattura);
	}
	
	public Optional<Fattura> getFatturaById(Long id){
		return fatturaRepository.findById(id);
		
	}
	
	public void deleteFatturaById(Long id) {
		Optional<Fattura> fattura = fatturaRepository.findById(id);
		fatturaRepository.deleteById(id);
	}
	
	public void updateFattura(long id, Fattura fattura) {
		Fattura fat= fatturaRepository.getById(id);
		fatturaRepository.deleteById(id);
		fattura.setId(fat.getId());
		fattura.setCliente(fat.getCliente());
		fattura.setStatoFattura(fat.getStatoFattura());
		fatturaRepository.save(fattura);
	}
	public void updateFattura(Fattura fattura) {
		Optional<Fattura> of  = fatturaRepository.findById(fattura.getId());
		if(of.isEmpty()) {
			throw new EntityNotFoundException("La fattura con id " + fattura.getId() + " non esiste");
		}
		else {
			fatturaRepository.save(fattura);
		}
	}
	
	public Page<Fattura> getFattureByCliente(Long id, Pageable p){
		return fatturaRepository.findByClienteId(id, p);
	}
	
	public Page<Fattura> getFatturaByData(LocalDate date, Pageable p){
		return fatturaRepository.findByData(date, p);
	}
	
	public Page<Fattura> getFattureByIdStatoFattura(Long id, Pageable p){
		return fatturaRepository.findByStatoFatturaId(id, p);
		
	}
	
	public Page<Fattura> getFattureByAnno(int anno, Pageable p){
		return fatturaRepository.findByAnno(anno, p);
		
	}

	public Page<Fattura> findByRangeImportoBetween(Double importoMinimo, Double importoMassimo, Pageable p) {
		return fatturaRepository.findByImportoBetween(importoMinimo, importoMassimo, p);
	}
	
}
