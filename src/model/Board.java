package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map.Entry;
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

	private static Tile[][] tiles;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.board = new char[width][height];
		Board.tiles = new Tile[width][height];
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
	public static void parseBoard(String filename, Entities entities) {

		try {
			Scanner s = new Scanner(new File(filename));
			int y = 0;
			while (s.hasNextLine()) {
				String line = s.nextLine();
				for (int x = 0; x < line.length(); x++) {

					if (line.charAt(x) == '@') {
						tiles[x][y] = new BoundaryTile(x, y, false);
					} else if (line.charAt(x) == '-') {
						tiles[x][y] = new Tile(x, y, false);
					} else if (line.charAt(x) == 'K') {
						tiles[x][y] = new RoomTile(x, y, true, 'K');
						entities.getRooms().get("Kitchen").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'k') {
						tiles[x][y] = new RoomTile(x, y, false, 'k');
						entities.getRooms().get("Kitchen").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'B') {
						tiles[x][y] = new RoomTile(x, y, true,'B');
						entities.getRooms().get("Ball Room").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'b') {
						tiles[x][y] = new RoomTile(x, y, false,'b');
						entities.getRooms().get("Ball Room").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'C') {
						tiles[x][y] = new RoomTile(x, y, true,'C');
						entities.getRooms().get("Conservatory").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'c') {
						tiles[x][y] = new RoomTile(x, y, false,'c');
						entities.getRooms().get("Conservatory").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'D') {
						tiles[x][y] = new RoomTile(x, y, true,'D');
						entities.getRooms().get("Dining Room").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'd') {
						tiles[x][y] = new RoomTile(x, y, false,'d');
						entities.getRooms().get("Dining Room").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'X') {
						tiles[x][y] = new RoomTile(x, y, true,'X');
						entities.getRooms().get("Pool").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'x') {
						tiles[x][y] = new RoomTile(x, y, false,'x');
						entities.getRooms().get("Pool").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'I') {
						tiles[x][y] = new RoomTile(x, y, true,'I');
						entities.getRooms().get("Billiard Room").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'i') {
						tiles[x][y] = new RoomTile(x, y, false,'i');
						entities.getRooms().get("Billiard Room").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'Y') {
						tiles[x][y] = new RoomTile(x, y, true,'Y');
						entities.getRooms().get("Library").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'y') {
						tiles[x][y] = new RoomTile(x, y, false,'y');
						entities.getRooms().get("Library").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'H') {
						tiles[x][y] = new RoomTile(x, y, true,'H');
						entities.getRooms().get("Hall").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'h') {
						tiles[x][y] = new RoomTile(x, y, false,'h');
						entities.getRooms().get("Hall").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'S') {
						tiles[x][y] = new RoomTile(x, y, true,'S');
						entities.getRooms().get("Study").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 's') {
						tiles[x][y] = new RoomTile(x, y, false,'s');
						entities.getRooms().get("Study").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'L') {
						tiles[x][y] = new RoomTile(x, y, true,'L');
						entities.getRooms().get("Lounge").getTiles().add((RoomTile) tiles[x][y]);
					} else if (line.charAt(x) == 'l') {
						tiles[x][y] = new RoomTile(x, y, false,'l');
						entities.getRooms().get("Lounge").getTiles().add((RoomTile) tiles[x][y]);
					} else {
						tiles[x][y] = new Tile(x, y, true);
					}
				}

				y++;
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file: " + filename);
		}

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
				if (tiles[x][y] instanceof RoomTile) {
					System.out.print(tiles[x][y].getName() + " ");
				} else {
					System.out.printf("  ");
				}
			}
			System.out.println();

		}
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public static void main(String[] args) {

	}

}
