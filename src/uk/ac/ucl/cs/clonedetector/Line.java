/**
 * Copyright (c) 2011 Team Apollo
 */
package uk.ac.ucl.cs.clonedetector;

import java.math.BigInteger;

/*
 * A class which stores information about a line of text.
 */
public class Line implements Comparable<Line> {

	/**
	 * The position of line in question in a file.
	 */
	private final int lineNumber;

	/**
	 * The text found at position lineNumber
	 */
	private final String lineContent;

	/**
	 * The fingerprint of the line in question
	 */
	private final BigInteger fingerprint;

	/**
	 * Constructs a <code>Line</code> using the passed parameters.
	 * 
	 * @param lineNumber
	 *            position of line in question in a file
	 * @param lineContent
	 *            text found at position lineNumber
	 * @param fingerprint
	 *            fingerprint of the line
	 */
	public Line(int lineNumber, String lineContent, BigInteger fingerprint) {
		this.lineNumber = lineNumber;
		this.lineContent = lineContent;
		this.fingerprint = fingerprint;
	}

	/**
	 * Gets the line number of the Line in question
	 * 
	 * @return lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * Gets the content of the line
	 * 
	 * @return lineContent
	 */
	public String getLineContent() {
		return lineContent;
	}

	/**
	 * Gets the fingerprint of the line
	 * 
	 * @return fingerprint
	 */
	public BigInteger getFingerprint() {
		return fingerprint;
	}

	@Override
	public int hashCode() {
		return fingerprint.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other.getClass().getName().equals(this.getClass().getName())) {
			if (((Line) other).fingerprint.equals(this.fingerprint)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int compareTo(Line other) {
		return (this.fingerprint.subtract(other.fingerprint)).intValue();
	}

}
