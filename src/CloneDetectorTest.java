
import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CloneDetectorTest {


	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test_stripWhitespace() {
		assertEquals(CloneDetector.stripWhitespace("test"), "test");
		assertEquals(CloneDetector.stripWhitespace("hello hello"), "hellohello");
		assertEquals(CloneDetector.stripWhitespace("this is a test"), "thisisatest");	
	}
	
	@Test
	public void test_computeFingerprint() {
		assertEquals(CloneDetector.computeFingerprint("", CloneDetector.FingerprintMethod.MD5_HASH), BigInteger.ZERO);
		//assertEquals(CloneDetector.computeFingerprint("%hello_2819£%^", CloneDetector.FingerprintMethod.MD5_HASH), new BigInteger());
	}


}
