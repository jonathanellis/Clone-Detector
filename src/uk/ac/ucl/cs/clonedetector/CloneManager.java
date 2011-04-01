/**
 * Copyright (c) 2011 Team Apollo
 */
package uk.ac.ucl.cs.clonedetector;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <code>CloneManager</code> manages clones found in a file and coalesce the
 * clone if its appropriate.
 * 
 * @author apollo
 * @version 1.0
 */
public class CloneManager {

	/**
	 * Holds the <code>Clone</code> object
	 */
	private ArrayList<Clone> clones = new ArrayList<Clone>();

	/**
	 * Adds new clone to the list of existing clones.
	 * 
	 * @param clone
	 *            <code>Clone</code> to be added.
	 */
	public void add(Clone clone) {
		clones.add(clone);
	}

	/**
	 * Coalesces clones by created a list of "good" clones that do not encompass
	 * each other or overlap.
	 * 
	 * @return A list of "good" clones.
	 */
	public ArrayList<Clone> coalesce() {
		ArrayList<Clone> goodClones = new ArrayList<Clone>();

		// Remove clones that are subsets of other clones:
		for (Clone c : clones) {
			boolean shouldUpgrade = true;
			ArrayList<Clone> toDelete = new ArrayList<Clone>();
			for (Clone g : goodClones) {
				if (c.encompasses(g)) {
					toDelete.add(g);
				} else if (g.encompasses(c)) {
					shouldUpgrade = false;

				}
			}
			if (shouldUpgrade)
				goodClones.add(c);
			for (Clone d : toDelete) {
				goodClones.remove(d);
			}
		}

		// Remove clones that overlap themselves:
		ArrayList<Clone> toDelete = new ArrayList<Clone>();
		for (Clone g : goodClones) {
			if (g.overlapsItself()) {
				toDelete.add(g);
			}
		}

		for (Clone d : toDelete) {
			goodClones.remove(d);
		}

		return goodClones;
	}

	@Override
	public String toString() {
		Collections.sort(clones); // Sort the clones
		String result = "";
		for (Clone c : coalesce()) {
			if (c.getLength() >= CloneDetector.options.getCloneMinLength())
				result += c.toString() + "\n";
		}
		return result.trim();
	}

}