/**
 * @author William Zhang - 251215208
 * the purpose of this class is the evaluate the current state of the game and to 
 * check and see if anybody has won the game
 */

	
public class Evaluate {
	private char[][] board;
	private int size;
	private int tile;
	private int maxLevels;
	
	/**
	 * 
	 * @param size sets the size
	 * @param tilesToWin sets the tiles 
	 * @param maxLevels sets the maximum levels
	 */
	public Evaluate(int size, int tile, int maxLevels) {
		// initializes values and board
		this.size = size;
		this.tile = tile;
		this.maxLevels = maxLevels;
		this.board = new char[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = 'e';
			}
		}
	}
	
	/**
	 * This method creates a dictionary ready for use 
	 * 
	 * @return the dictionary
	 */
	public Dictionary createDictionary() { 
		Dictionary myDictionary = new Dictionary(9887);
		return myDictionary;
	}
	/**
	 * This method repeatedly prints the values of the board to create
	 * a string representation of the game state
	 * 
	 * @param dict 
	 * @return the state of the game 
	 */
	public Record repeatedState(Dictionary dict) {
		String game_state = "";
		for (int i = 0; i < size; i++) { // iterates through board
			for (int j = 0; j < size; j++) {
				game_state += board[j][i]; // adds board values onto a string as the game state
			}
		}
		return dict.get(game_state);
	}
	
	/**
	 * This method inserts the state as a new record
	 * 
	 * @param dict gets stored into the record
	 * @param score gets stored into the record
	 * @param level gets stored into the record
	 */
	public void insertState(Dictionary dict, int score, int level) {
		String game_state = "";
		for (int i = 0; i < size; i++) { // iterates through board 
			for (int j = 0; j < size; j++) {
				game_state += board[j][i]; // adds board values onto a string as the game state
			}
		}
		Record newRec = new Record(game_state, score, level); // create new instance of a record
		dict.put(newRec);
	}

	public void storePlay(int row, int col, char symbol) { // inserts a value from a play
		board[row][col] = symbol;
	}

	// methods below returns boolean of if the specified character matches e, c, or h
	public boolean squareIsEmpty(int row, int col) {
		return (board[row][col] == 'e');
	}

	public boolean tileOfComputer(int row, int col) {
		return (board[row][col] == 'c');
	}

	public boolean tileOfHuman(int row, int col) {
		return (board[row][col] == 'h');
	}
	
	/**
	 * This method determines if the game is a win or not
	 * @param symbol
	 * @return the result of the game if it is a win
	 */
	public boolean wins(char symbol) {
		boolean gameEndCheck = false;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[j][i] == symbol) {
					while (gameEndCheck == false) {
						int vert_count = 0;
						for (int k = 0; k < size; k++) { // checks the vertical
							if (board[k][i] == symbol) {
								vert_count++;
								if (vert_count >= tile) {
									break;
								}
							} else {
								vert_count = 0;
							}
						}
						if (vert_count == tile) {
							return true;
						} else { // Goes into horizontal check
							int horizontal_count = 0;
							for (int l = 0; l < size; l++) {
								if (board[j][l] == symbol) {
									horizontal_count++;
									if (horizontal_count == tile) {
										break;
									}
								} else {
									horizontal_count = 0;
								}
							}
							if (horizontal_count == tile) {
								return true;
							}
						}

						if (DiagonalCheck(j, i, symbol) == true) { // separate method for diagonal check
							return true;
						} else {
							break;
						}

					}
				}
			}
		}
		return gameEndCheck;
	}

	/**
	 * This method determines if the game is a draw or not 
	 * 
	 * @return the value if it is a draw
	 */
	public boolean isDraw() {
		boolean check = true;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[j][i] == 'e') { // iterates through to find empty values
					return false;
				}
			}
		}
		return check;
	}
	/**
	 * This method determines the associated evaluate score of the game
	 * 
	 * @return the result of the board
	 */
	public int evalBoard() {
		if (wins('h')) {
			return 0;
		} else if (wins('c')) {
			return 3;
		} else if (isDraw()) {
			return 2;
		} else {
			return 1;
		}

	}
	
	/**
	 * This method checks both diagonals by iterating through them to find if the game is won or not 
	 * @param rowLocation
	 * @param column
	 * @param val
	 * @return boolean if sequence is found in diagonals
	 */
	private boolean DiagonalCheck(int rowLocation, int columnLocation, char val) {
		int consecutiveCount = 0;
		int rows, columns;
		// first check diagonal from top left to bottom right
		if (rowLocation > columnLocation) { // sets the coordinates as the very top left corner of diagonal 
			columns = 0;
			rows = rowLocation - columnLocation;
		} else {
			columns = columnLocation - rowLocation;
			rows = 0;
		}

		while ((rows < size) && (columns < size) && (consecutiveCount != tile)) { // iterates through each diagonal, counting consecutive tiles of matching type
			if (board[rows][columns] == val) {
				consecutiveCount++;
				if (consecutiveCount >= tile) {
					break;
				}
			} else {
				consecutiveCount = 0; // if not found, reset counter to 0
			}
			rows++;
			columns++;
		}

		if (consecutiveCount == tile) { // if the count matches, then it returns true
			return true; 
		}
		consecutiveCount = 0; // checking diagonal starting from top right to bottom left, reset vals
		rows = rowLocation;
		columns = columnLocation;

		while ((rows + 1 < size) && (columns - 1 > 0)) { // while loop pushes the coordinates to very top right of diagonal
			rows++;
			columns--;
		}
		while ((rows >= 0) && (columns < size) && (consecutiveCount != tile)) { // iterates through and finds if it matches or not
			if (board[rows][columns] == val) {
				consecutiveCount++;
			} else {
				consecutiveCount = 0;
			}
			rows--;
			columns++;
		}
		return consecutiveCount == tile; // return boolean if found right
	}
}