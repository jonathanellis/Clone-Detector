package uk.ac.ucl.cs.clonedetector.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.CloneDetector;
import uk.ac.ucl.cs.clonedetector.CloneManager;
import uk.ac.ucl.cs.clonedetector.Index;

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
	public void test_findClonesAlgotrithmNotFound() throws IOException, NoSuchAlgorithmException {
		//Test that the correct exception is thrown, and the correct message given to the user when a file does not exist
		String[] args = new String[3];
		args[0] = "text/testing.java";
		args[1] = "-a";
		args[2] = "asdggds";
		CloneDetector.main(args);
		assertTrue(errContent.toString().contains("No such algorithm"));
	}
	
	@Test
	public void test_findClonesInFile() {
		//Test for correct clone detection in a single java file
		String[] args = new String[1];
		args[0] = "text/testing.java";
		CloneDetector.main(args);
		assertTrue(outContent.toString().contains("1-4:5-8"));
	}
	
	@Test
	public void test_findClonesInMulitpleFiles() {
		//Test for correct clone detection in multiple java files
		String[] args = new String[2];
		args[0] = "text/testing.java";
		args[1] = "text/testing2.java";
		CloneDetector.main(args);
		assertTrue(outContent.toString().contains("(text/testing.java)1-9:(text/testing2.java)1-9"));
	}
	
	@Test
	public void test_findClones() {
		//Test that CloneManager is correctly returned
		CloneDetector cd = new CloneDetector();
		Index i = new Index();
		assertEquals(cd.findClones(i).toString(), new CloneManager().toString());
		
	}
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite(CloneDetectorTest.class);
		return suite;
	}
	
}
