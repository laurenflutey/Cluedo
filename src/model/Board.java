package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.stage.FileChooser;

/**
 * Class representing the Cluedo board
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Board {

	private char[][] board;

	private int height;
	private int width;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.board = new char[width][height];
	}

	public Board() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the board
	 */
	public char[][] getBoard() {
		return board;
	}

	/**
	 * method to move a piece on the board
	 * 
	 * @param p
	 *            piece to move
	 * @param fromX
	 *            originX
	 * @param fromY
	 *            originY
	 * @param toX
	 *            destinationX
	 * @param toY
	 *            destinationY
	 */
	public void movePieceOnBoard(char p, int fromX, int fromY, int toX, int toY) {
		board[fromX][fromY] = '0';
		board[toX][toY] = p;
	}

	/**
	 * simple parser for the file
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public void parseBoard(String filename) {

		try {
			Scanner s = new Scanner(new File(filename));
			int y = 0;
			while (s.hasNextLine()) {
				String line = s.nextLine();
				for (int x = 0; x < line.length(); x++) {
					board[x][y] = line.charAt(x);
				}
				y++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file: " + filename);
		}
	}

	public void printBoard() {
		for (int y = 0; y < this.width; y++) {
			for (int x = 0; x < this.height; x++) {
				if(board[x][y] == '0'){
					System.out.print(". ");
				}
				else if(board[x][y] == 'N'){
					System.out.print("█ ");
				}
				else{
					System.out.print(board[x][y] + " ");
				}

			}
			System.out.println();
		}

	}
	
	public static void main(String[] args){
		Board b = new Board(26, 26);
		b.parseBoard("Board.txt");
		b.printBoard();
	}

}
