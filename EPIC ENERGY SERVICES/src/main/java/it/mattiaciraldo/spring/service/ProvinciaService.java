package it.mattiaciraldo.spring.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.mattiaciraldo.spring.Common;
import it.mattiaciraldo.spring.model.Provincia;
import it.mattiaciraldo.spring.repository.ProvinciaRepository;

@Service
public class ProvinciaService {
	@Autowired
	ProvinciaRepository provinciaRepository;

	Common comuniCommon = new Common();

	public Provincia saveProvincia(Provincia provincia) {
		return provinciaRepository.save(provincia);
	}

//	public ResponseEntity<?> deleteProvinciaById(Long id) {
//		Optional<Provincia> provincia = provinciaRepository.findById(id);
//        if(provincia.isPresent()) {
//        provinciaRepository.deleteById(id);
//        return new ResponseEntity<>("Provincia eliminata ", HttpStatus.OK);
//    } else {
//        return new ResponseEntity<>("Provincia non trovata ", HttpStatus.BAD_REQUEST);
//        }
//    }

	public void updateProvincia(Provincia provincia) {
		Optional<Provincia> lst = provinciaRepository.findById(provincia.getId());
		if (lst.isEmpty()) {
			throw new EntityNotFoundException("La provicnia con id " + provincia.getId() + "non esiste");
		} else {
			provinciaRepository.save(provincia);
		}
	}

	public Page<Provincia> getAllProvince(Pageable p) {
		return provinciaRepository.findAll(p);
	}

	public Optional<Provincia> getProvinciaById(Long id) {
		Optional<Provincia> provincia = provinciaRepository.findById(id);
		return provincia;

	}
}
