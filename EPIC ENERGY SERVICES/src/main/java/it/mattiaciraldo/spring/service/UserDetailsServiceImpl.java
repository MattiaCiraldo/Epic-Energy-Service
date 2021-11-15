package it.mattiaciraldo.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mattiaciraldo.spring.model.User;
import it.mattiaciraldo.spring.model.UserDetailsImpl;
import it.mattiaciraldo.spring.repository.UserRepository;


/* UserDetailsServiceImpl 
Implementa l’interfaccia UserDetailsService fornita dal modulo Spring Security.
L'interfaccia UserDetailsService viene utilizzata per recuperare i dati relativi all'utente. 
Ha un metodo chiamato loadUserByUsername() che può essere sovrascritto per personalizzare il processo di ricerca dell'utente
UserDetailsService restituisce un'implementazione UserDetails che restituisce le GrantedAuthorities necessarie
*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	// Cerca l'utenete nel DB e ritorna l'implementazione di UserDetailsImpl o un'eccezione
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			return UserDetailsImpl.build(user.get());
		} else {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
	}

}
