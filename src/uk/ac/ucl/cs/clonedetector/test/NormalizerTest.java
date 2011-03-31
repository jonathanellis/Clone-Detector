package uk.ac.ucl.cs.clonedetector.test;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.CloneDetector;
import uk.ac.ucl.cs.clonedetector.Normalizer;

public class NormalizerTest {
String lang;

	@Before
	public void setUp() throws Exception {
	
	}

	@After
	public void tearDown() throws Exception {
		
	}

	
	@Test
	public void test_normalize()  {
		//Fixture objects with and without language specified
		Normalizer normlang = new Normalizer("java");
		Normalizer normnolang = new Normalizer("");
		
		//Test cases for var replacement
		assertEquals(normlang.normalize("int foo = 4"), "int%VAR%=4");
		assertEquals(normlang.normalize("double foo = 12.3"), "double%VAR%=12.3");
		assertEquals(normlang.normalize("float foo = 12.3"), "float%VAR%=12.3");
		assertEquals(normlang.normalize("byte foo = 4"), "byte%VAR%=4");
		//assertEquals(normlang.normalize("char foo = \"c\""), "char%VAR%=\"c\"");
		
		//Test case with added keywords
		assertEquals(normlang.normalize("public static int foo = 4"), "publicstaticint%VAR%=4");
		
		//Test cases for no lang normalization
		assertEquals(normnolang.normalize("int foo = 4"), "intfoo=4");
		assertEquals(normnolang.normalize("double foo = 12.3"), "doublefoo=12.3");
		assertEquals(normnolang.normalize("float foo = 12.3"), "floatfoo=12.3");
		assertEquals(normnolang.normalize("byte foo = 4"), "bytefoo=4");
		assertEquals(normnolang.normalize("char foo = \"c\""), "charfoo=\"c\"");
		
		//Test case for same return on same input for no specified lang
		assertEquals(normnolang.normalize("int foo = 4"), normnolang.normalize("int foo = 4"));
		
		//Test case for same return on same input for specified lang
		assertEquals(normlang.normalize("int foo = 4"), normlang.normalize("int foo = 4"));
		
		//Test case for non-equal normalisation when language is or is not specified
		assertFalse(normlang.normalize("int foo = 4").equals(normnolang.normalize("int foo = 4")));
		
		//Test case for strip white space
		assertEquals(normnolang.normalize("a                   b                   c"), "abc");
	}
}
