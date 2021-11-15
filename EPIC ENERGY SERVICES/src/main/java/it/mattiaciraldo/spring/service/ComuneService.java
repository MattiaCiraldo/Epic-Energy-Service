package it.mattiaciraldo.spring.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.mattiaciraldo.spring.Common;
import it.mattiaciraldo.spring.model.Comune;
import it.mattiaciraldo.spring.repository.ComuneRepository;
import it.mattiaciraldo.spring.repository.ProvinciaRepository;

@Service
public class ComuneService {
	@Autowired
	ComuneRepository comuneRepository;

	@Autowired
	ProvinciaRepository provinciaRepository;

	Common commonComune = new Common();

	public Comune saveComune(Comune comune) {
		return comuneRepository.save(comune);
		
		

	}

	public Page<Comune> getAllComuni(Pageable p) {
		return comuneRepository.findAll(p);
	}

	public Page<Comune> getByNome(String nome, Pageable p) {
		return comuneRepository.findByNome(nome, p);
	}

	public void deleteComuneById(Long id) {
		comuneRepository.deleteById(id);
	}

	public void updateComune(Comune comune) {
		Comune com = comuneRepository.getById(comune.getId());

		if (com != null) {
			comuneRepository.save(com);
		} else {
			throw new EntityNotFoundException("Il comune con id " + comune.getId() + " non esiste");
		}

	}

	public Optional<Comune> getComuneById(Long id) {
		Optional<Comune> comune = comuneRepository.findById(id);
		return comune;
	}

}
