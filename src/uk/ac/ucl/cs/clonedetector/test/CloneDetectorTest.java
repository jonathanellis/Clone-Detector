package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.*;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.CloneDetector;
import uk.ac.ucl.cs.clonedetector.CloneManager;
import uk.ac.ucl.cs.clonedetector.Index;
import uk.ac.ucl.cs.clonedetector.Reference;

public class CloneDetectorTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

	}

	@After
	public void tearDown() throws Exception {
		System.setOut(null);
	    System.setErr(null);
	}

	
	@Test
	public void test_computeFingerprint() throws NoSuchAlgorithmException {
		assertEquals(CloneDetector.computeFingerprint("", "MD5"), BigInteger.ZERO);
	}
	
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
	
		assertEquals(CloneDetector.getExtension("path/to/code.thisisaveryniceextensiontohave"), "thisisaveryniceextensiontohave");
		assertEquals(CloneDetector.getExtension("path/to.a.file.with.lots.of.dots/to/code.thisisaveryniceextensiontohave"), "thisisaveryniceextensiontohave");
	}
	
	@Test
	public void test_computeFingerPrint() throws NoSuchAlgorithmException {
		BigInteger result = CloneDetector.computeFingerprint("thisisanicelineisntit", "StringHashCode");
		assertEquals(result, BigInteger.valueOf("thisisanicelineisntit".hashCode()));
		
		result = CloneDetector.computeFingerprint("thisisanicelineisntit", "MD5");
		
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update("thisisanicelineisntit".getBytes(), 0, "thisisanicelineisntit".length());
		BigInteger testvalue = new BigInteger(1, m.digest());
		
		assertEquals(testvalue, result);
	}
	
	@Test
	public void test_MainUsage(){
		String[] args = new String[0];
		CloneDetector.main(args);
		assertTrue(outContent.toString().contains("USAGE: java -jar clone.java <filename(s)>"));
	}
	
	@Test
	public void test_findClonesFileNotFound() throws IOException {
		String[] args = new String[1];
		args[0] = "This_is_not_a_file_that_exits.tom";
		CloneDetector.main(args);
		assertTrue(errContent.toString().contains("File not found"));
	
	}
	
	@Test
	public void test_findClonesInFile() {
		String[] args = new String[1];
		args[0] = "text/testing.txt";
		CloneDetector.main(args);
		assertTrue(outContent.toString().contains("(text/testing.txt)1-4:(text/testing.txt)5-8"));
	}
	
	@Test
	public void test_findClonesInMulitpleFiles() {
		String[] args = new String[2];
		args[0] = "text/testing.txt";
		args[1] = "text/testing.txt";
		CloneDetector.main(args);
		assertTrue(outContent.toString().contains("(text/testing.txt)1-8:(text/testing.txt)1-8"));
	}
	
	@Test
	public void test_findClones() throws FileNotFoundException, NoSuchAlgorithmException, IOException{
		CloneManager c = new CloneManager();
		CloneDetector cd = new CloneDetector();
		c = cd.findClones(new Index(),"text/emptyfile.txt","StringHashCode" );
		
		assertEquals(c.clones.toString(), new CloneManager().clones.toString());
		c = cd.findClones(new Index(),"text/testing.txt","StringHashCode" );
		
		Reference r = new Reference("text/testing.txt", 1);
		Reference r2 = new Reference("text/testing.txt", 4);
		Reference r3 = new Reference("text/testing.txt", 5);
		Reference r4 = new Reference("text/testing.txt", 8);
		
		CloneManager c2 = new CloneManager();
		c2.add(r,r2,r3,r4);
		
        assertEquals(c.clones.toString(), c2.clones.toString());
	}
	
}
