package shorteningservices.controller;

import java.util.LinkedList;
import java.util.List;

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

import shorteningservices.URLShortenerApplication;
import shorteningservices.entity.URL;
import shorteningservices.entity.User;
import shorteningservices.service.URLService;
import shorteningservices.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private URLService urlService;
	
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
	
	@PostMapping("/users/{userID}/urls")
	void newURL(@Valid @RequestBody URL newURL, @PathVariable int userID) {
		newURL.setUser(userID);
		if (newURL.getAlias() == null) {
			newURL.setAlias(URLShortenerApplication.hashToString(Math.abs(newURL.getOriginal().hashCode())));
		}
		System.out.println("Adding custom entry : "+ newURL.toString());
		urlService.saveURL(newURL);
	}
	
	@PutMapping("/users")
	void replaceUser(@Valid @RequestBody User newUser) {
		service.save(newUser);
	}
	
	@DeleteMapping("/users/{id}")
	void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}
	
	@GetMapping("/users/{id}/urls")
	Iterable<String> listURLSByUser(@PathVariable int id){
		List<String> carrier = new LinkedList<String>();
		service.findAllURLs(id).forEach(u -> carrier.add(u.toString()));
		return carrier;
	}
	
}
