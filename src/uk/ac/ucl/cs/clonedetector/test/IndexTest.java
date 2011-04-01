package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
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
public Index i;
public Reference r;


	@Before
	public void setUp() throws Exception {
		i = new Index();
		r = new Reference("filename", 14);
		i.add(BigInteger.ONE, r);
		i.updateIndex("text/testing.java", "StringHashCode");
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void test_computeFingerprint() throws NoSuchAlgorithmException {
		//test that an empty line returns ZERO
		assertEquals(Index.computeFingerprint("", "MD5"), BigInteger.ZERO);
		
		//test that the expected fingerprint is returned for hashcode
		assertEquals(Index.computeFingerprint("thisisaline", "StringHashCode"), BigInteger.valueOf("thisisaline".hashCode()));
		
		//test that the expected fingerprint is returned for md5
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update("thisisaline".getBytes(), 0, "thisisaline".length());
		BigInteger fingerprint = new BigInteger(1, m.digest());
		
		assertEquals(Index.computeFingerprint("thisisaline", "MD5"), fingerprint);
	}

	@Test
	public void test_lookup(){
		//test that lookup returns the correct fingerprint based on the reference entered
		assertEquals(i.lookup(r), BigInteger.ONE);
		//test that lookup returns the correct reference based on the fingerprint entered
		assertEquals(i.lookup(BigInteger.TEN), new ArrayList<Reference>());
		assertEquals(i.lookup(BigInteger.ONE).get(0), r );
	}

	
public static TestSuite suite() {
	TestSuite suite = new TestSuite(IndexTest.class);
	return (TestSuite) suite;
}
	
}
