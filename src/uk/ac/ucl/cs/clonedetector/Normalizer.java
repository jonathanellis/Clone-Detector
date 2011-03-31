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
	 * The type of file in question
	 */
	private String lang;

	/**
	 * The keywords for the language in question </br> <Strong>Note:</strong> A
	 * .txt file for example has no keywords, while something like a .java file
	 * will have keywords like <code>for</code>, <code>while<code/>, etc.
	 */
	private String keywords = "";

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
			StringBuilder stringBuilder = new StringBuilder();

			String line;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line + "|");
			}

			keywords = stringBuilder.toString();
			keywords = keywords.substring(0, keywords.length() - 1);
			in.close();

		} catch (IOException e) {
			// Language does not exist, do nothing.
		}
	}

	/**
	 * Gets the type of file
	 * 
	 * @return language
	 */
	public String getLanguage() {
		return lang;
	}

	/**
	 * Gets the keywords
	 * 
	 * @return keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * Normalizes a given string. If a language has been specified and exists then the
	 * string will be normalized under that language's keywords (i.e. will ignore any
	 * keywords when normalizing identifiers).
	 * All strings are also normalized: This includes both single- and double-quoted
	 * strings and strings where the quotes have been escaped within the string.
	 * 
	 * @param string
	 * 		the content to normalize
	 * 
	 * @return the normalized string
	 */
	public String normalize(String string) {
		if (!keywords.equals("")) {
			String regexp_var = "((?!(" + keywords + ")\\b)\\b[A-Za-z][A-Za-z0-9]*)";
			String regexp_str = "([\"'])(?:\\\\?+.)*?\\1";
			String regexp_num = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
			string = string.replaceAll(regexp_var, "%VAR%");	// Normalize variables
			string = string.replaceAll(regexp_str, "%STR%");	// Normalize strings
			string = string.replaceAll(regexp_num, "%NUM%");	// Normalize numbers
		}
		string = string.toLowerCase();
		string = string.replaceAll("\\s*", ""); 		// strip whitespace
		return string;
	}

}