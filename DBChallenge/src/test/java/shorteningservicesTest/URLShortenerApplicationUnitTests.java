package shorteningservicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import shorteningservices.URLShortenerApplication;
import shorteningservices.entity.CallStatistics;
import shorteningservices.entity.URL;
import shorteningservices.entity.User;
import shorteningservices.service.UserService;

public class URLShortenerApplicationUnitTests {

	@Test
	public void idConversionTest() {
		String testURL = "www.google.com";
		int hashValue = Math.abs(testURL.hashCode());
		String alias = URLShortenerApplication.hashToString(hashValue);
		int reversedHashValue = URLShortenerApplication.shortURLtoHashValue(alias);
		assertEquals(hashValue, reversedHashValue);
	}

	@Test
	public void createEmptyUserTest() {
		User testUser = new User("John", "Doe", "JohnDoe1", "SafePassword");

		// testing whether a call to the url entries will be tolerated although empty
		// and asserts that list is empty
		assertFalse(testUser.getURLs().iterator().hasNext());
	}

	@Test
	public void addURLmanually() {
		User testUser2 = new User("Jane", "Doe", "JaneDoe2", "SaferPassword");
		URL testURL = new URL("www.google.com", "alias", null);
		testUser2.addURL(testURL);

		// assert that URL is added into user specific list
		assertTrue(testUser2.getURLs().get(0).equals(testURL));

		// assert that owner field gets set correctly in URL
		assertTrue(testURL.getOwner().equals(testUser2));
	}

	@Test
	public void remURLmanually() {
		User testUser2 = new User("Jane", "Doe", "JaneDoe2", "SaferPassword");
		URL testURL = new URL("www.google.com", "alias", null);
		testUser2.addURL(testURL);

		testUser2.removeURL(testURL);

		// assert that URL is removed from URL list
		assertTrue(testUser2.getURLs().isEmpty());

		// assert that owner field is reset after removal
		assertTrue(testURL.getOwner() == null);
	}

	@Test
	public void recordCall() {
		URL testURL = new URL("www.google.com", "alias", null);
		testURL.setStats(new CallStatistics(null, LocalDateTime.now(), new LinkedList<LocalDateTime>(), 0,
				new LinkedList<User>()));
		LocalDateTime creationTime = LocalDateTime.now();
		testURL.getStats().recordCall(null, creationTime);

		assertEquals(testURL.getStats().getCallTimes().get(0), creationTime);
		assertTrue(testURL.getStats().getCallsTotal() == 1);
		assertTrue(testURL.getStats().getCreator() == null);
	}

}
