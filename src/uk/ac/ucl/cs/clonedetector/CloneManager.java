package uk.ac.ucl.cs.clonedetector;

import java.util.ArrayList;

public class CloneManager {
	ArrayList<Clone> clones = new ArrayList<Clone>();
	
	public void addClone(int iStart, int jStart, int length) {
		clones.add(new Clone(iStart, jStart, length));
	}
	
	public String toString() {
		return "";
	}
	
}