package it.mattiaciraldo.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mattiaciraldo.spring.model.Role;
import it.mattiaciraldo.spring.model.ERole;
import it.mattiaciraldo.spring.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	RoleRepository roleRepository;
	
	
	public List<Role> myFindAllUsers() {
        return roleRepository.findAll();
    }
    
    public Optional<Role> myFindUserById(long id) {
        return roleRepository.findById(id);
    }
    
    public void myInsertRole(ERole roletype) {
    	roleRepository.save(new Role(roletype));
    }
}
