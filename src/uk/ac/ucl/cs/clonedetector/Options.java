/**
 * Copyright (c) 2011 Team Apollo
 */
package uk.ac.ucl.cs.clonedetector;

import java.util.ArrayList;

/**
 * <code>Options</code> handles parameters that maybe passed to the program when
 * it is executed from the command line.
 * 
 * @author apollo
 * @version 1.0
 */
public class Options {

	/**
	 * Holds filenames in which the program is going to search for clone.
	 */
	private ArrayList<String> filenames = new ArrayList<String>();

	/**
	 * Default minimum length of clone
	 */
	private int cloneMinLength = 2;

	/**
	 * Default algorithm
	 */
	private String algorithm = "StringHashCode";

	/**
	 * Parses the command line objects and initializes the Options class to be
	 * configured with the options supplied at the command line.
	 * 
	 * @param args
	 *            The command line arguments to be parsed.
	 */
	public Options(String[] args) {
		// Parse arguments:
		int mode = 0; // holds the mode we are in (0 = filenames, 1 = clone
						// length, 2 = algorithm)
		for (int i = 0; i < args.length; i++) {
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
	}

	/**
	 * Gets the filenames which need to be processed.
	 * 
	 * @return filenames
	 */
	public ArrayList<String> getFilenames() {
		return filenames;
	}

	/**
	 * Gets the minimum length of clones
	 * 
	 * @return the smallest size a clone can be
	 */
	public int getCloneMinLength() {
		return cloneMinLength;
	}

	/**
	 * Gets the algorithm to use to find clones
	 * 
	 * @return algorithm in use
	 */
	public String getAlgorithm() {
		return algorithm;
	}

}
