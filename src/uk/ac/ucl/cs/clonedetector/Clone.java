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
			Reference tempStart = iStart.clone();
			Reference tempEnd = iEnd.clone();
			iStart = jStart;
			iEnd = jEnd;
			jStart = tempStart;
			jEnd = tempEnd;
		}
		
		this.iStart = iStart;
		this.iEnd = iEnd;
		this.jStart = jStart;
		this.jEnd = jEnd;
	}
	

	
	public String toString() {
		String iFilename = iStart.getFilename();
		String jFilename = jStart.getFilename();
		if (iFilename.equals(jFilename)) {
			return String.format("%d-%d:%d-%d", this.iStart.getLine(), this.iEnd.getLine(), this.jStart.getLine(), this.jEnd.getLine());			
		}
		return String.format("(%s)%d-%d:(%s)%d-%d", this.iStart.getFilename(), this.iStart.getLine(), this.iEnd.getLine(), this.jStart.getFilename(), this.jStart.getLine(), this.jEnd.getLine());
	}
	
	public Reference getIStart() {
		return iStart;
	}
	
	public int getLength() {
		return iEnd.getLine() - iStart.getLine();
	}
	
	public int compareTo(Clone other) {
		return iStart.compareTo(other.iStart);
	}
}