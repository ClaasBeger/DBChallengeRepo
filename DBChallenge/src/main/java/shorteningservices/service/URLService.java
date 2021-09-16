package shorteningservices.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import shorteningservices.entity.URL;
import shorteningservices.exception.URLNotFoundException;
import shorteningservices.repository.URLRepository;

@Service
public class URLService {
	
	@Autowired
	private URLRepository urlRepo;
	
	public URL saveURL (URL newUrl) {
		return urlRepo.save(newUrl);
	}
	
	public Iterable<URL> findAll(){
		return urlRepo.findAll();
	}
	
	public URL findByID(int id) {
		return urlRepo.findById(id).orElseThrow(() -> new URLNotFoundException(id));
	}
	
	public void deleteById(int id) {

		try {
			urlRepo.deleteById(id);
		} catch (Exception e) {
			System.err.println("Unable to delete URL with ID: " + id);
		}

	}
	
	public String findOriginalByAlias(String alias) {
		return urlRepo.findOriginalByAlias(alias);
	}
	
	public Boolean replaceAlias(String alias, String original) {
		try {
			urlRepo.replaceAlias(alias, original);
			return true;
		}
		catch(Exception e) {
			System.err.println("Unable to replace URL with Original: "+ original);
			e.printStackTrace();
			return false;
		}
	}

}
