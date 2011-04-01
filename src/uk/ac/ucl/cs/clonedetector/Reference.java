/**
 * Copyright (c) 2011 Team Apollo
 */
package uk.ac.ucl.cs.clonedetector;

/**
 * <code>Reference</code> holds a reference to a line in a file with a pointer
 * to the next. It is used by <code>clone()</code>
 * 
 * @author apollo
 * @version 1.0
 */
public class Reference implements Cloneable, Comparable<Reference> {

	/**
	 * Filename being referenced
	 */
	private String filename;

	/**
	 * Line number being referenced (from 1)
	 */
	private int line;

	/**
	 * Pointer to the next reference
	 */
	private Reference next;

	/**
	 * Creates a reference to a line in a file.
	 * 
	 * @param filename
	 *            The filename referenced.
	 * @param line
	 *            The line in the file referenced.
	 */
	public Reference(String filename, int line) {
		this(filename, line, null);
	}

	/**
	 * Creates a reference to a line in a file with a pointer to the next
	 * reference (used by <code>clone()</code>).
	 * 
	 * @param filename
	 *            The filename referenced.
	 * @param line
	 *            The line referenced.
	 * @param next
	 *            Pointer to the next reference.
	 */
	public Reference(String filename, int line, Reference next) {
		this.filename = filename;
		this.line = line;
		this.next = next;
	}

	/**
	 * Gets the filename
	 * 
	 * @return filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Gets the line
	 * 
	 * @return line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Gets the next <code>Reference</code> object
	 * 
	 * @return next
	 */
	public Reference next() {
		return next;
	}

	/**
	 * Called to set the pointer to the next reference after the object has been
	 * created.
	 * 
	 * @param next
	 *            Pointer to the next reference.
	 */
	public void setNext(Reference next) {
		this.next = next;
	}

	/**
	 * Increments the line counter by 1
	 */
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
			Reference r = (Reference) o;
			return (this.filename.equals(r.filename) && this.line == r.line);
		}
		return false;
	}

	@Override
	public int compareTo(Reference other) {
		return new Integer(line).compareTo(new Integer(other.line));
	}

}