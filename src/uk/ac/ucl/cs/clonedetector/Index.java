package uk.ac.ucl.cs.clonedetector;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Index {
	private HashMap<BigInteger,ArrayList<Integer>> index = new HashMap<BigInteger,ArrayList<Integer>>();

	public void updateIndex(BigInteger fingerprint, int lineNumber) {
		if (fingerprint == BigInteger.ZERO) return;
		
		if (!index.containsKey(fingerprint)) {
			index.put(fingerprint, new ArrayList<Integer>());
		}
		index.get(fingerprint).add(lineNumber);
	}
	
	public ArrayList<Integer> linesWithFingerprint(BigInteger fingerprint) {
		ArrayList<Integer> matches = index.get(fingerprint);
		if (matches == null) return new ArrayList<Integer>();
		return matches;
	}
	
	
}