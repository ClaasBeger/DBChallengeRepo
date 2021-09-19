package shorteningservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import shorteningservices.entity.URL;
import shorteningservices.entity.User;
import shorteningservices.service.URLService;
import shorteningservices.service.UserService;

@SpringBootApplication
public class URLShortenerApplication {

	@Autowired
	private URLService urlService;

	@Autowired
	private UserService userService;

	// URL shortening logic partially adapted from
	// https://www.geeksforgeeks.org/how-to-design-a-tiny-url-or-url-shortener/
	/**
	 * This method is mostly used for testing purposes, meaning shortening of URLs
	 * directly through the main class
	 * 
	 * @param original,    a String representing the original url address for which
	 *                     the alias will be generated
	 * @param customAlias, optional, null will be passed if no custom Alias has been
	 *                     generated
	 * @return a boolean value representing whether the generation of the URL was
	 *         successful
	 */
	public boolean shorten(String original, String customAlias) {

		try {
			String Alias;
			if (customAlias != null) {
				urlService.saveURL(new URL(original, customAlias));
				Alias = customAlias;
			} else {
				Alias = hashToString(Math.abs(original.hashCode()));
				System.out.println("Created entry " + original + " and " + Alias);
				urlService.saveURL(new URL(original, Alias));
			}
			return true;
		} catch (Exception e) {
			System.out.println("An exception was encountered when trying to create url entry and save");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Main method for the generation of alias values for originals
	 * 
	 * @param hashValue the hashValue received by calling .hashCode() on the
	 *                  original String value. Has to be positive!
	 * @return A String representation of the hash Value passed as a parameter
	 */
	public static String hashToString(int hashValue) {
		StringBuffer createdAlias = new StringBuffer();

		// Mapping for later conversion
		char map[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

		// Convert given integer id to a base 62 number
		while (hashValue > 0) {
			// use above map to store actual character
			// in short url
			createdAlias.append(map[hashValue % 62]);
			hashValue = hashValue / 62;
		}

		// Reverse shortURL to complete base conversion
		return createdAlias.reverse().toString();
	}

	/**
	 * A method to transform a shortURL (e.g String representation created in
	 * hashToString) back to the respective hash Value Useful to control mapping
	 * process
	 * 
	 * @param shortURL The String representing the hashValue of the original (alias)
	 * @return Integer representing the hashValue of the original
	 */
	public static int shortURLtoHashValue(String shortURL) {
		int hashValue = 0;

		// A simple base conversion logic
		for (int i = 0; i < shortURL.length(); i++) {
			if ('a' <= shortURL.charAt(i) && shortURL.charAt(i) <= 'z')
				hashValue = hashValue * 62 + shortURL.charAt(i) - 'a';
			if ('A' <= shortURL.charAt(i) && shortURL.charAt(i) <= 'Z')
				hashValue = hashValue * 62 + shortURL.charAt(i) - 'A' + 26;
			if ('0' <= shortURL.charAt(i) && shortURL.charAt(i) <= '9')
				hashValue = hashValue * 62 + shortURL.charAt(i) - '0' + 52;
		}
		return hashValue;
	}

	public static void main(String[] args) {
		SpringApplication.run(URLShortenerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void execCodeAfterStartup() {

		// Instantiate your classes here and output their content to the console by
		// calling their toString() method
		String testURL = "www.google.com";
		shorten(testURL, null);
		String testURL2 = "www.yahoo.com";
		shorten(testURL2, null);
		// instantiate test data

		String ownedURL = "www.youtube.com";
		User TestUser = new User("FirstName", "LastName", "Username@RandomMail.com", "****");
		URL u = new URL(ownedURL, hashToString(Math.abs(ownedURL.hashCode())), TestUser);
		TestUser.addURL(u);
		userService.save(TestUser);
		urlService.saveURL(u);
	}

}
