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
	private Reference iStart;


	/**
	 * The start of the second block that collides
	 */
	private Reference jStart;

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
	public Clone(Reference iStart, Reference jStart, int length) {
		if (iStart.getLine() > jStart.getLine()) {
			Reference temp = iStart;
			iStart = jStart;
			jStart = temp;
		}
		
		this.iStart = iStart;
		this.jStart = jStart;
		this.length = length;
	}

	@Override
	public int compareTo(Clone arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString() {
		return String.format("%s-%s", this.iStart,this.jStart);
	}
	
/*	public int getiStart() { return iStart; }

	@Override

	
	@Override
	public int compareTo(Clone other) {
		return new Integer(iStart).compareTo(new Integer(other.getiStart()));
	}*/
}