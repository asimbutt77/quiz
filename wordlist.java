import java.io.*;
import java.util.*;

public class wordlist implements Comparable<wordlist>{

	String word;
	int length;


/* compareTo method to sort array list in a descending order of length */

	public int compareTo(wordlist other){
		if (length == other.length)
			return 0;
		else if (length < other.length)
			return 1;
		else
			return -1;
	}

/* Class wordlist constructor */

public wordlist (String word) {
	this.word = word;
	this.length = word.length();
}

/* Add wordlist object in an array list */

public static  ArrayList<wordlist> addWord (String word, ArrayList<wordlist> list){
	wordlist entry = new wordlist(word);
	list.add(entry);
	return list;
}

/* Method to search for a given prefix string in the array list starting
 from the tail of descending ordered array list*/

public static boolean searchPrefix (String prefix, ArrayList<wordlist> list){
	Boolean found;
	found = false;
	int prefixLength = prefix.length();

	for (int i = list.size()-1;i >= 0;i--){
		found = list.get(i).word.equals(prefix);
		if (found == true)
			break;
		if (list.get(i).word.length() > prefixLength)
			break;
	}
	return found;
}

/* Method to determine if a word is a compound word */

public static boolean isCompoundWord (String word, ArrayList<wordlist> list){

boolean found = false;
Boolean prefixFoundIteration = false;
int prefixFoundIndex = 0;

		for (int j = 1; j < word.length()+1; j++){
			String prefix = word.substring(0,j);
//			System.out.println("Prefix to be tested if present in the word list: " + prefix);
			found = searchPrefix(prefix,list);

			if (found == true && (j != (word.length()-1))){
				prefixFoundIndex = j;
				prefixFoundIteration = prefixFoundIteration || found;
//				System.out.println("Valid prefix found in the word : "+ word.substring(0,j));
			}
		}
	
		if (prefixFoundIteration == true && prefixFoundIndex < word.length()){
					found = true;
//					System.out.println("Suffix to be tested recursively: "+ word.substring(prefixFoundIndex,(word.length())));
					found=isCompoundWord((word.substring(prefixFoundIndex,(word.length()))),list);
		}
		return found;
}

/* Main method to read the words from the text file named "wordlist.txt" and run checks for longest compound word.
   The program stops executing once longest compound word is found in an ordered word list with longest word at 
   the head of the array list */

	public static void main(String[] args){

		ArrayList<wordlist> list = new ArrayList<wordlist>();


		try {
			Scanner scan = new Scanner(new File("wordlist.txt"));

			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				addWord(line,list);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		/* Array list to be sorted in a descending order of word lengths */

		Collections.sort(list);


		int longestWordLength = 0;
		String testWord;
		int testWordLength;
		boolean longestWordNotFound = true;

		while(list.size() > 0) {

			testWord = list.get(0).word;
			testWordLength = testWord.length();
			list.remove(0);

//			System.out.println("This word will be tested if it is a compound word: "+ testWord);


			if (testWordLength < longestWordLength)
				break;

			if (!longestWordNotFound){
				if (isCompoundWord(testWord,list) == true && (testWordLength == longestWordLength))
					System.out.println("Another compound word in the file with the same length as the found compound word is - \""+ testWord + "\" - with length of "+ testWordLength + " characters.");
			}

			if (isCompoundWord(testWord,list) == true && longestWordNotFound){
				longestWordLength = testWordLength;
				longestWordNotFound = false;
				System.out.println("Longest compound word in the file is - \""+ testWord + "\" - with length of "+ longestWordLength + " characters.");
			}

		}
		

	}


}