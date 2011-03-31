package uk.ac.ucl.cs.clonedetector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CloneDetector {
  
  /*
   * Variables for the findClones() method (and its related methods).
   * The variables are re-used each iteration for efficiency reasons.
   * ("collider" is what the collision is colliding with)
   */
  private String line;
  private Line currentLine;
  private BigInteger fingerprint;
  private int offset;
  private int colliderStart;
  private int currentCollisionStart;
  private int currentCollisionEnd;
  
  private int lineNumber;
  private LinkedList<Line> currentList;
  private boolean inCollisionBlock = false;
  private ArrayList<Clone> clones;
  
  
	/*
	 * from:
	 * http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java/453067#453067
	 */
	private int count(String filename) throws IOException {
    InputStream is = new BufferedInputStream(new FileInputStream(filename));
    try {
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n')
                    ++count;
            }
        }
        return count;
    } finally {
        is.close();
    }
}

	/**
	 * Find clones in the given filename using the specified algorithm.
	 * 
	 * @param filename
	 * @param algorithm
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 *             Thrown if the algorithm is not available on the system.
	 */
	public ArrayList<Clone> findClones(String filename, String algorithm) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
	  
	  /*
	   * The number of input lines which the algorithm expects: (this is a soft
	   * limit: the algorithm needs to know it only for the performance of its data structures)
	   */
	  int numberOfLines = count(filename);
	  
	  BufferedReader in = new BufferedReader(new FileReader(filename));
	  
		clones = new ArrayList<Clone>();

		/*
		 * The HashTable class is not used because we want to see the collisions
		 * happening:
		 */
		HashMap<BigInteger, LinkedList<Line>> hashCodeTable = new HashMap<BigInteger, LinkedList<Line>>(numberOfLines);

		/*
		 * there couldn't possibly be collisions between line numbers (as each
		 * line has a unique line number), so no linked lists are needed:
		 */
		HashMap<Integer, Line> lineNumberTable = new HashMap<Integer, Line>(numberOfLines);
		
		Normalizer normalizer = new Normalizer(getExtension(filename));
		
		lineNumber = 0;
		while ((line = in.readLine()) != null) {
			lineNumber++;

			/*
			 * \s matches all whitespace characters
			 */
			String processedLine = line.replaceAll("\\s*", "");
			processedLine = normalizer.normalize(processedLine);
			fingerprint = computeFingerprint(processedLine, algorithm);
			currentLine = new Line(lineNumber, processedLine, fingerprint);

			/*
			 * storing the read line and its attributes:
			 */
			lineNumberTable.put(lineNumber, currentLine);

			if (processedLine.equals("")) {
				if (inCollisionBlock) {
				  exitFromCollisionBlock(false);
				}
				continue;
			}

			/*
			 * a collision has happened
			 */
			if (hashCodeTable.containsKey(fingerprint)) {

				/*
				 * checking to see if this really is a block of collisions,
				 * rather than sequence of random collisions with random lines:
				 */

				if (inCollisionBlock) {
					offset = lineNumber - currentCollisionStart;
					
					if ((!lineNumberTable.get(colliderStart + offset).getFingerprint().equals(fingerprint))) {
					  exitFromCollisionBlock(false);
					}
				}

				/*
				 * entering a new collision block:
				 */
				if (!inCollisionBlock) {
					inCollisionBlock = true;
					currentCollisionStart = lineNumber;
					colliderStart = hashCodeTable.get(fingerprint).getFirst().getLineNumber();
				}

				currentList = hashCodeTable.remove(fingerprint);
				currentList.add(currentLine);
				hashCodeTable.put(fingerprint, currentList);
			}
			/*
			 * no collision has happened
			 */
			else {
				currentList = new LinkedList<Line>();
				currentList.add(currentLine);
				hashCodeTable.put(fingerprint, currentList);

				/*
				 * exiting the current collision block:
				 */
				if (inCollisionBlock) {
				  exitFromCollisionBlock(false);
				}
			}
		}
		if (inCollisionBlock) {
		  exitFromCollisionBlock(true);
		}

		in.close();
		return clones;
	}

	private void exitFromCollisionBlock(boolean endOfLoop)
	{
	  inCollisionBlock = false;
    
	  if ( endOfLoop ) { currentCollisionEnd = lineNumber; }
	  else { currentCollisionEnd = lineNumber - 1; }
	  
    if (colliderStart == (currentCollisionStart - 1)) {
      currentCollisionStart--;
    }
    clones.add(new Clone(colliderStart, currentCollisionStart, currentCollisionEnd - currentCollisionStart));
	}
	
	/**
	 * Retrieves the file extension from a given relative or absolute filename.
	 * Filenames with no extension return "".
	 * 
	 * @param filename
	 *            Filename to get the extension for
	 * @return Extension for the filename
	 */
	public static String getExtension(String filename) {
		String chunks[] = filename.split("\\.");
		if (chunks.length > 1)
			return chunks[chunks.length - 1];
		return "";
	}

	public static BigInteger computeFingerprint(String line, String algorithm) throws NoSuchAlgorithmException {

		if (algorithm.equals("StringHashCode"))
			return BigInteger.valueOf(line.hashCode());

		/*
		 *  Else hand over to MessageDigest:
		 */

		if (line.equals(""))
			return BigInteger.ZERO;

		BigInteger fingerprint = null;
		MessageDigest m = MessageDigest.getInstance(algorithm);
		m.update(line.getBytes(), 0, line.length());
		fingerprint = new BigInteger(1, m.digest());
		return fingerprint;
	}

	/*
	 * Handles all the output:
	 */
	public void findClonesFromFiles(String[] filenames, String algorithm) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		for (int i = 0; i < filenames.length; i++) {
			if (filenames.length > 1)
				System.out.println(filenames[i]);
			
			ArrayList<Clone> clones = findClones(filenames[i], algorithm);
			
			for (Clone clone : clones)
				System.out.println(clone);
			
			if (filenames.length > 1 && i < filenames.length - 1)
				System.out.println("");
		}
	}

	public static void main(String[] args) {
		CloneDetector cd = new CloneDetector();
		if (args.length < 1) {
			System.out.println("Missing filename");
		} else {
			try {
				cd.findClonesFromFiles(args, "SHA-1");
			} catch (FileNotFoundException e) {
				System.out.println("File not found!");
			} catch (IOException e) {
				System.out.println("An error occurred whilst reading the file.");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("No such algorithm available on this system!");
			}
		}
	}
}
