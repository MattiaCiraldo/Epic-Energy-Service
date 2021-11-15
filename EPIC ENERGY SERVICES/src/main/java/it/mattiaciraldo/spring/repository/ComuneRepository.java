package it.mattiaciraldo.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.mattiaciraldo.spring.model.Comune;

public interface ComuneRepository extends JpaRepository<Comune, Long> {
	
	Page<Comune> findByNome(String nome, Pageable p);

}
