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
  
  /*
   * Variables for the findClones() method (and its related methods).
   * The variables are re-used each iteration for efficiency reasons.
   * ("collider" is what the collision is colliding with)
   */

	private Index index = new Index();
  
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
	public ArrayList<Clone> findClones(String filename, String algorithm) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		ArrayList<Clone> clones = new ArrayList<Clone>();
		BufferedReader in = new BufferedReader(new FileReader(filename));
		Normalizer normalizer = new Normalizer(getExtension(filename));
		String line;
		int lineNum = 1;
		
		int iStart = -1;
		int jStart = -1;
		int matchLength = -1;
		int prevMatchingLine = -1;
		
		while ((line = in.readLine()) != null) {
			String normalizedLine = normalizer.normalize(line);
			BigInteger fingerprint = computeFingerprint(normalizedLine, algorithm);
			
			ArrayList<Integer> matchingLines = index.linesWithFingerprint(fingerprint);
			
			// Start of a new match:
			if (matchingLines.size() > 0 && matchLength == -1) {
				int earliestMatch = matchingLines.get(0);
				prevMatchingLine = earliestMatch;
				iStart = lineNum;
				jStart = earliestMatch;
				matchLength = 1;
			} else if (matchLength > -1) {
				if (matchingLines.contains(prevMatchingLine+1)) { // continue match
					matchLength++;
					prevMatchingLine++;
				} else { // end match
					Clone c = new Clone(iStart, jStart, matchLength-1);
					clones.add(c);
					iStart = -1;
					jStart = -1;
					matchLength = -1;
				}
			}
			
			index.updateIndex(fingerprint, lineNum);
			lineNum++;
		}
		return clones;
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
		 *  Else hand over to MessageDigest:
		 */

		if (line.equals(""))
			return BigInteger.ZERO;

		BigInteger fingerprint = null;
		MessageDigest m = MessageDigest.getInstance(algorithm);
		m.update(line.getBytes(), 0, line.length());
		fingerprint = new BigInteger(1, m.digest());
		return fingerprint;
	}

	/*
	 * Handles all the output:
	 */
	public void findClonesFromFiles(String[] filenames, String algorithm) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		for (int i = 0; i < filenames.length; i++) {
			if (filenames.length > 1)
				System.out.println(filenames[i]);
			
			ArrayList<Clone> clones = findClones(filenames[i], algorithm);
			
			for (Clone clone : clones)
				System.out.println(clone);
			
			if (filenames.length > 1 && i < filenames.length - 1)
				System.out.println("");
		}
	}

	public static void main(String[] args) {
		CloneDetector cd = new CloneDetector();
		if (args.length < 1) {
			System.out.println("Missing filename");
		} else {
			try {
				cd.findClonesFromFiles(args, "StringHashCode");
			} catch (FileNotFoundException e) {
				System.out.println("File not found!");
			} catch (IOException e) {
				System.out.println("An error occurred whilst reading the file.");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("No such algorithm available on this system!");
			}
		}
	}
}
