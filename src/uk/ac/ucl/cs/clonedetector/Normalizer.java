/**
 * Copyright (c) 2011 Team Apollo
 */
package uk.ac.ucl.cs.clonedetector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class description
 */
public class Normalizer {
	
	/**
	 * 
	 */
	String lang;
	
	/**
	 * 
	 */
	String keywords = "";

	/**
	 * Constructs a <code>Normalizer</code> using the passed parameter
	 * 
	 * @param lang
	 */
	public Normalizer(String lang) {
		this.lang = lang;
		BufferedReader in;
		
		try {
			in = new BufferedReader(new FileReader("lang/" + lang + ".keywords"));
			
			String line;
			while ((line = in.readLine()) != null) {
				keywords += line + "|";
			}
			
			keywords = keywords.substring(0, keywords.length() - 1);
			in.close();
			
		} catch (IOException e) {
			// Language does not exist, do nothing.
		}
	}

	/**
	 * Normalizes a given string. If a language has been specified and exists
	 * then the string will be normalized under that language's keywords (i.e.
	 * will ignore any keywords when normalizing identifiers).
	 * 
	 * @param string
	 * 
	 * @return the normalized string
	 */
	public String normalize(String string) {
		if (!keywords.equals("")) {
			String regexp = "((?!(" + keywords + ")\\b)\\b[A-Za-z][A-Za-z0-9]*)";

			// normalise variables
			string = string.replaceAll(regexp, "%VAR%"); 
		}
		
		// strip whitespace
		string = string.replaceAll("\\s*", "");
		return string;
	}

}