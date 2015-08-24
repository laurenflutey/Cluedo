package view.gui.game.components;

import controller.GuiGameController;
import model.*;
import view.gui.game.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Game canvas, which is embedded in the JFrame of the {@link GameFrame}. The
 * canvas is used to draw the current state of the game BOARD for the player to
 * interact with.
 *
 * @author Marcel
 * @author Reuben
 */
public class GameCanvas extends Canvas {

	/**
	 * Static dimension representing width of the GameCanvas
	 */
	public static int width = 800;

	/**
	 * Static dimension representing height of the GameCanvas
	 */
	public static int height = 832;

	/**
	 * Size of a single tileSize in the game
	 */
	private int tileSize = 32;

	private final Tile[][] tiles;
	private final GuiGameController GUIGAMECONTROLLER;
	private final GameFrame GAMEFRAME;

	// An array of pixels representing the game canvas
	private int[] pixels;

	// TODO Remove
	private final Random random = new Random();
	private int globalXOffset;
	private int globalYOffset;

	private Player currentPlayerHovered;
	private Weapon currentWeaponHovered;
	private Room currentRoomHovered;


	/**
	 * Constructor
	 *
	 * Creates the GameCanvas and assigns it to it position on the parent grid
	 * bag layout
	 *
	 * @param guiGameController
	 *            Board
	 * @param contentPane
	 *            Parent panel
	 */
	public GameCanvas(final GuiGameController guiGameController, final JPanel contentPane, final GameFrame gameFrame) {
		GUIGAMECONTROLLER = guiGameController;
		GAMEFRAME = gameFrame;
		tiles = GUIGAMECONTROLLER.getEntities().getBoard().getTiles();

		// Set up grid bag constraints
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridheight = 3;

		// add canvas to parent panel
		contentPane.add(this, constraints);

		addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				double scaleX = GAMEFRAME.getCanvasWidth() / (double)width;
				double scaleY = GAMEFRAME.getCanvasHeight() / (double)height;

				int x = e.getX() - globalXOffset;
				int y = e.getY() - globalYOffset;

				double trueX = x / (tileSize * scaleX);
				double trueY = y / (tileSize * scaleY);

				currentPlayerHovered = GUIGAMECONTROLLER.getCurrentPlayerHovered((int)trueX, (int)trueY);

				if (currentPlayerHovered == null) {
					currentWeaponHovered = GUIGAMECONTROLLER.getCurrentWeaponHovered((int)trueX, (int)trueY);
					if (currentWeaponHovered == null) {
						currentRoomHovered = GUIGAMECONTROLLER.getCurrentRoomHovered((int)trueX, (int)trueY);
					}
				}
			}
		});

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				double scaleX = GAMEFRAME.getCanvasWidth() / (double)width;
				double scaleY = GAMEFRAME.getCanvasHeight() / (double)height;

				int x = e.getX() - globalXOffset;
				int y = e.getY() - globalYOffset;

				double trueX = x / (tileSize * scaleX);
				double trueY = y / (tileSize * scaleY);

				Move move = new Move((int)trueX, (int)trueY);

				GUIGAMECONTROLLER.sendMove(move);
			}
		});

		load();

		// init pixel array, 1D over 2D for access speed
		pixels = new int[width * height];
	}

	/**
	 * Method to update the contents of the canvas
	 */
	public void tick() {
		// TODO Create image raster and convert to buffered image
		// TODO aim for 60 fps, and about 120 ticks a second
	}

	/**
	 * Clears the canvas
	 */
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	/**
	 * Alternate render method that off sets the render area by a specified
	 * amount
	 *
	 * @param xOffSet
	 *            x offset value
	 * @param yOffSet
	 *            y offset value
	 */
	public void render(int xOffSet, int yOffSet) {
		if (tileSize == 32) {
			xOffSet = 0;
			yOffSet = 0;
		} else {
			xOffSet = xOffSet * tileSize + width / 2;
			yOffSet = yOffSet * tileSize + height / 2;
			globalXOffset = xOffSet;
			globalYOffset = yOffSet;
		}

		for (int y = 0; y < height; y++) {
			int yy = y + yOffSet;
			if (yy < 0 || yy >= height)
				continue;
			for (int x = 0; x < width; x++) {
				int xx = x + xOffSet;
				if (xx < 0 || xx >= width)
					continue;

				if (yy < tileSize && xx > width - tileSize * 6) {
					renderHoverBar(yy, xx, x, y);
				} else {

					// Gets the absolute tile position, rather than the pixel
					// position
					Tile currentTile = tiles[x / tileSize][y / tileSize];

					if (currentTile.isWallTile()) {
						if (tileSize == 32) {
							pixels[xx + yy * width] = wall32Pixels[x % tileSize + y % tileSize * tileSize];
						} else {
							pixels[xx + yy * width] = wall64Pixels[x % tileSize + y % tileSize * tileSize];
						}
					} else if (currentTile.isBoundary()) {
						if (tileSize == 32) {
							pixels[xx + yy * width] = boundary32Pixels[x % tileSize + y % tileSize * tileSize];
						} else {
							pixels[xx + yy * width] = boundary64Pixels[x % tileSize + y % tileSize * tileSize];
						}
					} else {
						if (tileSize == 32) {
							pixels[xx + yy * width] = floor32Pixels[x % tileSize + y % tileSize * tileSize];
						} else {
							pixels[xx + yy * width] = floor64Pixels[x % tileSize + y % tileSize * tileSize];
						}
					}

					if (currentTile.isOccupied()) {
						Player player = currentTile.getPlayer();
						if (player != null) {
							renderPlayers(x, y, yy, xx, player);
						} else {
							renderWeapon(x, y, yy, xx, currentTile);
						}
					}
				}
			}
		}
	}

	private void renderHoverBar(int yy, int xx, int x, int y) {
		int border = tileSize/8;
		int xOffset = width - tileSize * 6;


		if (yy < border || yy > tileSize - border) {
			pixels[xx + yy * width] = Color.GRAY.getRGB();
		}else if ((xx > xOffset && xx < xOffset + border)
				|| (xx < width && xx > width - border)) {
			pixels[xx + yy * width] = Color.GRAY.getRGB();
		} else {

			int textX = (xx % 612);
			int textY = (yy % tileSize - 4);

			int position = textX + textY * 184;

			if (position > 4415) {
				// I am lazy and hate arrays
				return;
			}


			int col = 0;

			if (currentPlayerHovered != null) {
				col = renderPlayerHover(position, col);
			} else if (currentWeaponHovered != null) {
				col = renderWeaponHover(position, col);
			} else if (currentRoomHovered != null) {
				col = renderRoomHover(position, col);
			}

			if (col != -65316) {
				pixels[xx + yy * width] = col;
			} else {
				pixels[xx + yy * width] = Color.BLACK.getRGB();
			}
		}
	}

	private int renderRoomHover(int position, int col) {
		char roomId = currentRoomHovered.getID();
		if (roomId == 'K') {
			col = kitchenHoverText32Pixels[position];
		} else if (roomId == 'S') {
			col = studyHoverText32Pixels[position];
		} else if (roomId == 'L') {
			col = loungeHoverText32Pixels[position];
		} else if (roomId == 'C') {
			col = conservatoryRoomHoverText32Pixels[position];
		} else if (roomId == 'B') {
			col = ballroomHoverText32Pixels[position];
		} else if (roomId == 'I') {
			col = billiardRoomHoverText32Pixels[position];
		} else if (roomId == 'Y') {
			col = libraryHoverText32Pixels[position];
		} else if (roomId == 'H') {
			col = hallRoomHoverText32Pixels[position];
		} else if (roomId == 'D') {
			col = diningRoomHoverText32Pixels[position];
		} else if (roomId == 'X') {
			col = poolHoverText32Pixels[position];
		}
		return col;
	}

	private int renderWeaponHover(int position, int col) {
		char weaponId = currentWeaponHovered.getId();
		if (weaponId == '!'){
			col = candlestickHoverText32Pixels[position];
		} else if (weaponId == '%') {
			col = daggerHoverText32Pixels[position];
		} else if (weaponId == '?') {
			col = leadpipeHoverText32Pixels[position];
		} else if (weaponId == '&') {
			col = revolverHoverText32Pixels[position];
		} else if (weaponId == '#') {
			col = ropeHoverText32Pixels[position];
		} else if (weaponId == '*') {
			col = spannerHoverText32Pixels[position];
		}
		return col;
	}

	private int renderPlayerHover(int position, int col) {
		char playerChar = currentPlayerHovered.getCh();
		if (playerChar == 'p') {
            col = peacockHoverText32Pixels[position];
        }

        else if (playerChar == 'r') {
            col = plumHoverText32Pixels[position];
        }

        else if (playerChar == 's') {
            col = scarHoverText32Pixels[position];
        }

        else if (playerChar == 'm') {
            col = mustHoverText32Pixels[position];
        }

        else if (playerChar == 'w') {
            col = whiteHoverText32Pixels[position];
        }

        else if (playerChar == 'g') {
            col = greenHoverText32Pixels[position];
        }
		return col;
	}

	/**
	 * Delegate method to handle the rendering of the players to the board
	 *
	 * @param x
	 * @param y
	 * @param yy
	 *            Absolute y position after offset
	 * @param xx
	 *            Absolute x position after offset
	 * @param player
	 *            The player to be rendered to the current tile
	 */
	private void renderPlayers(int x, int y, int yy, int xx, Player player) {
		if (tileSize == 32) {
			if (player.getCh() == 'p') {
				int col = peacockSpritesheet32Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 'r') {
				int col = plumSpritesheet32Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 's') {
				int col = scarSpritesheet32Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 'm') {
				int col = mustSpritesheet32Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 'w') {
				int col = whiteSpritesheet32Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 'g') {
				int col = greenSpritesheet32Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			}
		} else {
			if (player.getCh() == 'p') {
				int col = peacockSpritesheet64Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 'r') {
				int col = plumSpritesheet64Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 's') {
				int col = scarSpritesheet64Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 'm') {
				int col = mustSpritesheet64Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 'w') {
				int col = whiteSpritesheet64Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (player.getCh() == 'g') {
				int col = greenSpritesheet64Pixels[x % tileSize + y % tileSize * tileSize * 4];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			}
		}
	}

	/**
	 * Method to handle the rendering of a weapon to the board
	 *
	 * @param x
	 * @param y
	 * @param yy
	 *            The absolute y position, once off set
	 * @param xx
	 *            The absolute x position, once off set
	 * @param currentTile
	 *            The that is is currently being rendered
	 */
	private void renderWeapon(int x, int y, int yy, int xx, Tile currentTile) {
		if (tileSize == 32) {
			char weaponId = currentTile.getWeapon().getId();
			if (weaponId == '!') {
				int col = weaponSpritesheet32Pixels[x % tileSize + y % tileSize * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '?') {
				int spritesheetoffsetX = tileSize;
				int col = weaponSpritesheet32Pixels[(x % tileSize) + spritesheetoffsetX + y % tileSize * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '%') {
				int spritesheetoffsetX = tileSize * 2;
				int col = weaponSpritesheet32Pixels[(x % tileSize) + spritesheetoffsetX + y % tileSize * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '&') {
				int spritesheetoffsetY = tileSize;
				int col = weaponSpritesheet32Pixels[(x % tileSize)
						+ (y % tileSize + spritesheetoffsetY) * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '*') {
				int spritesheetoffsetX = tileSize;
				int spritesheetoffsetY = tileSize;
				int col = weaponSpritesheet32Pixels[(x % tileSize) + spritesheetoffsetX
						+ (y % tileSize + spritesheetoffsetY) * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '#') {
				int spritesheetoffsetX = tileSize * 2;
				int spritesheetoffsetY = tileSize;
				int col = weaponSpritesheet32Pixels[(x % tileSize) + spritesheetoffsetX
						+ (y % tileSize + spritesheetoffsetY) * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			}
		} else {
			char weaponId = currentTile.getWeapon().getId();
			if (weaponId == '!') {
				int col = weaponSpritesheet64Pixels[x % tileSize + y % tileSize * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '?') {
				int spritesheetoffsetX = tileSize;
				int col = weaponSpritesheet64Pixels[(x % tileSize) + spritesheetoffsetX + y % tileSize * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '%') {
				int spritesheetoffsetX = tileSize * 2;
				int col = weaponSpritesheet64Pixels[(x % tileSize) + spritesheetoffsetX + y % tileSize * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '&') {
				int spritesheetoffsetY = tileSize;
				int col = weaponSpritesheet64Pixels[(x % tileSize)
						+ (y % tileSize + spritesheetoffsetY) * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '*') {
				int spritesheetoffsetX = tileSize;
				int spritesheetoffsetY = tileSize;
				int col = weaponSpritesheet64Pixels[(x % tileSize) + spritesheetoffsetX
						+ (y % tileSize + spritesheetoffsetY) * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			} else if (weaponId == '#') {
				int spritesheetoffsetX = tileSize * 2;
				int spritesheetoffsetY = tileSize;
				int col = weaponSpritesheet64Pixels[(x % tileSize) + spritesheetoffsetX
						+ (y % tileSize + spritesheetoffsetY) * tileSize * 3];
				if (col != -65316) {
					pixels[xx + yy * width] = col;
				}
			}
		}
	}

	/**
	 * Getter
	 *
	 * @return Int array of pixels in canvas
	 */
	public int[] getPixels() {
		return pixels;
	}

	/**
	 * Toggles the tile size, resulting in a full view of the board or a more
	 * zoomed in version.
	 */
	public void toggleTileSize() {
		// TODO Make this work
		// if (tileSize == 32) {
		// tileSize = 64;
		// } else {
		// tileSize = 32;
		// }
	}

	/*---------------------------------------------------------------------------------
	             _______  _______  _______ __________________ _______  _______
	            (  ____ \(  ____ )(  ____ )\__   __/\__   __/(  ____ \(  ____ \
	            | (    \/| (    )|| (    )|   ) (      ) (   | (    \/| (    \/
	            | (_____ | (____)|| (____)|   | |      | |   | (__    | (_____
	            (_____  )|  _____)|     __)   | |      | |   |  __)   (_____  )
	                  ) || (      | (\ (      | |      | |   | (            ) |
	            /\____) || )      | ) \ \_____) (___   | |   | (____/\/\____) |
	            \_______)|/       |/   \__/\_______/   )_(   (_______/\_______)
	
	----------------------------------------------------------------------------------/*
	/**
	 * Loads the sprites from the image resources into their respective pixel array
	 */
	private void load() {
		try {
			BufferedImage image = ImageIO.read(new File("images/tiles/wall-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, wall32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/tiles/wall-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, wall64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/tiles/floor-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, floor32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/tiles/floor-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, floor64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/tiles/boundary-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, boundary32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/tiles/boundary-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, boundary64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Reverend Green

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/green/green-spritesheet-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, greenSpritesheet32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/green/green-spritesheet-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, greenSpritesheet64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/green/green-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, greenHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Colonel Mustard

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/must/must-spritesheet-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, mustSpritesheet32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/must/must-spritesheet-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, mustSpritesheet64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/must/mustard-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, mustHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Mrs Peacock

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/peacock/peacock-spritesheet-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, peacockSpritesheet32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/peacock/peacock-spritesheet-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, peacockSpritesheet64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/peacock/peacock-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, peacockHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Professor Plum

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/plum/plum-spritesheet-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, plumSpritesheet32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/plum/plum-spritesheet-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, plumSpritesheet64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/plum/plum-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, plumHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Miss Scarlett

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/scar/scar-spritesheet-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, scarSpritesheet32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/scar/scar-spritesheet-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, scarSpritesheet64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/scar/scar-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, scarHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Mrs White

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/white/white-spritesheet-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, whiteSpritesheet32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/white/white-spritesheet-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, whiteSpritesheet64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/characters/white/white-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, whiteHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Weapons

		try {
			BufferedImage image = ImageIO.read(new File("images/weapons/weapon-spritesheet-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, weaponSpritesheet32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/weapons/weapon-spritesheet-64.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, weaponSpritesheet64Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Room text

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/ballroom-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, ballroomHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/billiardroom-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, billiardRoomHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/conservatory-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, conservatoryRoomHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/diningroom-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, diningRoomHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/kitchen-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, kitchenHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/library-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, libraryHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/hall-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, hallRoomHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/lounge-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, loungeHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/pool-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, poolHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/rooms/study-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, studyHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Weapon text

		try {
			BufferedImage image = ImageIO.read(new File("images/weapons/candlestick-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, candlestickHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/weapons/dagger-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, daggerHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/weapons/leadpipe-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, leadpipeHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/weapons/revolver-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, revolverHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/weapons/rope-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, ropeHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/weapons/spanner-text-32.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, spannerHoverText32Pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO Fix this and use it if we have time.
	// private int[] convertFrom32To64(int[] spritesheet, int width) {
	// int[] newSpritesheet = new int[spritesheet.length * 4];
	// for (int i = 0; i < spritesheet.length; i++) {
	// if (i >= width) {
	// newSpritesheet[i * 2 + spritesheet.length] = spritesheet[i];
	// newSpritesheet[i * 2 + spritesheet.length + 1] = spritesheet[i];
	// newSpritesheet[i * 2 + 2 * spritesheet.length] = spritesheet[i];
	// newSpritesheet[i * 2 + 2 * spritesheet.length + 1] = spritesheet[i];
	// } else {
	// newSpritesheet[i * 2] = spritesheet[i];
	// newSpritesheet[i * 2 + 1] = spritesheet[i];
	// newSpritesheet[i * 2 + spritesheet.length] = spritesheet[i];
	// newSpritesheet[i * 2 + spritesheet.length + 1] = spritesheet[i];
	// }
	// }
	// return newSpritesheet;
	// }

	private static int[] wall32Pixels = new int[32 * 32];
	private static int[] floor32Pixels = new int[32 * 32];
	private static int[] boundary32Pixels = new int[32 * 32];
	private static int[] wall64Pixels = new int[64 * 64];
	private static int[] floor64Pixels = new int[64 * 64];
	private static int[] boundary64Pixels = new int[64 * 64];

	private static int[] greenSpritesheet32Pixels = new int[128 * 96];
	private static int[] greenSpritesheet64Pixels = new int[256 * 192];
	private static int[] greenHoverText32Pixels = new int[184 * 24];

	private static int[] mustSpritesheet32Pixels = new int[128 * 96];
	private static int[] mustSpritesheet64Pixels = new int[256 * 192];
	private static int[] mustHoverText32Pixels = new int[184 * 24];

	private static int[] peacockSpritesheet32Pixels = new int[128 * 96];
	private static int[] peacockSpritesheet64Pixels = new int[256 * 192];
	private static int[] peacockHoverText32Pixels = new int[184 * 24];

	private static int[] plumSpritesheet32Pixels = new int[128 * 96];
	private static int[] plumSpritesheet64Pixels = new int[256 * 192];
	private static int[] plumHoverText32Pixels = new int[184 * 24];

	private static int[] scarSpritesheet32Pixels = new int[128 * 96];
	private static int[] scarSpritesheet64Pixels = new int[256 * 192];
	private static int[] scarHoverText32Pixels = new int[184 * 24];

	private static int[] whiteSpritesheet32Pixels = new int[128 * 96];
	private static int[] whiteSpritesheet64Pixels = new int[256 * 192];
	private static int[] whiteHoverText32Pixels = new int[184 * 24];

	private static int[] weaponSpritesheet32Pixels = new int[96 * 64];
	private static int[] weaponSpritesheet64Pixels = new int[192 * 128];

	// Room text
	private static int[] ballroomHoverText32Pixels = new int[184 * 24];
	private static int[] billiardRoomHoverText32Pixels = new int[184 * 24];
	private static int[] conservatoryRoomHoverText32Pixels = new int[184 * 24];
	private static int[] diningRoomHoverText32Pixels = new int[184 * 24];
	private static int[] kitchenHoverText32Pixels = new int[184 * 24];
	private static int[] libraryHoverText32Pixels = new int[184 * 24];
	private static int[] loungeHoverText32Pixels = new int[184 * 24];
	private static int[] hallRoomHoverText32Pixels = new int[184 * 24];
	private static int[] poolHoverText32Pixels = new int[184 * 24];
	private static int[] studyHoverText32Pixels = new int[184 * 24];

	// Weapon text
	private static int[] candlestickHoverText32Pixels = new int[184 * 24];
	private static int[] daggerHoverText32Pixels = new int[184 * 24];
	private static int[] leadpipeHoverText32Pixels = new int[184 * 24];
	private static int[] revolverHoverText32Pixels = new int[184 * 24];
	private static int[] ropeHoverText32Pixels = new int[184 * 24];
	private static int[] spannerHoverText32Pixels = new int[184 * 24];

}
