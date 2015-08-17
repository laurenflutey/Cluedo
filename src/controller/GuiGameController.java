package controller;

import model.Board;
import model.Entities;
import model.Tile;
import view.gui.GameFrame;

/**
 * Created by Marcel on 17/08/15.
 */
public class GuiGameController {

    private final Board BOARD;
    private final Entities ENTITIES;

    private final Tile[][] tiles;
    private final MovementController MOVEMENT_CONTROLLER;
    private final GameFrame DISPLAY;

    /**
     * Constructor for the {@link GuiGameController} class
     */
    public GuiGameController() {
        this.ENTITIES = new Entities();
        this.BOARD = ENTITIES.getBoard();

        tiles = ENTITIES.getBoard().getTiles();

		/* Assign board to movement controller */
        this.MOVEMENT_CONTROLLER = new MovementController(BOARD);

        DISPLAY = new GameFrame(BOARD);

        DISPLAY.repaint();

    }

    public static void main(String[] args) {
        new GuiGameController();
    }
}
