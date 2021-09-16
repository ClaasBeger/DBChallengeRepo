package shorteningservices.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import shorteningservices.URLShortenerApplication;
import shorteningservices.entity.URL;
import shorteningservices.service.URLService;

@RestController
@RequestMapping("/api")
public class URLController {

	@Autowired
	private URLService urlService;

	@GetMapping("/urls")
	Iterable<URL> all() {
		return urlService.findAll();
	}

	@GetMapping("/urls/{id}")
	URL one(@PathVariable int id) {
		return urlService.findByID(id);
	}

	@GetMapping("/shortenedURL/{alias}")
	public void redirect(@PathVariable String alias, HttpServletResponse response) throws IOException {
		System.out.println("Gonna send redirect to " + urlService.findOriginalByAlias(alias));
		response.sendRedirect("http://" + urlService.findOriginalByAlias(alias));
	}

	@PostMapping("/urls")
	void newURL(@Valid @RequestBody URL newURL) {
		if (newURL.getAlias() == null) {
			newURL.setAlias(URLShortenerApplication.hashToString(Math.abs(newURL.getOriginal().hashCode())));
		}
		urlService.saveURL(newURL);
	}

	@PutMapping("/urls")
	void replaceURL(@Valid @RequestBody URL newURL) {
		urlService.replaceAlias(newURL.getAlias(), newURL.getOriginal());
	}

	@DeleteMapping("/urls/{id}")
	void deleteURL(@PathVariable int id) {
		urlService.deleteById(id);
	}

}
