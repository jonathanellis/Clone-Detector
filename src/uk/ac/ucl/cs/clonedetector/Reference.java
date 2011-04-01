package uk.ac.ucl.cs.clonedetector;

public class Reference implements Cloneable, Comparable<Reference> {
	private String filename;
	private int line;
	private Reference next;
	
	public Reference(String filename, int line) {
		this.filename = filename;
		this.line = line;
	}
	
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