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
	 * Constructs a <code>Normalizer</code> using the passed parameter as the language
	 * under which to perform the normalization. 
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
	 * Gets the language that the normalizer is currently using.
	 * 
	 * @return language
	 */
	public String getLanguage() {
		return lang;
	}

	/**
	 * Gets a list of keywords for the current language.
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
			String regexp_var = "((?!(" + keywords + ")\\b)\\b[A-Za-z_][A-Za-z0-9_]*)";
			String regexp_str = "([\"'])(?:\\\\?+.)*?\\1";
			String regexp_num = "\\b[0-9]+(\\.[0-9]+)?(e[+-]?[0-9]+)?\\b";
		//	String regexp_com = "//.*|/\\*[\\s\\S]*?\\*/";
			string = string.replaceAll(regexp_var, "%VAR%");	// Normalize variables
			string = string.replaceAll(regexp_str, "%STR%");	// Normalize strings
			string = string.replaceAll(regexp_num, "%NUM%");	// Normalize numbers
		//	string = string.replaceAll(regexp_com, ""); 		// Strip comments
		}
		string = string.toLowerCase();
		string = string.replaceAll("\\s*", ""); 		// strip whitespace
		return string;
	}

}