/**
 * @author William Zhang - 251215208
 * The purpose of this class is to create a hash table to be used during the game
 */

public class Dictionary implements DictionaryADT {
	private int size; 
	private Node[] hash_array; 
	
	/**
	 * constructor 
	 * @param size
	 */
	public Dictionary(int size) {
		this.size = size;
		hash_array = new Node[size];
	}
	
	/**
	 * 
	 * puts in a new record into the hash table 
	 * Throws duplicatedkeyexception if the key to put in already exists
	 * 
	 * @param rec
	 * @return integer depending on outcome of code (1 for collisions, 0 for no collisions, throw an error)
	 * 
	 */
	public int put(Record rec) throws DuplicatedKeyException {
		int pos = hashVal(rec.getKey());
		DuplicatedKeyException dup_key = new DuplicatedKeyException("You have a duplicated key");
		Node proceedingNode = hash_array[pos]; 

		if (proceedingNode != null) { // make sure non empty
			if (proceedingNode.getNext() == null && proceedingNode.getValue().equals(rec.getKey())) {
				throw dup_key;
			} else if (!proceedingNode.getValue().getKey().equals(rec.getKey())) {
				while (proceedingNode.getNext() != null) {
					if (proceedingNode.getValue().getKey().equals(rec.getKey())) { // throws error when the key is duplicated
						throw dup_key;
					}
					proceedingNode = proceedingNode.getNext();
				}
				proceedingNode.setNext(new Node(rec)); // puts the new value at the end of the linked list
				return 1;
			} else {
				throw dup_key;
			}

		} else {
			hash_array[pos] = new Node(rec);
			hash_array[pos].setNext(null);
			return 0;
		}
	}

	/**
	 * 
	 * This method removes the key specified in the parameter 
	 * This method also throws inexistentkeyexception in the case that the key does not exist
	 * 
	 * @param key
	 * 
	 * 
	 */
	public void remove(String key) throws InexistentKeyException {
		InexistentKeyException no_key = new InexistentKeyException("This key does not exist");
		int takeOutPos = hashVal(key);

		if (hash_array[takeOutPos] == null) { // if position is empty, then key does not exist
			throw no_key;
		} else {
			boolean foundCheck = false; // make a boolean check to see if the value that has to be removed is found or not
			Node proceedingNode = hash_array[takeOutPos];
			Node nodeValuedNext = proceedingNode.getNext();

			if (proceedingNode.getValue().getKey().equals(key)) {  // compares values to the key
				hash_array[takeOutPos] = proceedingNode.getNext();
			} else {
				while (proceedingNode.getNext() != null) {
					if (nodeValuedNext.getValue().getKey().equals(key)) {
						proceedingNode.setNext(nodeValuedNext.getNext()); // removes Node that matches parameter 
						foundCheck = true;
						return;
					}
					nodeValuedNext = nodeValuedNext.getNext();
				}
			}
		}
	}

	/**
	 * 
	 * This method just gets the key value stored at the position
	 * @param key
	 * @return value 
	 */
	public Record get(String key) {
		Node proceedingNode = hash_array[hashVal(key)]; 
		Record value = null;
		while (proceedingNode != null) { // iterates through until key is found (separate chaining)
			if ((proceedingNode.getValue().getKey().equals(key))) {
				value = proceedingNode.getValue();
				break;
			}
			proceedingNode = proceedingNode.getNext();
		}
		return value;
	}
	
	/**
	 * This method hashes a value, transforming the string into an integer code
	 * 
	 * @param recKey
	 * @return keyValue & size (hashed value)
	 */
	private int hashVal(String recKey) {
		//get the current key value
		String key = recKey; 
		int keyValue = 0; 
		for (int i = 0; i < key.length(); i++) {
			keyValue += ((int) recKey.charAt(i) * Math.pow(33, i)) % size;
		}
		return keyValue % size;
	}
	
	/**
	 * This method returns the number of records in the hash table 
	 */
	public int numRecords() {
		int tally = 0;
		for (int i = 0; i < size; i++) { // first loop iterates through the array
			Node proceedingNode = hash_array[i];
			while (proceedingNode != null) { // second loop iterates inside separate chaining linked lists
				tally++;
				proceedingNode = proceedingNode.getNext();
			}
		}
		return tally;
	}
}