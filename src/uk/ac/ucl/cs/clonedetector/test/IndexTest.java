package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.Clone;



public class IndexTest {

	public Clone c;
	
	@Before
	public void setUp() throws Exception {
		//c = new Clone(1, 10, 3);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void test_newClone() throws NoSuchAlgorithmException {
		assertEquals(c.toString(), "1-4:10-13");
	}
	

	
}
