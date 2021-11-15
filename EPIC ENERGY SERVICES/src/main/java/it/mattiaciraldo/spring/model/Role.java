package it.mattiaciraldo.spring.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	@Enumerated(EnumType.STRING)
	private ERole roleType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ERole getRoleType() {
		return roleType;
	}
	public void setRoleType(ERole roletype) {
		this.roleType = roletype;
	}

	public Role() {}
	public Role(ERole roletype) {
		this.roleType = roletype;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roletype=" + roleType + "]";
	}
}
