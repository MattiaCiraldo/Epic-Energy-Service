package it.mattiaciraldo.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.mattiaciraldo.spring.model.User;
import it.mattiaciraldo.spring.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public List<User> myFindAllUsers() {
		return userRepository.findAll();
	}

	public User  myFindUserById(long id) {
		return userRepository.getById(id);
	}

	public void myInsertUser(String usurname, String nome,String cognome, String email, String password) {
		userRepository.save(new User(usurname, nome, cognome, email, password));
	}
    public User findUserByUsername(String username) {
        return userRepository.findUserUsername(username);
    }
    // Ordinamento
    public List<User> myFindAllUserSorted() {
        return userRepository.findByOrderByUsernameAsc();
    }
 // Paginazione e Ordinamento
    public List<User> myFindAllUsersPageSizeSort(Integer page, Integer size, String sort) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sort));
        Page<User> pagedResult = userRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<User>();
        }
    }
}
