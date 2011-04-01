package uk.ac.ucl.cs.clonedetector.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
		//Fixtures for creating a cloneManager object with one clone in it's clone list
		c = new CloneManager();
		r = new Reference("filenamexxx", 13);
		cl = new Clone(r, r, r, r);
		c.clones.add(cl);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void test_add() {
		//Test that clones are added correctly
		assertEquals(c.clones.size(), 1);
		assertEquals(c.clones.get(0), cl);
		
		c.add(r,r,r,r);
		assertEquals(c.clones.size(), 2);
		
		assertTrue(c.clones.get(0).toString().contains("filenamexxx"));
	}
	
	@Test
	public void test_toString() {
		//TEst that the correct clones are output by printing cloneManager to string
		assertEquals(c.toString(), cl.toString());
		assertTrue(c.toString().contains("filenamexxx"));
		
		Clone c2 = new Clone(r, r, r, r);
		c.clones.add(c2);
		
		//Test that muliple clones are output to string correctly
		String output = cl.toString()+"\n"+c2.toString();
		
		assertEquals(c.toString(), output);
	}
	
	
	

	
}
