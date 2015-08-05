package model;

import controller.GameController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

/**
 * Class representing the Cluedo board which is a 2D array of {@link Tile}
 * objects
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Board {

	private int height;
	private int width;

	/**
	 * Collection of tiles that make up the board
	 */
	private Tile[][] tiles;

	/**
	 * Rooms that are on the board
	 */
	private Map<String, Room> rooms;

	/**
	 * Constructs the board setting the height, width and creating the data
	 * structures. The board is then parsed for the Cluedo game.
	 *
	 * @param width width of game board
	 * @param height height of game board
	 */
	public Board(int width, int height, Map<String, Room> rooms) {
		this.width = width;
		this.height = height;
		this.rooms = rooms;
		tiles = new Tile[width][height];
		parseBoard("Board.txt");
	}

	/**
	 * simple parser for the file
	 * 
	 * @param filename name of the board file (Should be Board.txt)
	 * @throws FileNotFoundException This shouldn't happen
	 */
	private void parseBoard(String filename) {
		// If you're reading this comment, you are about to witness some ugly parsing. This code makes me cry at night.
		// I AM SORRY
		/*
					 _____  ___________________   __
					/  ___||  _  | ___ \ ___ \ \ / /
					\ `--. | | | | |_/ / |_/ /\ V /
					 `--. \| | | |    /|    /  \ /
					/\__/ /\ \_/ / |\ \| |\ \  | |
					\____/  \___/\_| \_\_| \_| \_/

		 */

		try {
			Scanner s = new Scanner(new File(filename));
			int y = 0;
			while (s.hasNextLine()) {
				String line = s.nextLine();
				for (int x = 0; x < line.length(); x++) {
					if (line.charAt(x) == '@') {
						tiles[x][y] = new BoundaryTile(x, y, null, false);
					} else if (line.charAt(x) == '-') {
						tiles[x][y] = new Tile(x, y, null, false, ' ');
					} else if (line.charAt(x) == 'K') {
						tiles[x][y] = new Tile(x, y, rooms.get("Kitchen"), true, 'K');
						rooms.get("Kitchen").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'k') {
						tiles[x][y] = new Tile(x, y, rooms.get("Kitchen"), false, '.');
						rooms.get("Kitchen").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == '1') {
						tiles[x][y] = new Tile(x, y, rooms.get("Kitchen"), false, true, '.');
						rooms.get("Kitchen").getTiles().add(tiles[x][y]);
						rooms.get("Kitchen").getDoors().add(tiles[x][y]);
					} else if (line.charAt(x) == 'B') {
						tiles[x][y] = new Tile(x, y, rooms.get("Ball Room"), true, 'B');
						rooms.get("Ball Room").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'b') {
						tiles[x][y] = new Tile(x, y, rooms.get("Ball Room"), false, '.');
						rooms.get("Ball Room").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == '2') {
						tiles[x][y] = new Tile(x, y, rooms.get("Ball Room"), false, true, '.');
						rooms.get("Ball Room").getTiles().add(tiles[x][y]);
						rooms.get("Ball Room").getDoors().add(tiles[x][y]);
					} else if (line.charAt(x) == 'C') {
						tiles[x][y] = new Tile(x, y, rooms.get("Conservatory"), true, 'C');
						rooms.get("Conservatory").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'c') {
						tiles[x][y] = new Tile(x, y, rooms.get("Conservatory"), false, '.');
						rooms.get("Conservatory").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == '3') {
						tiles[x][y] = new Tile(x, y, rooms.get("Conservatory"), false, true, '.');
						rooms.get("Conservatory").getTiles().add(tiles[x][y]);
						rooms.get("Conservatory").getDoors().add(tiles[x][y]);
					} else if (line.charAt(x) == 'D') {
						tiles[x][y] = new Tile(x, y, rooms.get("Dining Room"), true, 'D');
						rooms.get("Dining Room").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'd') {
						tiles[x][y] = new Tile(x, y, rooms.get("Dining Room"), false, '.');
						rooms.get("Dining Room").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == '4') {
						tiles[x][y] = new Tile(x, y, rooms.get("Dining Room"), false, true, '.');
						rooms.get("Dining Room").getTiles().add(tiles[x][y]);
						rooms.get("Dining Room").getDoors().add(tiles[x][y]);
					} else if (line.charAt(x) == 'X') {
						tiles[x][y] = new Tile(x, y, rooms.get("Pool"), true, 'X');
						rooms.get("Pool").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'x') {
						tiles[x][y] = new Tile(x, y, rooms.get("Pool"), false, '.');
						rooms.get("Pool").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'I') {
						tiles[x][y] = new Tile(x, y, rooms.get("Billiard Room"), true, 'I');
						rooms.get("Billiard Room").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'i') {
						tiles[x][y] = new Tile(x, y, rooms.get("Billiard Room"), false, '.');
						rooms.get("Billiard Room").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == '5') {
						tiles[x][y] = new Tile(x, y, rooms.get("Billiard Room"), false, true, '.');
						rooms.get("Billiard Room").getTiles().add(tiles[x][y]);
						rooms.get("Billiard Room").getDoors().add(tiles[x][y]);
					} else if (line.charAt(x) == 'Y') {
						tiles[x][y] = new Tile(x, y, rooms.get("Library"), true, 'Y');
						rooms.get("Library").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'y') {
						tiles[x][y] = new Tile(x, y, rooms.get("Library"), false, '.');
						rooms.get("Library").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == '6') {
						tiles[x][y] = new Tile(x, y, rooms.get("Library"), false, true, '.');
						rooms.get("Library").getTiles().add(tiles[x][y]);
						rooms.get("Library").getDoors().add(tiles[x][y]);
					} else if (line.charAt(x) == 'H') {
						tiles[x][y] = new Tile(x, y, rooms.get("Hall"), true, 'H');
						rooms.get("Hall").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'h') {
						tiles[x][y] = new Tile(x, y, rooms.get("Hall"), false, '.');
						rooms.get("Hall").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == '7') {
						tiles[x][y] = new Tile(x, y, rooms.get("Hall"), false, true, '.');
						rooms.get("Hall").getTiles().add(tiles[x][y]);
						rooms.get("Hall").getDoors().add(tiles[x][y]);
					} else if (line.charAt(x) == 'S') {
						tiles[x][y] = new Tile(x, y, rooms.get("Study"), true, 'S');
						rooms.get("Study").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 's') {
						tiles[x][y] = new Tile(x, y, rooms.get("Study"), false, '.');
						rooms.get("Study").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == '8') {
						tiles[x][y] = new Tile(x, y, rooms.get("Study"), false, true, '.');
						rooms.get("Study").getTiles().add(tiles[x][y]);
						rooms.get("Study").getDoors().add(tiles[x][y]);
					} else if (line.charAt(x) == 'L') {
						tiles[x][y] = new Tile(x, y, rooms.get("Lounge"), true, 'L');
						rooms.get("Lounge").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == 'l') {
						tiles[x][y] = new Tile(x, y, rooms.get("Lounge"), false, '.');
						rooms.get("Lounge").getTiles().add(tiles[x][y]);
					} else if (line.charAt(x) == '9') {
						tiles[x][y] = new Tile(x, y, rooms.get("Lounge"), false, true, '.');
						rooms.get("Lounge").getTiles().add(tiles[x][y]);
						rooms.get("Lounge").getDoors().add(tiles[x][y]);
					} else {
						tiles[x][y] = new Tile(x, y, null, false, ' ');
					}
				}
				y++;
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file: " + filename);
		}

	}

	/**
	 * prints the board
	 */
	public void printBoard() {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if (tiles[x][y] instanceof BoundaryTile) {
					System.out.print("█ ");
				} else if (tiles[x][y].getPlayer() != null) {
					if (tiles[x][y].getPlayer().isCurrentPlayer()) {
						if (GameController.IS_GAME_COLOURED) {
							System.out.print("\u001B[32m" + tiles[x][y].getPlayer().getPlayerNumber() + "\u001B[0m" + " ");
						} else {
							System.out.print(tiles[x][y].getPlayer().getPlayerNumber() + " ");
						}
					} else {
						if (GameController.IS_GAME_COLOURED) {
							System.out.print("\u001B[31m" + tiles[x][y].getPlayer().getPlayerNumber() + "\u001B[0m" + " ");
						} else {
							System.out.print(tiles[x][y].getPlayer().getPlayerNumber() + " ");
						}
					}
				} else if (tiles[x][y].getWeapon() != null) {
					if (GameController.IS_GAME_COLOURED) {
						System.out.print("\u001B[33m" + tiles[x][y].getWeapon().getId() + "\u001B[0m" + " ");
					} else {
						System.out.print(tiles[x][y].getWeapon().getId() + " ");
					}
				} else {
					System.out.printf(tiles[x][y].getName() + " ");
				}

			}
			if (GameController.IS_GAME_COLOURED) {
				System.out.println("\u001B[34m" + " " + (y + 1) + "\u001B[0m");
			} else {
				System.out.println(" " + (y + 1));
			}
		}
		if (GameController.IS_GAME_COLOURED){
			System.out.print("\u001B[34m" + "\nA B C D E F G H I J K L M N O P Q R S T U V W X \n" + "\u001B[0m" + "\n\n\n");
		} else {
			System.out.print("\nA B C D E F G H I J K L M N O P Q R S T U V W X \n\n\n");
		}
	}

	/**
	 * Get tiles.
	 *
	 * @return the tile [ ] [ ]
     */
	public Tile[][] getTiles() {
		return tiles;
	}

}
