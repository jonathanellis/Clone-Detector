package uk.ac.ucl.cs.clonedetector;

import java.util.ArrayList;
import java.util.Collections;

public class CloneManager {
	private ArrayList<Clone> clones = new ArrayList<Clone>();

	
	public void add(Clone c) {
		clones.add(c);
	}
	
	public ArrayList<Clone> coalesce() {
		ArrayList<Clone> goodClones = new ArrayList<Clone>();

		// Remove clones that are subsets of other clones:
		for (Clone c: clones) {
			boolean shouldUpgrade = true;
			ArrayList<Clone> toDelete = new ArrayList<Clone>();
			for (Clone g : goodClones) {
				if (c.encompasses(g)) {
					toDelete.add(g);
				} else if (g.encompasses(c)) {
					shouldUpgrade = false;
					
				}
			}
			if (shouldUpgrade) goodClones.add(c);
			for (Clone d : toDelete) {
				goodClones.remove(d);
			}
		}
				
		return goodClones;
	}
	
	@Override
	public String toString() {
		Collections.sort(clones);
		String result = "";
		for (Clone c : coalesce()) {
			if (c.getLength() >= CloneDetector.options.cloneMinLength) result += c.toString() + "\n";
		}
		return result.trim();
	}
	
}