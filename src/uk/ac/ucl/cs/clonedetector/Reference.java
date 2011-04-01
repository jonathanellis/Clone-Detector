package uk.ac.ucl.cs.clonedetector;

public class Reference implements Cloneable {
	private String filename;
	private int line;
	
	public Reference(String filename, int line) {
		this.filename = filename;
		this.line = line;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public int getLine() {
		return line;
	}
	
	public void incLine() {
		line++;
	}
	
	public Reference successor() {
		return new Reference(filename, line+1);
	}
	
	public Reference clone() {
		return new Reference(filename, line);
	}
	
	
	public boolean equals(Object o) {
		if (o instanceof Reference) {
			Reference r = (Reference)o;
			return (this.filename.equals(r.filename) && this.line == r.line);
		}
		return false;
	}
	
}