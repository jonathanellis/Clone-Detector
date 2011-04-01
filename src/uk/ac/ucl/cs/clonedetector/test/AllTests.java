package uk.ac.ucl.cs.clonedetector.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class AllTests  extends TestCase {
	
	public static TestSuite suite;
	
	//Collates the individual test suites into one, so that they can be run together
	
	public static Test suite(){
		suite = new TestSuite();
		suite.addTest(CloneDetectorTest.suite());
		suite.addTest(CloneManagerTest.suite());
		suite.addTest(CloneTest.suite());
		suite.addTest(IndexTest.suite());
		suite.addTest(NormalizerTest.suite());
		suite.addTest(ReferenceTest.suite());
		
		return suite;
	}

	//Main method to make it runnable from the command line and the JUINT interface
	public static void main(String[] args){
		TestResult t = null;
		suite.run(t);
		System.out.println(t);
	}
	
}
