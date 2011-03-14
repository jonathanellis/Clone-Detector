import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.math.*;


public class CloneDetector {
	public enum FingerprintMethod {MD5_HASH};


	public void findClones(String filename) throws FileNotFoundException, IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line;
		ArrayList<BigInteger> fingerprints = new ArrayList<BigInteger>();
		
		while ((line = in.readLine()) != null) {
			String processedLine;
			processedLine = stripWhitespace(line);
			
			BigInteger fingerprint = computeFingerprint(processedLine, FingerprintMethod.MD5_HASH);

			fingerprints.add(fingerprint);
		}
		
		// Build comparison matrix between hashes:
		for (int i=0; i<fingerprints.size(); i++) {
			for (int j=0; j<i; j++) {
				BigInteger fi = fingerprints.get(i);
				BigInteger fj = fingerprints.get(j);
				if (fi.equals(fj) && !fi.equals(BigInteger.ZERO)) System.out.println("clone found! " + (i+1) + ":" + (j+1));
			}
			
		}

	}
	
	public static BigInteger computeFingerprint(String line, FingerprintMethod method) {
		if (line.equals("")) return BigInteger.ZERO;
		
		BigInteger fingerprint = null;
		if (method == FingerprintMethod.MD5_HASH) {
			try {
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.update(line.getBytes(), 0, line.length());
				fingerprint = new BigInteger(1,m.digest());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
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
