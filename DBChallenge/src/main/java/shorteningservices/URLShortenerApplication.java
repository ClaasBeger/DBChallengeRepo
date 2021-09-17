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
	
	//URL shortening logic partially adapted from https://www.geeksforgeeks.org/how-to-design-a-tiny-url-or-url-shortener/
	public boolean shorten(String original, String customAlias) {
		try {
			String Alias;
			if (customAlias != null) {
				urlService.saveURL(new URL(original, customAlias));
				Alias = customAlias;
			}
			else {
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
	
	public static int shortURLtoHashValue(String shortURL)
    {
        int hashValue = 0;
     
        // A simple base conversion logic
        for (int i = 0; i < shortURL.length(); i++)
        {
            if ('a' <= shortURL.charAt(i) &&
                       shortURL.charAt(i) <= 'z')
            hashValue = hashValue * 62 + shortURL.charAt(i) - 'a';
            if ('A' <= shortURL.charAt(i) &&
                       shortURL.charAt(i) <= 'Z')
            hashValue = hashValue * 62 + shortURL.charAt(i) - 'A' + 26;
            if ('0' <= shortURL.charAt(i) &&
                       shortURL.charAt(i) <= '9')
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
		userService.save(TestUser);
		urlService.saveURL(new URL(ownedURL, hashToString(Math.abs(ownedURL.hashCode())), TestUser.getId()));
	}

}
