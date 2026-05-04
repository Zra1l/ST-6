package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.awt.GridLayout;

public class ProgramTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testInitialState() {
        assertEquals(State.PLAYING, game.state);
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testCheckStateAllConditions() {
        game.symbol = 'X';
        char[] hWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(hWin));

        char[] hWin2 = {' ', ' ', ' ', 'O', 'O', 'O', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(hWin2));

        char[] hWin3 = {' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(hWin3));

        char[] vWin1 = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(vWin1));

        char[] vWin2 = {' ', 'O', ' ', ' ', 'O', ' ', ' ', 'O', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(vWin2));

        char[] vWin3 = {' ', ' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(vWin3));

        char[] dWin1 = {'O', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(dWin1));

        char[] dWin2 = {' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(dWin2));

        char[] drawBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(drawBoard));
    }

    @Test
    void testEvaluatePosition() {
        char[] winBoard = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(100, game.evaluatePosition(winBoard, game.player1));
        assertEquals(-100, game.evaluatePosition(winBoard, game.player2));
        
        char[] drawBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(drawBoard, game.player1));

        char[] playingBoard = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(playingBoard, game.player1));
    }

    @Test
    void testMiniMaxLogic() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[4] = 'O'; 
        
        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);

        for (int i = 0; i < 9; i++) game.board[i] = ' ';
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[4] = 'X';
        game.board[5] = 'X';
        game.board[6] = 'X';
        game.board[7] = 'O';
        game.board[8] = 'X';
        
        int winMove = game.MiniMax(game.board, game.player2);
        assertEquals(3, winMove);
    }

    @Test
    void testCellClass() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(1, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testPlayerClass() {
        Player p = new Player();
        p.symbol = 'X';
        p.move = 5;
        p.selected = true;
        p.win = false;
        assertEquals('X', p.symbol);
        assertTrue(p.selected);
        assertFalse(p.win);
    }

    @Test
    void testUtilityMethods() {
        char[] b = new char[9];
        int[] ib = new int[9];
        ArrayList<Integer> al = new ArrayList<>();
        assertDoesNotThrow(() -> {
            Utility.print(b);
            Utility.print(ib);
            Utility.print(al);
        });
    }

    @Test
    void testGenerateMoves() {
        char[] board = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> move_list = new ArrayList<>();
        game.generateMoves(board, move_list);
        assertEquals(7, move_list.size());
    }

    @Test
    void testUIComponentsCoverage() {
        assertDoesNotThrow(() -> {
            TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
            TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
            char marker = cell.getMarker();
            assertEquals(' ', marker);
        });
    }
}
