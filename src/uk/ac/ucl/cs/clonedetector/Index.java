package uk.ac.ucl.cs.clonedetector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Index implements Iterable<BigInteger> {
	private HashMap<Reference, BigInteger> forwardIndex = new HashMap<Reference, BigInteger>();
	private HashMap<BigInteger,ArrayList<Reference>> invertedIndex = new HashMap<BigInteger,ArrayList<Reference>>();

	public void updateIndex(String filename, String algorithm) throws NoSuchAlgorithmException, IOException {
		Normalizer normalizer = new Normalizer(CloneDetector.getExtension(filename));
		BufferedReader in = new BufferedReader(new FileReader(filename));
		Reference ref = new Reference(filename, 1);
		String line;
		while ((line = in.readLine()) != null) {
			String normalizedLine = normalizer.normalize(line);
			BigInteger fingerprint = computeFingerprint(normalizedLine, algorithm);
			add(fingerprint, ref);
			
			// runs off the end:
			Reference nextRef = new Reference(filename, ref.getLine()+1);
			ref.setNext(nextRef);
			
			ref = nextRef;
		}			
	}
	

	public static BigInteger computeFingerprint(String line, String algorithm) throws NoSuchAlgorithmException {

		if (algorithm.equals("StringHashCode"))
			return BigInteger.valueOf(line.hashCode());

		/*
		 * Else hand over to MessageDigest:
		 */

		if (line.equals("")) return BigInteger.ZERO;

		MessageDigest m = MessageDigest.getInstance(algorithm);
		m.update(line.getBytes(), 0, line.length());
		BigInteger fingerprint = new BigInteger(1, m.digest());
		return fingerprint;
	}
	
	// Update both forward and inverse indexes:
	public void add(BigInteger fingerprint, Reference reference) {
		// Update forward index:
		forwardIndex.put(reference, fingerprint);
		
		// Update inverted index:
		if (fingerprint == BigInteger.ZERO) return;
		if (!invertedIndex.containsKey(fingerprint)) {
			invertedIndex.put(fingerprint, new ArrayList<Reference>());
		}
		invertedIndex.get(fingerprint).add(reference);
	}

	
	// Inverted lookup:
	public ArrayList<Reference> lookup(BigInteger fingerprint) {
		ArrayList<Reference> matches = invertedIndex.get(fingerprint);
		if (matches == null) return new ArrayList<Reference>();
		return matches;
	}
	
	
	// Forward lookup:
	public BigInteger lookup(Reference ref) {
		return forwardIndex.get(ref);
	}


	@Override
	public Iterator<BigInteger> iterator() {
		return invertedIndex.keySet().iterator();
	}
	
	
}