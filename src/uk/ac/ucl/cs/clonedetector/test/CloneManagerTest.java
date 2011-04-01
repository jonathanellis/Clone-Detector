package uk.ac.ucl.cs.clonedetector.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.cs.clonedetector.CloneDetector;
import uk.ac.ucl.cs.clonedetector.CloneManager;
import uk.ac.ucl.cs.clonedetector.Options;
import uk.ac.ucl.cs.clonedetector.Reference;
import uk.ac.ucl.cs.clonedetector.Clone;

public class CloneManagerTest extends TestCase {

	public CloneManager c;
	
	public Clone cl;
	public Reference r1;
	public Reference r2;
	public Reference r3;
	public Reference r4;
	
	@Before
	public void setUp() throws Exception {
		c = new CloneManager();
		r1 = new Reference("filename", 1);
		r2 = new Reference("filename", 5);
		r3 = new Reference("filename", 11);
		r4 = new Reference("filename", 15);
		cl = new Clone(r1,r2,r3,r4);
	}

	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void test_coalesce() {
		
		//set-up file number
		String[] args = new String[1];
		args[0] = "filename";
		CloneDetector.options = new Options(args);
		
		//add first clone to cloneManager
		c.add(cl);
		
		//add expected clone to test list
		ArrayList<Clone> list = new ArrayList<Clone>();
		list.add(cl);
		
		//Test that one clone is not coalesced and output correctly
		assertEquals(c.coalesce().toString(),list.toString());
		
		//Creating a new clone that is inside cl clone
		Reference re1 = new Reference("filename", 2);
		Reference re2 = new Reference("filename", 4);
		Reference re3 = new Reference("filename", 12);
		Reference re4 = new Reference("filename", 14);
		Clone cl2 = new Clone(re1,re2,re3,re4);	
		
		//c now contains both clones
		c.add(cl2);
		
		//Test list only containing the clone we expect to be output
		list.clear();
		list.add(cl);
		
		//Test that only the encompassing clone is output from coalescing
		assertEquals(c.coalesce().toString(),list.toString());
		
		//create new clone that does not overlap with either of the other 2
		Reference re5 = new Reference("filename", 99);
		Reference re6 = new Reference("filename", 101);
		Reference re7 = new Reference("filename", 104);
		Reference re8 = new Reference("filename", 106);
		Clone cl3 = new Clone(re5,re6,re7,re8);
		
		//add a third clone that does not overlap with either of the other 2
		c.add(cl3);
		
		//Test that the non-overlapping clone is output correctly when 3 clones are present
		assertTrue(c.coalesce().toString().contains(cl3.toString()));
		
	}
	
	@Test
	public void test_Add_and_toString() {
		//set up file numbers
		String[] args = new String[1];
		args[0] = "filename";
		CloneDetector.options = new Options(args);
		
		//add first clone to clone manager
		c.add(cl);
		
		//add expected clone to test list
		ArrayList<Clone> list = new ArrayList<Clone>();
		list.add(cl);
		
		//Test that one clone output to string correctly
		assertEquals(c.toString(),cl.toString());
		
		list.add(cl);
		//Test that similar clones are coalesced
		assertEquals(c.toString(),cl.toString());
		
	}
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite(CloneManagerTest.class);
		return suite;
	}

}
