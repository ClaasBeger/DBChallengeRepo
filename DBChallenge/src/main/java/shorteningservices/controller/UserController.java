package shorteningservices.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import shorteningservices.entity.CallStatistics;
import shorteningservices.entity.URL;
import shorteningservices.entity.User;
import shorteningservices.service.StatisticsService;
import shorteningservices.service.URLService;
import shorteningservices.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private URLService urlService;

	@Autowired
	private StatisticsService statService;

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
		User owner = service.findById(userID);
		newURL.setUser(owner);
		if (newURL.getAlias() == null) {
			newURL.setAlias(URLShortenerApplication.hashToString(Math.abs(newURL.getOriginal().hashCode())));
		}
		owner.addURL(newURL);
		System.out.println("Adding custom entry : " + newURL.toString());
		newURL.setStats(new CallStatistics(null, LocalDateTime.now(), new LinkedList<LocalDateTime>(), 0,
				new LinkedList<User>()));
		urlService.saveURL(newURL);
		service.save(owner);
		statService.saveStats(newURL.getStats());
	}

	@PutMapping("/users/{userID}/replace/{newUsername}")
	void replaceUname(@Valid @RequestBody User newUser, @PathVariable Integer userID,
			@PathVariable String newUsername) {
		newUser.setusername(newUsername);
		newUser.setId(userID);
		System.out.println(newUser.toString());
		service.replaceUsername(newUser);
	}

	@PutMapping("/users/{userID}/replace")
	void replacePw(@Valid @RequestBody User newUser, @PathVariable Integer userID) {
		newUser.setId(userID);
		service.replacePassword(newUser);
	}

	@DeleteMapping("/users/{id}")
	void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}

	@GetMapping("/users/{id}/urls")
	Iterable<String> listURLSByUser(@PathVariable int id) {
		List<String> carrier = new LinkedList<String>();
		service.findAllURLs(id).forEach(u -> carrier.add(u.toString()));
		return carrier;
	}

	@GetMapping("/users/{id}/shortenedURL/{alias}")
	public void redirect(@PathVariable String alias, @PathVariable int id, HttpServletResponse response)
			throws IOException {
		System.out.println("Gonna send redirect request from " + service.findById(id).getusername() + " to "
				+ urlService.findOriginalByAlias(alias));
		urlService.findByID(urlService.findObjectIDByOriginal(urlService.findOriginalByAlias(alias))).getStats()
				.recordCall(service.findById(id), LocalDateTime.now());
		statService.saveStats(urlService
				.findByID(urlService.findObjectIDByOriginal(urlService.findOriginalByAlias(alias))).getStats());
		response.sendRedirect("http://" + urlService.findOriginalByAlias(alias));
	}

}
