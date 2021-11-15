package it.mattiaciraldo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.mattiaciraldo.spring.model.User;
import it.mattiaciraldo.spring.service.UserService;

@RestController
@RequestMapping("/usercontroller")
public class UserController {
	@Autowired
	private UserService userService;
	
    @GetMapping("/userall")
    public List<User> fingUserAll(){
        List<User> listUser = userService.myFindAllUsers();
        return listUser;
    }
    
    @GetMapping("/userUsername")
    public User getUserByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }
    @GetMapping("/usersave")
    public void salvaUser(String username, String nome, String cognome, String email, String password) {
        userService.myInsertUser(username, nome,cognome, email, password);
    }
    @GetMapping(value = "/mygetalluserssortbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> myGetAllUserSortByName() {
        return userService.myFindAllUserSorted();
    }
	@GetMapping(value = "/mygetalluserspagesizesort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> myGetAllUserPageSizeSort(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size, @RequestParam(defaultValue = "id") String sort) {
      List<User> list = userService.myFindAllUsersPageSizeSort(page, size, sort);
      return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
}
