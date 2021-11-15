package it.mattiaciraldo.spring.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.mattiaciraldo.spring.model.Fattura;

public interface FatturaRepository extends JpaRepository<Fattura, Long> {

	Page<Fattura> findByClienteId(Long id, Pageable p);

	Page<Fattura> findByData(LocalDate data, Pageable p);

	Page<Fattura> findByAnno(int anno, Pageable p);

	Page<Fattura> findByImportoBetween(Double importoMinimo, Double importoMassimo, Pageable p);

	Page<Fattura> findByStatoFatturaId(Long id, Pageable p);
}
