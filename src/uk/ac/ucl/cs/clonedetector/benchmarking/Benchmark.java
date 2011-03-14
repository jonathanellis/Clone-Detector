package uk.ac.ucl.cs.clonedetector.benchmarking;

import java.io.*;
import uk.ac.ucl.cs.clonedetector.program.CloneDetector;

public class Benchmark {

	public static void main(String[] args) {
		
		try {
			long t1 = System.currentTimeMillis();
			BufferedReader in = new BufferedReader(new FileReader("text/war&peace.txt"));
			String line;
			
			while ((line = in.readLine()) != null) {
				CloneDetector.computeFingerprint(line, args[0]);
			}
			long t2 = System.currentTimeMillis();
			System.out.println(t2-t1);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		} catch (IOException e) {
			System.out.println("An error occurred whilst reading the file.");
		}
		
	}
	

}
