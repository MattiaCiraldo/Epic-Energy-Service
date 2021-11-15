package it.mattiaciraldo.spring.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mattiaciraldo.spring.model.StatoFattura;
import it.mattiaciraldo.spring.repository.StatoFatturaRepository;

@Service
public class StatoFatturaService {
	
	@Autowired
	private StatoFatturaRepository statoFatturaRepository;
	
	public List<StatoFattura> getAllStatoFatture(){
		return statoFatturaRepository.findAll();
	}
	
	public StatoFattura aggiungiStatoFattura(StatoFattura statoFattura){
		return statoFatturaRepository.save(statoFattura);
	}
	
	public Optional<StatoFattura> getStatoFatturaById(Long id){
		Optional<StatoFattura> statoFattura = statoFatturaRepository.findById(id);
		return statoFattura;
	}
	
	public void deleteStatoFatturaById(Long id) {
		Optional<StatoFattura> statoFattura = statoFatturaRepository.findById(id);
		statoFatturaRepository.deleteById(id);
	}
	
	public void updateStatoFattura(StatoFattura statoFattura) {
		Optional<StatoFattura> lst = statoFatturaRepository.findById(statoFattura.getId());
		if(lst.isEmpty()) {
			throw new EntityNotFoundException("Lo stato fattura con id " + statoFattura.getId() + " non esiste"); 
		}
		else {
			statoFatturaRepository.save(statoFattura);
		}
	}
	
	
}
