package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.CloneManager;
import uk.ac.ucl.cs.clonedetector.Reference;
import uk.ac.ucl.cs.clonedetector.Clone;

public class CloneManagerTest {

	public CloneManager c;
	public Reference r;
	public Clone cl;
	
	
	@Before
	public void setUp() throws Exception {
		c = new CloneManager();
		r = new Reference("filename", 13);
		cl = new Clone(r, r, r, r);
		c.clones.add(cl);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void test_add() {
		assertEquals(c.clones.size(), 1);
		assertEquals(c.clones.get(0), cl);
		
		c.add(r,r,r,r);
		assertEquals(c.clones.size(), 2);
	}
	
	@Test
	public void test_toString() {
		assertEquals(c.toString(), cl.toString());
		
		Clone c2 = new Clone(r, r, r, r);
		c.clones.add(c2);
		
		String output = cl.toString()+"\n"+c2.toString();
		
		assertEquals(c.toString(), output);
	}
	
	
	

	
}
