package it.mattiaciraldo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mattiaciraldo.spring.model.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long>{
	
	
Provincia getByNome(String nome);
}
