package it.mattiaciraldo.spring.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import it.mattiaciraldo.spring.model.Cliente;
import it.mattiaciraldo.spring.model.TipoCliente;
import it.mattiaciraldo.spring.repository.ClienteRepository;
import it.mattiaciraldo.spring.repository.FatturaRepository;

@Service
public class ClienteService {
	@Autowired
	ClienteRepository clienteRepository;
	
	
	@Autowired
	FatturaRepository fatturaRepository;

	public Page<Cliente> getAllClienti(Pageable p) {
		return clienteRepository.findAll(p);
	}

	public void saveCliente(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	public ResponseEntity<?> deleteCliente(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			fatturaRepository.findByClienteId(id, Pageable.unpaged()).getContent()
							.forEach(f -> fatturaRepository.deleteById(f.getId()));

			clienteRepository.deleteById(id);
			return new ResponseEntity<>("Cliente Eliminato", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cliente Non Trovato", HttpStatus.NOT_FOUND);
		}
	}
	
	


	public void updateCliente(Cliente cliente) {
		Cliente c = clienteRepository.getById(cliente.getId());
		if (c == null) {
			throw new EntityNotFoundException("Il cliente con id " + cliente.getId() + " non esiste");
		} else {
			clienteRepository.save(cliente);

		}
	}

	public Page<Cliente> findByParteDelNome(String nomeContatto, Pageable p) {
		return clienteRepository.findByNomeContattoContainingIgnoreCase(nomeContatto, p);
	}

	public Page<Cliente> findByFatturatoAnnuale(Double fatturatoAnnuale, Pageable p) {
		Page<Cliente> cliente = clienteRepository.findByFatturatoAnnuale(fatturatoAnnuale, p);
		return cliente;

	}

	public Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable p) {
		Page<Cliente> cliente = clienteRepository.findByDataInserimento(dataInserimento, p);
		return cliente;
	}

	public Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable p) {
		Page<Cliente> cliente = clienteRepository.findByDataUltimoContatto(dataUltimoContatto, p);
		return cliente;
	}

	public List<Cliente> findAllClientePageSizeSort(Integer page, Integer size, String sort) {
		Pageable paging = PageRequest.of(page, size, Sort.by(sort));
		Page<Cliente> pagedResult = clienteRepository.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Cliente>();
		}
	}
	

	public Page<Cliente> orderByProvincia(Pageable p){
        return clienteRepository.findByOrderByIndirizzoSedeOperativaComuneProvinciaNomeAsc(p);
    }
	
	public Page<Cliente> orderByDataUltimoContatto(Pageable p){
        return clienteRepository.findByOrderByDataUltimoContattoAsc(p);
    }
	
	public Page<Cliente> orderByDataInserimento(Pageable p){
        return clienteRepository.findByOrderByDataInserimentoAsc(p);
    }
	public Page<Cliente> orderByRagioneSociale(Pageable p){
        return clienteRepository.findByOrderByNomeContattoAsc(p);
    }
	public Page<Cliente> orderByFatturatoAnnuale(Pageable p){
        return clienteRepository.findByOrderByFatturatoAnnualeAsc(p);
    }

}
