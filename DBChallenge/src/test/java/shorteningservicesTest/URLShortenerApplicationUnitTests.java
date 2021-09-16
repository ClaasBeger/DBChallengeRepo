package shorteningservicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import shorteningservices.URLShortenerApplication;

public class URLShortenerApplicationUnitTests {

	@Test
	public void idConversionTest() {
		String testURL = "www.google.com";
		int hashValue = Math.abs(testURL.hashCode());
		String alias = URLShortenerApplication.hashToString(hashValue);
		int reversedHashValue = URLShortenerApplication.shortURLtoHashValue(alias);
		assertEquals(hashValue, reversedHashValue);
	}
	
	
}
