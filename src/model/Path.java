package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a path that a player can take based on their current roll
 *
 * @author Marcel
 */
public class Path {

    /**
     * A collection of tile objects representing a valid path that a player can take
     */
    private List<Tile> path;

    /**
     * Constructor for a path object
     *
     * Initialises the path data structure
     */
    public Path() {
        this.path = new ArrayList<>();
    }

    /**
     * Checks if the Tile t is contained within the current path
     *
     * Used to check if the current path is going to loop or not
     *
     * @param t Tile to check
     * @return Whether or not if the tile is contained in the current path
     */
    public boolean contains(Tile t) {
        return path.contains(t);
    }

    /**
     * Getter
     *
     * Gets the current path
     *
     * @return {@link Path}
     */
    public List<Tile> getPath() {
        return path;
    }

    /**
     * Adds the tile to the current path
     *
     * @param t Tile to add
     */
    public void addToPath(Tile t) {
        this.path.add(t);
    }
}
