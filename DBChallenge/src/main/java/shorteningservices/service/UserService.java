package shorteningservices.service;

import java.text.MessageFormat;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import shorteningservices.entity.URL;
import shorteningservices.entity.User;
import shorteningservices.exception.UserNotFoundException;
import shorteningservices.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Optional<User> optionalUser = userRepository.findByUsername(username);
		System.out.println("-------------------------------------- Searching for "+username+" in User Database-----------------------------------------");
		if (optionalUser.isPresent()) {
			System.out.println("Not Found");
			return optionalUser.get();
		}
		throw new UsernameNotFoundException("User was not found in Database");
	}

	public void save(User newUser) {
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		final User createdUser = userRepository.save(newUser);
	}
	

	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(int userId) {

		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

	}

	public void deleteById(int userId) {

		try {
			userRepository.deleteById(userId);
		} catch (Exception e) {
			System.err.println("Unable to delete User with ID: " + userId);
		}

	}
	
	public void replaceUsername(User updatedUser) {
		userRepository.replaceUsername(updatedUser.getId(), updatedUser.getUsername());
	}
	
	public void replacePassword(User updatedUser) {
		userRepository.replacePassword(updatedUser.getId(), bCryptPasswordEncoder.encode(updatedUser.getPassword()));
	}
	
	public Iterable<URL> findAllURLs(Integer UserID){
		return userRepository.findAllURLs(UserID).orElseThrow(() -> new UserNotFoundException(UserID));
	}
}