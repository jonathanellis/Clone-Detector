package uk.ac.ucl.cs.clonedetector;

import java.util.ArrayList;

public class Options {
	private ArrayList<String> filenames = new ArrayList<String>();
	private int cloneMinLength = 2;	// Default value
	private String algorithm = "StringHashCode";	// Default value
	
	public Options(String[] args) {
		// Parse arguments:
		int mode = 0; // holds the mode we are in (0 = filenames, 1 = clone length, 2 = algorithm)
		for (int i=0; i<args.length; i++) {
			if (mode == 0) {
				if (!args[i].equals("-l") && !args[i].equals("-a")) {
					filenames.add(args[i]);
				} else if (args[i].equals("-l")) {
					mode = 1;
				} else if (args[i].equals("-a")) {
					mode = 2;
				}
			} else if (mode == 1) {
				cloneMinLength = Integer.parseInt(args[i]);
				mode = 0;
			} else if (mode == 2) {
				algorithm = args[i];
				mode = 0;
			}
		}
		/*System.out.println("Num filenames: " + filenames.size());
		System.out.println("Clone min length " + cloneMinLength);
		System.out.println("Algorithm: " + algorithm);*/
	}
	
	public ArrayList<String> getFilenames() {
		return filenames;
	}
	
	public int getCloneMinLength() {
		return cloneMinLength;
	}
	
	public String getAlgorithm() {
		return algorithm;
	}
	
}
