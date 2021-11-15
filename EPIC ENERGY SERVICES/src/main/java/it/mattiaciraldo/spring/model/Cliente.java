package it.mattiaciraldo.spring.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.Data;

@Data
@Entity
@Table(name= "cliente")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ragioneSociale;
	@Column(unique = true, nullable= false)
	private String partitaIva;
	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;
	@Email
	@Column(unique = true, nullable= false)
	private String email;
	private String pec;
	private String telefono;
	private String nomeContatto;
	private String cognomeContatto;
	private String telefonoContatto;
	@Email
	private String emailContatto;
	@OneToOne
	private Indirizzo indirizzoSedeOperativa;
	@OneToOne
	private Indirizzo indirizzoSedeLegale;
	private LocalDate dataInserimento;
	private LocalDate dataUltimoContatto;
	private Double fatturatoAnnuale;

}
