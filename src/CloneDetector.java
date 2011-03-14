import java.io.*;
import java.security.*;
import java.math.*;


public class CloneDetector {

	public CloneDetector(String filename) throws FileNotFoundException, IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line;

		while ((line = in.readLine()) != null) {
			try {
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.update(line.getBytes(), 0, line.length());
				System.out.println("hash: " + new BigInteger(1,m.digest()).toString(16));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
		}

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
