import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.math.*;


public class CloneDetector {
	public enum FingerprintMethod {MD5_HASH};

	public CloneDetector(String filename) throws FileNotFoundException, IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line;
		ArrayList<BigInteger> fingerprints = new ArrayList<BigInteger>();
		
		while ((line = in.readLine()) != null) {
			String processedLine;
			processedLine = stripWhitespace(line);
			
			BigInteger fingerprint;
			if (processedLine.equals("")) {
				fingerprint = BigInteger.ZERO;
			} else {
				fingerprint = computeFingerprint(processedLine, FingerprintMethod.MD5_HASH);
			}
			
			//System.out.println(processedLine + " " + fingerprint);
			
			fingerprints.add(fingerprint);
		}
		
		// Build comparison matrix between hashes:
		for (int i=0; i<fingerprints.size(); i++) {
			for (int j=0; j<i; j++) {
				BigInteger fi = fingerprints.get(i);
				BigInteger fj = fingerprints.get(j);
				//System.out.println(fi);
				if (fi.equals(fj) && !fi.equals(BigInteger.ZERO)) System.out.println("clone found! " + (i+1) + ":" + (j+1));
			}
			
		}

	}
	
	public BigInteger computeFingerprint(String line, FingerprintMethod method) {
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
	
	public String stripWhitespace(String line) {
		String newLine;
		newLine = line.replaceAll(" ", "");
		newLine = newLine.replaceAll("\t", "");
		newLine = newLine.replaceAll("\n", "");
		newLine = newLine.replaceAll("\r", "");
		return newLine;
	}
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Missing filename");
		} else {
			try {
				new CloneDetector(args[0]);
			} catch (FileNotFoundException e) {
				System.out.println("File not found!");
			} catch (IOException e) {
				System.out.println("An error occurred whilst reading the file.");
			}
		}
	}
}
