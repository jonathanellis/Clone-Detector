package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
		//Test that clone list is returned
		ArrayList<Clone> list = new ArrayList<Clone>();
		assertEquals(c.coalesce().toString(), list.toString());
		
		//Test that clones are added to the list and returned
		c.add(cl);
		list.add(cl);
		assertEquals(c.coalesce().toString(),list.toString() );
		
		//Test coalescing of encompassing clones
		list.clear();
		
		Clone c1 = new Clone(r,r2,r3,r4);
		Clone c2 = new Clone(r5,r6,r3,r4);
		
		c.add(c1);
		list.add(c1);
		c.add(c2);
		
		assertEquals(c.coalesce().toString(),list.toString() );
	}
	
	@Test
	public void test_toString() {
		assertEquals(c.toString(), "");
		
		
		//Test Add and toString
		c.add(cl);
		assertEquals(c.toString(), cl.toString());
		
		//Test add and toSTring for two no overlapping clones
		Clone c2 = new Clone(r7,r8,r9,r10);
		c.add(c2);
		
		assertEquals(c.toString(), cl.toString()+"\n"+c2.toString());
	}
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite(CloneManagerTest.class);
		return suite;
	}
	

	
}
