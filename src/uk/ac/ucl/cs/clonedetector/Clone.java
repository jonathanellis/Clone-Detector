/**
 * Copyright (c) 2011 Team Apollo
 */
package uk.ac.ucl.cs.clonedetector;

/**
 * <code>Clone</code> stores information about clones (starting position, and
 * the length of the clone).
 */
public class Clone implements Comparable<Clone> {

	private Reference iStart;
	private Reference iEnd;
	private Reference jStart;
	private Reference jEnd;

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
	public Clone(Reference iStart, Reference iEnd, Reference jStart, Reference jEnd) {
		if (iStart.getLine() > jStart.getLine()) {
			Reference temp = iStart;
			iStart = jStart;
			jStart = temp;
		}
		
		this.iStart = iStart;
		this.iEnd = iEnd;
		this.jStart = jStart;
		this.jEnd = jEnd;
	}

	@Override
	public int compareTo(Clone arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString() {
		return String.format("(%s)%d-%d:(%s)%d-%d", this.iStart.getFilename(), this.iStart.getLine(), this.iEnd.getLine(), this.jStart.getFilename(), this.jStart.getLine(), this.jEnd.getLine());
	}
	
/*	public int getiStart() { return iStart; }

	@Override

	
	@Override
	public int compareTo(Clone other) {
		return new Integer(iStart).compareTo(new Integer(other.getiStart()));
	}*/
}