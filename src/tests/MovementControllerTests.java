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

    @Test
    public void testMovementIntoMiddleRoomFail2() {
        Move move = new Move(13, 16);
        int roll = 3;

        Player player = setupPlayer(new Player("Test", 't', 11, 16));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player, roll));
    }

    @Test
    public void testMovementMoveOntoPlayer1() {
        Move move = new Move(8, 10);
        int roll = 5;

        Player player1 = setupPlayer(new Player("Test", 't', 9, 10));
        Player player2 = setupPlayer(new Player("Test", 't', 8, 10));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player1, roll));
    }

    @Test
    public void testMovementMoveOntoPlayer2() {
        Move move = new Move(7, 11);
        int roll = 5;

        Player player1 = setupPlayer(new Player("Test", 't', 9, 10));
        Player player2 = setupPlayer(new Player("Test", 't', 7, 11));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player1, roll));
    }

    @Test
    public void testMovementMoveOntoPlayer3() {
        Move move = new Move(8, 12);
        int roll = 5;

        Player player1 = setupPlayer(new Player("Test", 't', 9, 10));
        Player player2 = setupPlayer(new Player("Test", 't', 8, 12));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player1, roll));
    }

    @Test
    public void testMovementMoveOntoWeapon1() {
        Move move = new Move(8, 12);
        int roll = 5;

        Player player1 = setupPlayer(new Player("Test", 't', 9, 10));

        setupWeapon(move);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player1, roll));
    }

    @Test
    public void testMovementMoveOntoWeapon2() {
        Move move = new Move(10, 10);
        int roll = 5;

        Player player1 = setupPlayer(new Player("Test", 't', 9, 10));

        setupWeapon(move);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player1, roll));
    }

    @Test
    public void testMovementMoveOntoWeapon3() {
        Move move = new Move(12, 10);
        int roll = 5;

        Player player1 = setupPlayer(new Player("Test", 't', 9, 10));

        setupWeapon(move);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        entities.setPlayers(players);

        assertFalse(movementController.isValidMove(move, player1, roll));
    }



    /**
     * Helper method to create a player object and associate it with all the needed goodness
     * @param player Player to create
     * @return Returns the player object
     */
    private Player setupPlayer(Player player) {

        Tile t = entities.getBoard().getTiles()[player.getXPos()][player.getYPos()];
        t.setPlayer(player);
        assertTrue(t.isOccupied());

        if (t.isRoomTile()) {
            player.setRoom(t.getRoom());
        }
        player.setAlive(true);

        player.setPlayerNumber(1);

        player.setCharacter(new Character("test", 't', player.getXPos(), player.getYPos()));

        return player;
    }

    /**
     * Helper method to create a test weapon on the board
     * @param move intended move of the player
     */
    private void setupWeapon(Move move) {
        Weapon weapon = new Weapon("TestWeapon", 'W');
        entities.getBoard().getTiles()[move.getX()][move.getY()].setWeapon(weapon);
    }
}