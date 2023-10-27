package com.aksu.mancalagame.mancala.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MancalaGameTest {
    @Test
    public void newGameResultsInInitialBoard() {
        MancalaGame game = new MancalaGame();

        Board board = game.getBoardContent();
        Board initialBoard = new Board(6);

        assertThat(board).isEqualTo(initialBoard);
    }

    @Test
    public void getBoardContentReturnsUpdatedContentAfterFirstMove() {
        MancalaGame game = new MancalaGame();
        Board board = game.getBoardContent();

        game.makeMove(3);

        assertThat(board.getSmallPits(Player.PLAYER2))
                .isEqualTo(List.of(7, 7, 7, 6, 6, 6));
    }

    @Test
    public void makeMoveChangesCurrentPlayerToPlayer2() {
        MancalaGame game = new MancalaGame();
        game.makeMove(3);

        assertThat(game.getCurrentPlayer()).isEqualTo(Player.PLAYER2);
    }

    @Test
    public void makingAMove2TimesChangesPlayerBackToPlayer1() {
        MancalaGame game = new MancalaGame();
        game.makeMove(2);
        game.makeMove(4);

        assertThat(game.getCurrentPlayer()).isEqualTo(Player.PLAYER1);
    }

    @Test
    public void makingAMove2TimesChangesPlayer2PitsCorrectly() {
        MancalaGame game = new MancalaGame();
        Board board = game.getBoardContent();
        List<Integer> player2Pits = board.getSmallPits(Player.PLAYER2);

        game.makeMove(3); // Should become Player1: [6, 6, 6, 0, 7, 7] Player2: [7, 7, 7, 6, 6, 6]
        game.makeMove(4); // Should become Player1: [7, 7, 7, 0, 7, 7] Player2: [7, 7, 7, 6, 0, 7]

        assertThat(player2Pits).isEqualTo(List.of(7, 7, 7, 6, 0, 7));
    }

    @Test
    public void whenLastStoneIsPutInBigPitCurrentPlayerDoesNotChange() {
        MancalaGame game = new MancalaGame();
        game.makeMove(0);

        assertThat(game.getCurrentPlayer()).isEqualTo(Player.PLAYER1);
    }

    @Test
    public void whenGameStartsGetWinnerReturnsNull() {
        MancalaGame game = new MancalaGame();
        assertThat(game.getWinner()).isNull();
    }

    @Test
    public void afterGameEndingMoveGetWinnerReturnsWinner() {
        Board board = new Board(Map.of(
                Player.PLAYER1, new PlayerBoard(List.of(0, 0, 0, 0, 0, 4), 5),
                Player.PLAYER2, new PlayerBoard(List.of(1, 4, 5, 6, 2, 0), 3)
        ));
        MancalaGame game = new MancalaGame(board, Player.PLAYER1);
        game.makeMove(5);

        assertThat(game.getWinner()).isNotNull();
    }

    @Test
    public void afterWinnerAllSmallPitsMustBeEmpty() {
        Board board = new Board(Map.of(
                Player.PLAYER1, new PlayerBoard(List.of(0, 0, 0, 0, 0, 4), 5),
                Player.PLAYER2, new PlayerBoard(List.of(1, 4, 5, 6, 2, 0), 3)
        ));
        MancalaGame game = new MancalaGame(board, Player.PLAYER1);
        game.makeMove(5);

        assertThat(board.getSmallPits(Player.PLAYER1)).isEqualTo(List.of(0, 0, 0, 0, 0, 0));
        assertThat(board.getSmallPits(Player.PLAYER2)).isEqualTo(List.of(0, 0, 0, 0, 0, 0));
    }


    @Test
    public void winnerIsCalculatedCorrectlyWhenPlayer2Wins() {
        Board board = new Board(Map.of(
                Player.PLAYER1, new PlayerBoard(List.of(5, 3, 0, 2, 0, 4), 5),
                Player.PLAYER2, new PlayerBoard(List.of(0, 0, 0, 0, 0, 3), 22)
        ));
        MancalaGame game = new MancalaGame(board, Player.PLAYER1);
        game.makeMove(1);
        game.makeMove(5);

        assertThat(game.getWinner()).isEqualTo(Player.PLAYER2);
    }

    @Test
    public void winnerIsCalculatedCorrectlyWhenPlayer1Wins() {
        Board board = new Board(Map.of(
                Player.PLAYER1, new PlayerBoard(List.of(0, 0, 0, 0, 0, 3), 25),
                Player.PLAYER2, new PlayerBoard(List.of(5, 3, 0, 2, 0, 4), 3)
        ));
        MancalaGame game = new MancalaGame(board, Player.PLAYER1);
        game.makeMove(5);

        assertThat(game.getWinner()).isEqualTo(Player.PLAYER1);
    }
}