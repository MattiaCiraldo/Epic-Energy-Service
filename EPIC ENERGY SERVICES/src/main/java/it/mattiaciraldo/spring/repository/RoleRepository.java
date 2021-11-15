package it.mattiaciraldo.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import it.mattiaciraldo.spring.model.Role;
import it.mattiaciraldo.spring.model.ERole;

@Component
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleType(ERole roletype);

}
