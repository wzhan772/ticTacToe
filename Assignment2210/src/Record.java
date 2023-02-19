/**
 * @author William Zhang - 251215208
 * the purpose of this class is to store records in the hash table
 * to be used during the game
 */

public class Record {
	//initialize variables
	String myKey;
	int myScore;
	int myLevel;
	//store the values
	public Record(String key, int score, int level) {
		this.myKey = key;
		this.myScore = score;
		this.myLevel = level;
	}
	//getters
	public String getKey() {
		return myKey;
	}

	public int getScore() {
		return myScore;
	}

	public int getLevel() {
		return myLevel;
	}

}