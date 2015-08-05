package tests;

import controller.MovementController;
import model.*;
import model.Character;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test suite for the {@link controller.MovementController} which is in control of the pathing algorithm
 * that allows a {@link model.Player} to move around the board.
 *
 * @author Marcel van Workum
 */
public class MovementControllerTests {

    private Entities entities;
    private MovementController movementController;

    public MovementControllerTests() {
        entities = new Entities();
        movementController = new MovementController(entities.getBoard());
    }

    @Test
     public void testMovementBasic1() {
        Move move = new Move(8, 10);
        int roll = 5;

        Player player = setupPlayer(new Player("Test", 't', 13, 10));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertTrue(movementController.isValidMove(move, player, roll));
    }

    @Test
     public void testMovementBasic2() {
        Move move = new Move(17, 10);
        int roll = 4;

        Player player = setupPlayer(new Player("Test", 't', 13, 10));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertTrue(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementBasic3() {
        Move move = new Move(12, 19);
        int roll = 6;

        Player player = setupPlayer(new Player("Test", 't', 12, 23));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertTrue(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementOntoSelf1() {
        Move move = new Move(13, 10);
        int roll = 5;

        Player player = setupPlayer(new Player("Test", 't', 13, 10));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementOntoSelf2() {
        Move move = new Move(10, 10);
        int roll = 1;

        Player player = setupPlayer(new Player("Test", 't', 10, 10));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementOntoSelf3() {
        Move move = new Move(12, 23);
        int roll = 1;

        Player player = setupPlayer(new Player("Test", 't', 12, 23));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player, roll));
    }

    @Test
     public void testMovementAroundWall1() {
        Move move = new Move(15, 23);
        int roll = 6;

        Player player = setupPlayer(new Player("Test", 't', 13, 23));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertTrue(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementAroundWall2() {
        Move move = new Move(16, 23);
        int roll = 6;

        Player player = setupPlayer(new Player("Test", 't', 18, 23));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertTrue(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementAroundWall3() {
        Move move = new Move(16, 23);
        int roll = 10;

        Player player = setupPlayer(new Player("Test", 't', 15, 16));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertTrue(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementAroundWallFail1() {
        Move move = new Move(15, 23);
        int roll = 4;

        Player player = setupPlayer(new Player("Test", 't', 13, 23));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertTrue(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementAroundWallFail2() {
        Move move = new Move(16, 23);
        int roll = 3;

        Player player = setupPlayer(new Player("Test", 't', 18, 23));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementAroundWallFail3() {
        Move move = new Move(16, 23);
        int roll = 6;

        Player player = setupPlayer(new Player("Test", 't', 15, 16));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementIntoMiddleRoomFail1() {
        Move move = new Move(13, 16);
        int roll = 3;

        Player player = setupPlayer(new Player("Test", 't', 15, 16));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player, roll));
    }

    /**
     * Helper method to create a player object and associate it with all the needed goodness
     * @param player Player to create
     * @return Returns the player object
     */
    private Player setupPlayer(Player player) {

        Tile t = entities.getBoard().getTiles()[player.getxPos()][player.getyPos()];
        t.setPlayer(player);
        assertTrue(t.isOccupied());

        if (t.isRoomTile()) {
            player.setRoom(t.getRoom());
        }
        player.setAlive(true);

        player.setPlayerNumber(1);

        player.setCharacter(new Character("test", 't', player.getxPos(), player.getyPos()));

        return player;
    }
}