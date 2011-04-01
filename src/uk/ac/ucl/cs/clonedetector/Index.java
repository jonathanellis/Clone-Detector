/**
 * Copyright (c) 2011 Team Apollo
 */
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

/**
 * This class encapsulates both a forward and inverted index which are used to
 * store information about the files the clone detection is being performed on.
 * 
 * @author apollo
 * @version 1.0
 */
public class Index implements Iterable<BigInteger> {

	private HashMap<Reference, BigInteger> forwardIndex = new HashMap<Reference, BigInteger>();
	private HashMap<BigInteger, ArrayList<Reference>> invertedIndex = new HashMap<BigInteger, ArrayList<Reference>>();

	/**
	 * Updates both the forward and inverted indexes by reading in the supplied
	 * filename with the supplied algorithm to compute the fingerprint.
	 * 
	 * @param filename
	 *            The filename to be indexed.
	 * @param algorithm
	 *            The algorithm to be used to compute the fingerprint for each
	 *            line.
	 * @throws NoSuchAlgorithmException
	 *             If the algorithm is not available on the system.
	 * @throws IOException
	 *             If the file cannot be read.
	 */
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
			Reference nextRef = new Reference(filename, ref.getLine() + 1);
			ref.setNext(nextRef);

			ref = nextRef;
		}
	}

	/**
	 * Computes a fingerprint for a single line using the supplied algorithm.
	 * 
	 * @param line
	 *            The line to be fingerprinted.
	 * @param algorithm
	 *            The algorithm to do the fingerprinting.
	 * @return The fingerprint of the line.
	 * @throws NoSuchAlgorithmException
	 *             Thrown if the algorithm is not available on the system.
	 */
	public static BigInteger computeFingerprint(String line, String algorithm) throws NoSuchAlgorithmException {

		if (algorithm.equals("StringHashCode"))
			return BigInteger.valueOf(line.hashCode());

		// Else hand over to MessageDigest:
		if (line.equals(""))
			return BigInteger.ZERO;

		MessageDigest m = MessageDigest.getInstance(algorithm);
		m.update(line.getBytes(), 0, line.length());
		BigInteger fingerprint = new BigInteger(1, m.digest());
		return fingerprint;
	}

	/**
	 * Adds a (fingerprint, reference) pair to the forward and inverted indexes.
	 * For the forward index, a key is created for the reference and the
	 * fingerprint is stored as the value. For the inverted index, a key and
	 * postings list is created for the fingerprint if it doesn't already exist
	 * and then the reference is added to the postings list.
	 * 
	 * @param fingerprint
	 * @param reference
	 */
	public void add(BigInteger fingerprint, Reference reference) {
		// Update forward index:
		forwardIndex.put(reference, fingerprint);

		// Update inverted index:
		if (fingerprint == BigInteger.ZERO)
			return;
		if (!invertedIndex.containsKey(fingerprint)) {
			invertedIndex.put(fingerprint, new ArrayList<Reference>());
		}
		invertedIndex.get(fingerprint).add(reference);
	}

	/**
	 * Does an inverted lookup on the index. For the supplied fingerprint, the
	 * corresponding postings list will be supplied in O(1) time.
	 * 
	 * @param fingerprint
	 *            The fingerprint to be looked up in the inverted index.
	 * @return The postings list of references that correspond to that
	 *         fingerprint.
	 */
	public ArrayList<Reference> lookup(BigInteger fingerprint) {
		ArrayList<Reference> matches = invertedIndex.get(fingerprint);
		if (matches == null)
			return new ArrayList<Reference>();
		return matches;
	}

	/**
	 * Does a forward lookup on the index. For the supplied reference, the
	 * corresponding fingerprint will be returned. This saves the line having to
	 * be stored and the fingerprint recomputed every time it is required.
	 * 
	 * @param ref
	 *            The reference to be looked up.
	 * @return The fingerprint of that reference.
	 */
	public BigInteger lookup(Reference ref) {
		return forwardIndex.get(ref);
	}

	@Override
	public Iterator<BigInteger> iterator() {
		return invertedIndex.keySet().iterator();
	}

}