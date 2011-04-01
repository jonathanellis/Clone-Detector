package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.Reference;



public class ReferenceTest extends TestCase{

public Reference r;
public Reference r2;
public Reference r3;
public Reference r4;
	@Before
	public void setUp() throws Exception {
		//Fixtures for three different references
		r = new Reference("filename", 14);
		r2 = new Reference("filenames", 124);
		r3 = new Reference("filenamess", 4);
		r4 = new Reference("filenamess", 4, r);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void test_getFilename()  {
		//Test that correct filename is returned
		assertEquals(r.getFilename(), "filename");
	}
	
	@Test 
	public void test_getLine(){
		//Test that correct line is returned
		assertEquals(r.getLine(), 14);
	}
	
	@Test
	public void test_next(){
		//test that the correct successor is returned by next()
		assertEquals(r4.next(), r);
	}
	
	@Test
	public void test_setNext(){
		//test that the sucessor reference is correctly set by setNext()
		r4.setNext(r2);
		assertEquals(r4.next(), r2);
	}
	
	@Test
	public void test_incLine(){
		//Test that the line is incremented correctly
		r.incLine();
		assertEquals(r.getLine(), 15);
	}
	
	@Test
	public void test_clone(){
		//Test that correct reference to a clone is returned
		assertEquals(r.clone(),new Reference("filename", 14) );
		assertEquals(r2.clone(),new Reference("filenames", 124) );
		assertEquals(r3.clone(),new Reference("filenamess", 4) );
	}

	@Test
	public void test_toString(){
		//Test that references are correctly output to string
		assertEquals(r.toString(), "(filename)" + 14 );
		assertEquals(r2.toString(), "(filenames)" + 124 );
		assertEquals(r3.toString(), "(filenamess)" + 4 );
	}
	
	@Test
	public void test_toEquals(){
		//Test that references are correctly returned true if they are the same
		assertEquals(r.equals(r) , true);
		assertEquals(r.equals(r2) , false);
		assertEquals(r.equals(new Integer(12)) , false);
	}
	
	@Test
	public void test_compareTo(){
		//Test that references are correctly compared to each other
		assertEquals(r.compareTo(r) , 0);
		assertTrue(r.compareTo(r2)<0);
		assertTrue(r.compareTo(r3)>0);
	}
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite(ReferenceTest.class);
		return suite;
	}

}
