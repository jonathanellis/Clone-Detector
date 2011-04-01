package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.Reference;



public class ReferenceTest {

public Reference r;
public Reference r2;
public Reference r3;
	@Before
	public void setUp() throws Exception {
		r = new Reference("filename", 14);
		r2 = new Reference("filenames", 124);
		r3 = new Reference("filenames", 4);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void test_getFilename()  {
		assertEquals(r.getFilename(), "filename");
	}
	
	@Test
	public void test_getLine(){
		assertEquals(r.getLine(), 14);
	}
	
	@Test
	public void test_incLine(){
		r.incLine();
		assertEquals(r.getLine(), 15);
	}
	
	@Test
	public void test_successor(){
		assertEquals(r.successor(),new Reference("filename", 15) );
	}
	
	@Test
	public void test_clone(){
		assertEquals(r.clone(),new Reference("filename", 14) );
	}

	@Test
	public void test_toString(){
		assertEquals(r.toString(), "(filename)" + 14 );
	}
	
	@Test
	public void test_toEquals(){
		assertEquals(r.equals(r) , true);
		assertEquals(r.equals(r2) , false);
		assertEquals(r.equals(new Integer(12)) , false);
	}
	
	@Test
	public void test_compareTo(){
		assertEquals(r.compareTo(r) , 0);
		assertTrue(r.compareTo(r2)<0);
		assertTrue(r.compareTo(r3)>0);
	}

}
