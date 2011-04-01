package uk.ac.ucl.cs.clonedetector.test;
import static org.junit.Assert.*;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.Normalizer;

public class NormalizerTest extends TestCase{
String lang;

Normalizer normlang;
Normalizer normnolang;

	@Before
	public void setUp() throws Exception {
		normlang = new Normalizer("java");
		normnolang = new Normalizer("");
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test_normalize()  {
		
		//Test cases for var replacement
		assertEquals(normlang.normalize("int foo = 4"), "int%var%=%num%");
		assertEquals(normlang.normalize("double foo = 12.3"), "double%var%=%num%");
		assertEquals(normlang.normalize("float foo = 12.3"), "float%var%=%num%");
		assertEquals(normlang.normalize("byte foo = 4"), "byte%var%=%num%");
		assertEquals(normlang.normalize("char foo = \"c\""), "char%var%=%str%");
		
		//Test case with added keywords
		assertEquals(normlang.normalize("public static int foo = 4"), "publicstaticint%var%=%num%");
		
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
		
		//Test case for stripping white space
		assertEquals(normnolang.normalize("a                   b                   c"), "abc");
	}
	
	@Test
	public void test_getLanguage(){
		//Test that the correct language is returned from the file extension
		assertEquals(normlang.getLanguage(), "java");
	}
	
	@Test
	public void test_getKeywords(){
		//Test that the correct keywords are loaded from the language and file extension
		String keyword = normlang.getKeywords().substring(0, 8);
		assertEquals(keyword, "abstract");
	}
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite(NormalizerTest.class);
		return suite;
	}
}
