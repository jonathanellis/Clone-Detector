package uk.ac.ucl.cs.clonedetector;

import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.math.*;

public class CloneDetector {

	public void findClones(String filename, String algorithm) throws FileNotFoundException, IOException, NoSuchAlgorithmException {

		List<BigInteger> fingerprints = new ArrayList<BigInteger>();
		BufferedReader in = new BufferedReader(new FileReader(filename));

		String line;
		while ((line = in.readLine()) != null) {
			String processedLine = line.replaceAll("\\s*", ""); // \s matches all whitespace characters
			BigInteger fingerprint = computeFingerprint(processedLine, algorithm);
			fingerprints.add(fingerprint);
		}

		// Build comparison matrix between hashes:
		for (int i = 0; i < fingerprints.size(); i++) {

			for (int j = i+1; j < fingerprints.size(); j++) { // check line i against all lines before it
				BigInteger fi = fingerprints.get(i);
				BigInteger fj = fingerprints.get(j);
				// store these values in case this is the start of a clone
				int jStart = j + 1;
				int jLength = 0;
				int iStart = i + 1;
				while (fi.equals(fj) && !fi.equals(BigInteger.ZERO)) { // while the lines are clones
					// Start of a clone:
					i++;
					j++;
					fi = fingerprints.get(i);
					fj = fingerprints.get(j);
					jLength++;
				}
				// clone finishes
				if (jLength > 0) {
					int jEnd = jStart + jLength - 1;
					int iEnd = iStart + jLength - 1;
					System.out.println(iStart + ":" + iEnd + "-" + jStart + ":" + jEnd);
				}

			}

		}

	}

	public static BigInteger computeFingerprint(String line, String algorithm) throws NoSuchAlgorithmException {

		if (algorithm.equals("StringHashCode")) return BigInteger.valueOf(line.hashCode());

		// Else hand over to MessageDigest:

		if (line.equals("")) return BigInteger.ZERO;
		
		BigInteger fingerprint = null;
		MessageDigest m = MessageDigest.getInstance(algorithm);
		m.update(line.getBytes(), 0, line.length());
		fingerprint = new BigInteger(1, m.digest());
		return fingerprint;
	}

	public static void main(String[] args) {
		CloneDetector cd = new CloneDetector();
		if (args.length < 1) {
			System.out.println("Missing filename");
		} else {
			try {
				cd.findClones(args[0], "SHA-1");
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
