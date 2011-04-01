package uk.ac.ucl.cs.clonedetector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class CloneDetector {


	/**
	 * Find clones in the given filename using the specified algorithm.
	 * 
	 * @param filename
	 * @param algorithm
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 *             Thrown if the algorithm is not available on the system.
	 */
	public CloneManager findClones(Index index, String filename, String algorithm) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		CloneManager cloneManager = new CloneManager();
		Normalizer normalizer = new Normalizer(getExtension(filename));
		String line;
		Reference iCurrent = new Reference(filename, 1);

		Reference iStart = null;
		Reference iEnd = null;
		Reference jStart = null;
		Reference jEnd = null;
		
		Reference jPrev = null;
		
		BufferedReader in = new BufferedReader(new FileReader(filename));

		while ((line = in.readLine()) != null) {
			
			String normalizedLine = normalizer.normalize(line);
			BigInteger fingerprint = computeFingerprint(normalizedLine, algorithm);

			ArrayList<Reference> matchingLines = index.lookup(fingerprint);

			// Start of a new match:
			if (matchingLines.size() > 0 && iStart == null) {
				Reference firstMatch = matchingLines.get(0);
				jPrev = firstMatch.clone();
				
				iStart = iCurrent.clone();
				iEnd = iStart.clone();
				jStart = firstMatch.clone();
				jEnd = jStart.clone();
			} else if (iStart != null) {
				if (matchingLines.contains(jPrev.successor())) { // continue match
					iEnd.incLine();
					jEnd.incLine();
					jPrev.incLine();
				} else { // end match
					cloneManager.add(iStart, iEnd, jStart, jEnd);
					iStart = null;
					iEnd = null;
					jStart = null;
					jEnd = null;
					jPrev = null;
				}
			}


			index.updateIndex(fingerprint, iCurrent.clone());
			iCurrent.incLine();
		}
		return cloneManager;
	}

	/**
	 * Retrieves the file extension from a given relative or absolute filename.
	 * Filenames with no extension return "".
	 * 
	 * @param filename
	 *            Filename to get the extension for
	 * @return Extension for the filename
	 */
	public static String getExtension(String filename) {
		String chunks[] = filename.split("\\.");
		if (chunks.length > 1)
			return chunks[chunks.length - 1];
		return "";
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

	
	/*
	 * Handles all the output:
	 */
	public void findClones(String algorithm, String[] filenames) {
		Index index = new Index();
		
		for (int i = 0; i < filenames.length; i++) {
			try {
				System.out.println(filenames[i] + ":");
				CloneManager clones = findClones(index, filenames[i], algorithm);
				System.out.println(clones);
				if (filenames.length > 1 && i < filenames.length - 1) // if it's the final line
					System.out.println("");
			} catch (FileNotFoundException e) {
				System.err.println("File not found!");
			} catch (NoSuchAlgorithmException e) {
				System.err.println("No such algorithm available on this system!");
			} catch (IOException e) {
				System.err.println("An error occurred whilst reading the file.");
			}
		}
	}

	public static void main(String[] args) {
		CloneDetector cd = new CloneDetector();
		if (args.length < 1) {
			System.out.println("USAGE: java -jar clone.java <filename(s)>");
		} else {
			cd.findClones("StringHashCode", args);
		}
	}
}
