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
	}
	
	@Test
	public void test_getExtension() {
		//Tests for cases which should return ""
		//assertEquals(CloneDetector.getExtension("path/to/file/noextension"), "");
		//assertEquals(CloneDetector.getExtension(""), "");
		
		//Tests for cases which should return correct extension
		assertEquals(CloneDetector.getExtension("path/to/code.java"), "java");
		assertEquals(CloneDetector.getExtension("path/to.a.file.with.lots.of.dots/to/code.java"), "java");
		assertEquals(CloneDetector.getExtension("path/to/code.cpp"), "cpp");
		assertEquals(CloneDetector.getExtension("path/to.a.file.with.lots.of.dots/to/code.cpp"), "cpp");
	}
	
}
