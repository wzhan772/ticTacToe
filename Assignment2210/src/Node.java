/**
 * @author William Zhang - 251215208
 * the purpose of this class is to be a linked list for the Dictionary class
 */

public class Node {
	//initialize variables
    private Node next;
    private Record value;
    
    public Node() {
    	next = null;
    	value = null;
    }
    
    public Node (Record val) {
    	next = null;
    	value = val;
    }
    //getters
    public Node getNext() {
    	return next;
    }
    
    public Record getValue() {
    	return value;
    }
    //setters
    public void setNext (Node nextVal) {
    	next = nextVal;
    }
    
    public void setValue (Record val) {
    	value = val;
    }
}