package it.mattiaciraldo.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import it.mattiaciraldo.spring.security.StringAttributeConverter;
import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	private String username;
	private String nome;
	private String cognome;
	@Convert(converter = StringAttributeConverter.class)
	private String email;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	

	public User() {}
	
	public User(Long id, String username, @Email String email, String password, String nome, String cognome) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }
	
	public User(String username, String nome, String cognome, String email, String password) {
		this.username = username;
		this.nome = nome;
        this.cognome = cognome;
		this.email = email;
		this.password = password;
		}
	
	public User(String username, String nome, String cognome, String email, String password, Set<Role> roles) {
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;	
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", usurname=" + username + ", nome=" + nome + ", email=" + email + ", password="
				+ password + ", roles=" + roles.toString() + "]";
	}
}
