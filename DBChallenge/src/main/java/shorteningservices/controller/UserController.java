package shorteningservices.controller;

import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shorteningservices.entity.User;
import shorteningservices.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	Iterable<User> all() {
		return service.findAll();
	}
	
	@GetMapping("/users/{id}")
	 User one(@PathVariable int id) {
		return service.findById(id);
	  }
	
	@PostMapping("/users")
	void newUser(@Valid @RequestBody User newUser) {
		service.save(newUser);
	}
	
	@PutMapping("/users")
	void replaceUser(@Valid @RequestBody User newUser) {
		service.save(newUser);
	}
	
	@DeleteMapping("/users/{id}")
	void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}
	
}
