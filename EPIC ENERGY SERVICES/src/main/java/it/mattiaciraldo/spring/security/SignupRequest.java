package it.mattiaciraldo.spring.security;

import java.util.Set;
//import javax.validation.constraints.Email;

import javax.validation.constraints.Email;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class SignupRequest {
    private String username;
    @Email
    private String email;
    private Set<String> role;
    private String password;
    private String nome;
    private String cognome;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<String> getRole() {
		return role;
	}
	public void setRole(Set<String> role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	} 
    
    
    
}