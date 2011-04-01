package uk.ac.ucl.cs.clonedetector;

import java.util.ArrayList;
import java.util.Collections;

public class CloneManager {
	ArrayList<Clone> clones = new ArrayList<Clone>();
	
	public void add(Reference iStart, Reference iEnd, Reference jStart, Reference jEnd) {
		clones.add(new Clone(iStart, iEnd, jStart, jEnd));
	}
	
	public String toString() {
		Collections.sort(clones);
		String result = "";
		for (Clone c : clones) {
			result += c.toString() + "\n";
		}
		return result.trim();
	}
	
}