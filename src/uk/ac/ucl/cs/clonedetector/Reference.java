
package uk.ac.ucl.cs.clonedetector;


public class Reference implements Cloneable, Comparable<Reference> {
	private String filename;	// Filename being referenced
	private int line;			// Line number being referenced (from 1)
	private Reference next;		// Pointer to the next reference
	
	/**
	 * Creates a reference to a line in a file.
	 * @param filename The filename referenced.
	 * @param line The line in the file referenced.
	 */
	public Reference(String filename, int line) {
		this.filename = filename;
		this.line = line;
	}
	
	/**
	 * Creates a reference to a line in a file with a pointer to the next reference (used by
	 * <code>clone()</code>).
	 * @param filename The filename referenced.
	 * @param line The line referenced.
	 * @param next Pointer to the next reference.
	 */
	public Reference(String filename, int line, Reference next) {
		this.filename = filename;
		this.line = line;
		this.next = next;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public int getLine() {
		return line;
	}
	
	public Reference next() {
		return next;
	}
	
	/**
	 * Called to set the pointer to the next reference after the object has been created.
	 * @param next Pointer to the next reference.
	 */
	public void setNext(Reference next) {
		this.next = next;
	}
	
	public void incLine() {
		line++;
	}
	

	@Override
	public Reference clone() {
		return new Reference(filename, line, next);
	}
	
	@Override
	public String toString() {
		return "(" + filename + ")" + line;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Reference) {
			Reference r = (Reference)o;
			return (this.filename.equals(r.filename) && this.line == r.line);
		}
		return false;
	}
	
	@Override
	public int compareTo(Reference other) {
		return new Integer(line).compareTo(new Integer(other.line));
	}

	
}