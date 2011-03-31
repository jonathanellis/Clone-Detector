package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.Line;



public class LineTest {

	public Line l;
	public Line l2;
	
	@Before
	public void setUp() throws Exception {
		
		 l = new Line("line", 14, BigInteger.ZERO);
		 l2 = new Line("line2", 12324, BigInteger.ONE);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_variables()  {
		assertEquals(l.fingerprint, BigInteger.ZERO);
		assertEquals(l.lineNumber, 14);
		assertEquals(l.lineContents, "line");
	}
	
	
	
	@Test
	public void test_hashCode()  {
		assertEquals(l.hashCode(), BigInteger.ZERO.hashCode());
	}
	
	@Test
	public void test_equals()  {
		assertEquals(l.equals(new Line("line", 14, BigInteger.ZERO)), true);
		assertEquals(l.equals(l2), false);
		
	}
	
	@Test
	public void test_compareTo()  {
		
		assertEquals(l.compareTo(l2),(BigInteger.ZERO.subtract(BigInteger.ONE)).intValue());
		
	}
	

	
}
