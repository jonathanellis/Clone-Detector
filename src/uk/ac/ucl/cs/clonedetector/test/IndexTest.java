package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.Index;
import uk.ac.ucl.cs.clonedetector.Reference;

public class IndexTest extends TestCase {
public Reference r;
public Index i;
public Index i2;

	@Before
	public void setUp() throws Exception {
		//Fixtures for a single reference within two seperate Indexes
		r = new Reference("filename", 14);
		i = new Index();
		i2 = new Index();
		i.updateIndex(BigInteger.ONE, r);
		i2.updateIndex(BigInteger.ZERO, r);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	
	@Test
	public void test_lookup(){
		//Test that the previous updateIndex worked correctly
		//Test that the correct reference array is returned
		assertEquals(i.lookup(BigInteger.ZERO), new ArrayList<Reference>());
		assertEquals(i.lookup(BigInteger.ONE),new ArrayList<Reference>(Arrays.asList(r)));
		assertEquals(i2.lookup(BigInteger.ZERO), new ArrayList<Reference>());
	}
	
public static TestSuite suite() {
	TestSuite suite = new TestSuite(IndexTest.class);
	return (TestSuite) suite;
}
	
}
