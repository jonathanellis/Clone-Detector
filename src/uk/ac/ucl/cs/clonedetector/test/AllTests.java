package uk.ac.ucl.cs.clonedetector.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class AllTests  extends TestCase {
	
	public static TestSuite suite;
	
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

	public static void main(String[] args){
		TestResult t = null;
		suite.run(t);
		System.out.println(t);
	}
	
}
