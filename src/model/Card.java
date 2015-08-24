package model;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Class to represent a card in the Cluedo game. A card represents a
 * {@link Weapon}, {@link Character} or {@link Room} and are used when a
 * {@link Player} makes a suggestion or accusation.
 *
 * @author Marcel
 * @author Reuben
 */
public class Card {

	/**
	 * Name of the card
	 */
	private String name;

	/**
	 * A card may be of type weapon, character or room
	 */
	private String type;

	private String path;

	/**
	 * Constructor
	 *
	 * @param name
	 *            Name of the card
	 * @param type
	 *            Type of the card
	 */
	public Card(String name, String type, String path) {
		this.name = name;
		this.setType(type);
		this.path = path;
	}

	public Card(String name, String type) {
		this.name = name;
		this.setType(type);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public Icon getImage() {
		Icon icon = new ImageIcon(path);
		return icon;
	}

}
