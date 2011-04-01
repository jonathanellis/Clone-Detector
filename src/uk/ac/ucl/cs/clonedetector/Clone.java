/**
 * Copyright (c) 2011 Team Apollo
 */
package uk.ac.ucl.cs.clonedetector;

/**
 * <code>Clone</code> stores information about clones (starting position, and
 * the length of the clone).
 */
public class Clone implements Comparable<Clone> {

	/**
	 * The start of the first block that collides
	 */
	private int iStart;

	/**
	 * The start of the second block that collides
	 */
	private int jStart;

	/**
	 * The length is the length of the collision
	 */
	private int length;

	/**
	 * Constructs a <code>Clone</code> using the passed parameters.
	 * 
	 * @param iStart
	 *            start of the first block that collides
	 * @param jStart
	 *            start of the second block that collides
	 * @param length
	 *            length is the length of the collision
	 */
	public Clone(int iStart, int jStart, int length) {
		this.iStart = iStart;
		this.jStart = jStart;
		this.length = length;
	}
	
	public int getiStart() { return iStart; }

	@Override
	public String toString() {
		return String.format("%d-%d:%d-%d", this.iStart, this.iStart
				+ this.length, this.jStart, this.jStart + length);
	}
	
	@Override
	public int compareTo(Clone other) {
		return new Integer(iStart).compareTo(new Integer(other.getiStart()));
	}
}