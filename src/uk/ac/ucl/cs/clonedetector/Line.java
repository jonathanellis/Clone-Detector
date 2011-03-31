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
	 * 
	 */
	public final String lineContents;
	
	/**
	 * 
	 */
	public final BigInteger fingerprint;
	
	/**
	 * 
	 */
	public final int lineNumber;

	/**
	 * Constructs a <code>Line</code> using the passed parameters.
	 * 
	 * @param lineContents
	 * @param lineNumber
	 * @param fingerprint
	 */
	public Line(String lineContents, int lineNumber, BigInteger fingerprint) {
		this.lineContents = lineContents;
		this.lineNumber = lineNumber;
		this.fingerprint = fingerprint;
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
