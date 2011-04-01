package uk.ac.ucl.cs.clonedetector;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Index {
	private HashMap<BigInteger,ArrayList<Reference>> index = new HashMap<BigInteger,ArrayList<Reference>>();

	public void updateIndex(BigInteger fingerprint, Reference reference) {
		if (fingerprint == BigInteger.ZERO) return;
		
		if (!index.containsKey(fingerprint)) {
			index.put(fingerprint, new ArrayList<Reference>());
		}
		index.get(fingerprint).add(reference);
	}
	
	public ArrayList<Reference> lookup(BigInteger fingerprint) {
		ArrayList<Reference> matches = index.get(fingerprint);
		if (matches == null) return new ArrayList<Reference>();
		return matches;
	}
	

	
}