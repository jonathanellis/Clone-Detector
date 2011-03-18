package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.CloneDetector;


public class CloneDetectorTest {


	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void test_computeFingerprint() throws NoSuchAlgorithmException {
		assertEquals(CloneDetector.computeFingerprint("", "MD5"), BigInteger.ZERO);
		//assertEquals(CloneDetector.computeFingerprint("%hello_2819£%^", "MD5"), new BigInteger());
	}


}
