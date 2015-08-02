package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class representing the Cluedo board
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Board {

	// TODO create a way to ensure that the player is entering a room in the
	// correct orientation

	private char[][] board;

	private int height;
	private int width;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.board = new char[width][height];
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
		printBoard();
	}

	public ArrayList<XYPosition> findAllowedMoves(int xOrigin, int yOrigin, int roll) {

		ArrayList<XYPosition> allowed = new ArrayList<XYPosition>();

		board[xOrigin][yOrigin] = '.';

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[j][i] == '—') {
					if (roll >= Math.abs(xOrigin - j) + Math.abs(yOrigin - i)
							&& ((Math.abs(xOrigin - j) + Math.abs(yOrigin - i)) % roll == 0)) {
						allowed.add(new XYPosition(j, i));
					}
				}
			}
		}

		return allowed;
	}

	private class XYPosition {
		int x;
		int y;

		public XYPosition(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	/**
	 * prints the board
	 */
	public void printBoard() {
		for (int y = 0; y < this.width; y++) {
			for (int x = 0; x < this.height; x++) {
				if (board[x][y] == '-') {
					System.out.print("  ");
				} else if (board[x][y] == '@') {
					System.out.print("▓ ");
				} else if (board[x][y] == '?') {
					System.out.print("  ");
				} else {
					System.out.print(board[x][y] + " ");
				}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		Board b = new Board(28, 28);
		b.parseBoard("Board.txt");
		//ArrayList<XYPosition> list = b.findAllowedMoves(10, 10, 4);
		//char count = '1';
		//for(XYPosition li : list){
		//	b.board[li.x][li.y] = count++;
		//}
		
		
		//System.out.flush();
		
		//System.out.println("\f");
		
		//System.out.println("HEllo");
		
	}

}
