package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.Clone;
import uk.ac.ucl.cs.clonedetector.CloneDetector;
import uk.ac.ucl.cs.clonedetector.Options;
import uk.ac.ucl.cs.clonedetector.Reference;

public class CloneTest extends TestCase {

	public Clone c;
	public Clone c2;
	public Clone c3;
	public Reference r;
	public Reference r2;
	public Reference r3;
	public Reference r4;
	
	@Before
	public void setUp() throws Exception {
		r = new Reference("filename", 14);
		r2 = new Reference("filename", 15);
		r3 = new Reference("filename", 16); 
		r4 = new Reference("filename", 17);
		c = new Clone(r,r2,r3,r4);
		c2 = new Clone(r3,r4,r,r2);
		c3 = new Clone(r4,r2,r3,r);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	
	@Test
	public void test_toString() {
		//Set up options so that we know there is only one file to check
		String[] args = new String[1];
		args[0] = "filename";
		CloneDetector.options = new Options(args);
		
		//Test clones are output correctly
		String output = "14-15:16-17";
		assertEquals(c.toString(), output);
		
		String output2 = "14-15:16-17";
		assertEquals(c2.toString(), output2);
	

	}
	
	@Test
	public void test_getIStart() {
		//Test that istart is returned correctly
		assertEquals(c.getIStart(), r);
	}
	
	@Test
	public void test_getLength() {
		//Test that length is correctly returned
		assertEquals(c.getLength(), 2);
	}
	
	@Test
	public void test_encompasses() {
		//New fixtures for encompass tests
		Reference re = new Reference("filename", 26);
		Reference re2 =new Reference("filename", 28);
		Reference re3 =new Reference("filename", 29);
		Reference re4 =new Reference("filename", 20);
		
		//A clones that is not encompassed by c
		Clone c5 = new Clone(re,re2,re3,re4);
		
		//Test that non-encompassing clones return false
		assertEquals(c.encompasses(c5), false);
		
		//Test that encompassing clones (or same in this case) return true
		assertEquals(c.encompasses(c), true);
		
		//Test that different file names for iStart and jStart always return false
		Reference re5 =new Reference("different", 20);
		c5 = new Clone(re,re2,re5,re4);
		assertEquals(c.encompasses(c5), false);
	}
	
	@Test
	public void test_compareTo() {
		//Test clones are correctly compared
		assertTrue(c.compareTo(c3) < 0);
		assertTrue(c3.compareTo(c) > 0);
	}
	
	@Test
	public void test_overlapsitself() {
		assertTrue(!c.overlapsItself());
	}

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(CloneTest.class);
		return  suite;
	}

	
}
