package uk.ac.ucl.cs.clonedetector.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.CloneManager;
import uk.ac.ucl.cs.clonedetector.Reference;
import uk.ac.ucl.cs.clonedetector.Clone;

public class CloneManagerTest extends TestCase {

	public CloneManager c;
	public Reference r;
	public Reference r2;
	public Reference r3;
	public Reference r4;
	public Reference r5;
	public Reference r6;
	public Reference r7;
	public Reference r8;
	public Reference r9;
	public Reference r10;
	public Clone cl;
	
	@Before
	public void setUp() throws Exception {
		c = new CloneManager(1);
		r = new Reference("filename", 12);
		r2 = new Reference("filename", 15);
		r3 = new Reference("filename", 20);
		r4 = new Reference("filename", 23);
		r5 = new Reference("filename", 13);
		r6 = new Reference("filename",14);
		
		r7 = new Reference("filename", 40);
		r8 = new Reference("filename", 43);
		r9 = new Reference("filename", 43);
		r10 = new Reference("filename",44);
		cl = new Clone(r,r2,r3,r4);
	}

	@After
	public void tearDown() throws Exception {
		
		
	}
	
	@Test
	public void test_coalesce() {
		
		//Test that a n empty clone list is returned when no clones are present
		ArrayList<Clone> list = new ArrayList<Clone>();
		assertEquals(c.coalesce().toString(), list.toString());
		
		//Test that clones are added to the list and returned correctly
		c.add(cl);
		list.add(cl);
		assertEquals(c.coalesce().toString(),list.toString());
		
		//Test that coalescing of encompassing clones works correctly
		list.clear();
		
		//Overlapping clones 12-15 & 13-14
		Clone c1 = new Clone(r,r2,r3,r4);
		Clone c2 = new Clone(r5,r6,r3,r4);
		
		//Only add outer clone to test list and add both to CloneManager
		c.add(c1);
		list.add(c1);
		c.add(c2);
		
		//if equal then coalescing has worked correctly
		assertEquals(c.coalesce().toString(),list.toString());
	}
	
	@Test
	public void test_Add_and_toString() {
		//Test an empty clone manager returns nothing
		assertEquals(c.toString(), "");

		//Test Add and toString
		c.add(cl);
		assertEquals(c.toString(), cl.toString());
		
		//Test add and toSTring for two no overlapping clones
		
		//A clone that does not overlap with cl 12-15 & 40-43
		Clone c2 = new Clone(r7,r8,r9,r10);
		c.add(c2);
		
		//Should return both clones separated by newline because they don't overlap
		assertEquals(c.toString(), cl.toString()+"\n"+c2.toString());
	}
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite(CloneManagerTest.class);
		return suite;
	}

}
