package it.mattiaciraldo.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import it.mattiaciraldo.spring.model.User;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User findUserUsername(String username);
	
	/* Sort */
    // Formula: findBy + OrderBy + NomeColonna + Ordinamento(Asc/Desc)
    public List<User> findByOrderByUsernameAsc();
    
    public Page<User> findAll(Pageable pageable);
    
	Optional<User> findByUsername(String nome);
	Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
  
