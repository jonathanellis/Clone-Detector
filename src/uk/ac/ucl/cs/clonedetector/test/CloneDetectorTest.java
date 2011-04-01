package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.*;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.CloneDetector;
import uk.ac.ucl.cs.clonedetector.CloneManager;
import uk.ac.ucl.cs.clonedetector.Index;
import uk.ac.ucl.cs.clonedetector.Reference;

public class CloneDetectorTest extends TestCase{
	
	//Global fixtures for testing output to out and err
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void tearDown() throws Exception {
		//Reset fixtures
		System.setOut(null);
	    System.setErr(null);
	}

	
	//Test that ZERO is return when empty string is fingerprinted
	@Test
	public void test_computeFingerprint() throws NoSuchAlgorithmException {
		assertEquals(CloneDetector.computeFingerprint("", "MD5"), BigInteger.ZERO);
	}
	
	//Test to get correct extension from a file
	@Test
	public void test_getExtension() {
		//Tests for cases which should return ""
		assertEquals(CloneDetector.getExtension("path/to/file/noextension"), "");
		assertEquals(CloneDetector.getExtension(""), "");
		
		//Tests for cases which should return correct extension
		assertEquals(CloneDetector.getExtension("path/to/code.java"), "java");
		assertEquals(CloneDetector.getExtension("path/to.a.file.with.lots.of.dots/to/code.java"), "java");
		assertEquals(CloneDetector.getExtension("path/to/code.cpp"), "cpp");
		assertEquals(CloneDetector.getExtension("path/to.a.file.with.lots.of.dots/to/code.cpp"), "cpp");
		assertEquals(CloneDetector.getExtension("path/to/code.pl"), "pl");
		assertEquals(CloneDetector.getExtension("path/to.a.file.with.lots.of.dots/to/code.pl"), "pl");
	
		//Tests for absurd extensions
		assertEquals(CloneDetector.getExtension("path/to/code.thisisaveryniceextensiontohave"), "thisisaveryniceextensiontohave");
		assertEquals(CloneDetector.getExtension("path/to.a.file.with.lots.of.dots/to/code.thisisaveryniceextensiontohave"), "thisisaveryniceextensiontohave");
	}
	
	@Test
	public void test_computeFingerPrint() throws NoSuchAlgorithmException {
		//Test that correct HashCode is returned by computeFingerprint()
		BigInteger result = CloneDetector.computeFingerprint("thisisanicelineisntit", "StringHashCode");
		assertEquals(result, BigInteger.valueOf("thisisanicelineisntit".hashCode()));
		
		//Test that computeFingerprint() returns correct fingerprint using MD5
		result = CloneDetector.computeFingerprint("thisisanicelineisntit", "MD5");
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update("thisisanicelineisntit".getBytes(), 0, "thisisanicelineisntit".length());
		BigInteger testvalue = new BigInteger(1, m.digest());
		assertEquals(testvalue, result);
	}
	
	@Test
	public void test_MainUsage(){
		//Test that USAGE information is output to the user when no filesname are given
		String[] args = new String[0];
		CloneDetector.main(args);
		assertTrue(outContent.toString().contains("USAGE: java -jar clone.java <filename(s)>"));
	}
	
	@Test
	public void test_findClonesFileNotFound() throws IOException {
		//Test that the correct exception is thrown, and the correct message given to the user when a file does not exist
		String[] args = new String[1];
		args[0] = "This_is_not_a_file_that_exits.tom";
		CloneDetector.main(args);
		assertTrue(errContent.toString().contains("File not found"));
	}
	
	@Test
	public void test_findClonesAlgotrithmNotFound() throws IOException {
		//Test that the correct exception is thrown, and the correct message given to the user when a file does not exist
		CloneDetector c = new CloneDetector();
		String[] args = new String[1];
		args[0] = "text/testing.java";
		c.findClones("Tom's Algorithm", args);
		assertTrue(errContent.toString().contains("No such algorithm"));
	}
	
	@Test
	public void test_findClonesInFile() {
		//Test for correct clone detection in a single java file
		String[] args = new String[1];
		args[0] = "text/testing.java";
		CloneDetector.main(args);
		assertTrue(outContent.toString().contains("(text/testing.java)1-4:(text/testing.java)5-8"));
	}
	
	@Test
	public void test_findClonesInMulitpleFiles() {
		//Test for correct clone detection in multiple java files
		String[] args = new String[2];
		args[0] = "text/testing.java";
		args[1] = "text/testing.java";
		CloneDetector.main(args);
		assertTrue(outContent.toString().contains("(text/testing.java)1-9:(text/testing.java)1-9"));
	}
	
	@Test
	public void test_findClones() throws FileNotFoundException, NoSuchAlgorithmException, IOException{
		//Tests that the correct CloneManager objects are created containing the correct clones
		CloneManager c = new CloneManager();
		CloneDetector cd = new CloneDetector();
		c = cd.findClones(new Index(),"text/emptyfile.txt","StringHashCode" );
		
		assertEquals(c.clones.toString(), new CloneManager().clones.toString());
		c = cd.findClones(new Index(),"text/testing.java","StringHashCode" );
		
		Reference r = new Reference("text/testing.java", 1);
		Reference r2 = new Reference("text/testing.java", 4);
		Reference r3 = new Reference("text/testing.java", 5);
		Reference r4 = new Reference("text/testing.java", 8);
		
		CloneManager c2 = new CloneManager();
		c2.add(r,r2,r3,r4);
		
        assertEquals(c.clones.toString(), c2.clones.toString());
	}
	
	
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite(CloneDetectorTest.class);
		return suite;
	}
	
}
