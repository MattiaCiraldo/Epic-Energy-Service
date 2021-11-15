package it.mattiaciraldo.spring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.mattiaciraldo.spring.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

//	public List<Cliente> findByOrderByNomeContattoAsc();
//
//	public List<Cliente> findByOrderByFatturatoAnnualeAsc();
//
//	public List<Cliente> findByOrderByDataInserimentoAsc();

	Page<Cliente> findAll(Pageable pageable);

//	Page<Cliente> findByRagioneSocialeContainingIgnoreCase(String nome, Pageable p);

	Page<Cliente> findByNomeContattoContainingIgnoreCase(String nomeContatto, Pageable p);

//  Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(Double fatturatoAnnuale, Pageable p);
//
//  Page<Cliente> findByFatturatoAnnualeLessThanEqual(Double fatturatoAnnuale, Pageable p);
//
//  Page<Cliente> findByFatturatoAnnualeBetween(Double fatturatoAnnualeMinimo,
//          Double fatturatoAnnualeMassimo, Pageable p);

	Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable p);

	Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable p);

	Page<Cliente> findByFatturatoAnnuale(Double fatturatoAnnuale, Pageable p);

	Page<Cliente> findByOrderByIndirizzoSedeOperativaComuneProvinciaNomeAsc(Pageable p);

	Page<Cliente> findByOrderByDataUltimoContattoAsc(Pageable p);

	Page<Cliente> findByOrderByDataInserimentoAsc(Pageable p);

	Page<Cliente> findByOrderByNomeContattoAsc(Pageable p);

	Page<Cliente> findByOrderByFatturatoAnnualeAsc(Pageable p);

}
