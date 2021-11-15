package it.mattiaciraldo.spring.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Fattura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer anno;
	private LocalDate data;
	private Double importo;
	private Integer numeroFattura;
	@ManyToOne
	private Cliente cliente;
	@ManyToOne
	private StatoFattura statoFattura;
}
