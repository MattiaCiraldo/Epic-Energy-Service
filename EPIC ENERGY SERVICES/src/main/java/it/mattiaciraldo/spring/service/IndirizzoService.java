package it.mattiaciraldo.spring.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.mattiaciraldo.spring.Common;
import it.mattiaciraldo.spring.model.Comune;
import it.mattiaciraldo.spring.model.Indirizzo;
import it.mattiaciraldo.spring.model.Provincia;
import it.mattiaciraldo.spring.repository.ComuneRepository;
import it.mattiaciraldo.spring.repository.IndirizzoRepository;
import it.mattiaciraldo.spring.repository.ProvinciaRepository;

@Service
public class IndirizzoService {

	@Autowired
	IndirizzoRepository indirizzoRepository;

	@Autowired
	ProvinciaRepository provinciaRepository;

	Common commonComune = new Common();

	public Indirizzo saveIndirizzo(Indirizzo indirizzo) {
		return indirizzoRepository.save(indirizzo);

	}

	public Page<Indirizzo> getAllIndirizzo(Pageable p) {
		return indirizzoRepository.findAll(p);
	}

	public void deleteIndirizzoById(Long id) {
		indirizzoRepository.deleteById(id);
	}

	public void updateIndirizzo(Indirizzo indirizzo) {
		Optional<Indirizzo> ind = indirizzoRepository.findById(indirizzo.getId());
		if (ind.isEmpty()) {
			throw new EntityNotFoundException("L'indirizzo con id " + indirizzo.getId() + " non esiste");
		} else {
			indirizzoRepository.save(indirizzo);
		}
	}

	public Optional<Indirizzo> getIndirizzoById(Long id) {
		Optional<Indirizzo> indirizzo = indirizzoRepository.findById(id);
		return indirizzo;
	}

}
