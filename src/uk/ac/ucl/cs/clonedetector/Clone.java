package uk.ac.ucl.cs.clonedetector;

public class Clone {
	private int iStart;
	private int jStart;
	private int length;
	
	public Clone(int iStart, int jStart, int length) {
		this.iStart = iStart;
		this.jStart = jStart;
		this.length = length;
	}
	
	public String toString() {
		return String.format("%d-%d:%d-%d", iStart, iStart+length, jStart, jStart+length);
	}
}