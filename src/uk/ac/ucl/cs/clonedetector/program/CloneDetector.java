package uk.ac.ucl.cs.clonedetector.program;
import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.math.*;



public class CloneDetector {


	public void findClones(String filename) throws FileNotFoundException, IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line;
		ArrayList<BigInteger> fingerprints = new ArrayList<BigInteger>();
		
		while ((line = in.readLine()) != null) {
			String processedLine = stripWhitespace(line);
			BigInteger fingerprint = computeFingerprint(processedLine, "MD5");
			fingerprints.add(fingerprint);
		}
		
		// Build comparison matrix between hashes:
		for (int i=0; i<fingerprints.size(); i++) {


			for (int j=0; j<i; j++) { // check line i against all lines before it
				BigInteger fi = fingerprints.get(i);
				BigInteger fj = fingerprints.get(j);
				int jStart = j+1;
				int jLength = 0;
				int iStart = i+1;
				while (fi.equals(fj) && !fi.equals(BigInteger.ZERO)) {
					// Start of a clone:
					i++;
					j++;
					fi = fingerprints.get(i);
					fj = fingerprints.get(j);
					jLength++;
				}
				if (jLength > 0) {
					int jEnd = jStart + jLength-1;
					int iEnd = iStart + jLength-1;
					System.out.println(jStart + ":" + jEnd + "-" + iStart + ":" + iEnd);
				}
					
			}
			
		}

	}
	
	public static BigInteger computeFingerprint(String line, String algorithm) {
		if (line.equals("")) return BigInteger.ZERO;
		
		BigInteger fingerprint = null;

		try {
			MessageDigest m = MessageDigest.getInstance(algorithm);
			m.update(line.getBytes(), 0, line.length());
			fingerprint = new BigInteger(1,m.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return fingerprint;
	}
	
	public static String stripWhitespace(String line) {
		String newLine;
		newLine = line.replaceAll(" ", "");
		newLine = newLine.replaceAll("\t", "");
		newLine = newLine.replaceAll("\n", "");
		newLine = newLine.replaceAll("\r", "");
		return newLine;
	}
	
	public static void main(String[] args) {
		CloneDetector cd = new CloneDetector();
		if (args.length < 1) {
			System.out.println("Missing filename");
		} else {
			try {
				cd.findClones(args[0]);
			} catch (FileNotFoundException e) {
				System.out.println("File not found!");
			} catch (IOException e) {
				System.out.println("An error occurred whilst reading the file.");
			}
		}
	}
}
