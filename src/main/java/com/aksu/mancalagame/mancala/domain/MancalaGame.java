package com.aksu.mancalagame.mancala.domain;

public class MancalaGame {
    private final Board board;
    private Player currentPlayer;

    private Player winner;

    public MancalaGame() {
        this.board = new Board(6);
        currentPlayer = Player.PLAYER1;
    }

    public MancalaGame(Board board, Player startingPlayer) {
        this.board = board;
        this.currentPlayer = startingPlayer;
    }

    public Board getBoardContent() {
        return board;
    }

    public void makeMove(int pitIndex) {
        SowCalculator calculator = new SowCalculator();
        SowResult result = calculator.sow(board, pitIndex, currentPlayer);

        if (result != SowResult.ENDED_ON_BIG_PIT) {
            switchPlayer();
        }

        checkEndOfGame();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    private void checkEndOfGame() {
        Player playerWithEmptySmallPits = board.getPlayerWithEmptySmallPits();

        if (playerWithEmptySmallPits != null) {
            SowCalculator calculator = new SowCalculator();
            calculator.sowAllSmallPitsToBigPit(board, playerWithEmptySmallPits.getOpposingPlayer());

            winner = board.getBigPit(Player.PLAYER1) > board.getBigPit(Player.PLAYER2) ? Player.PLAYER1 : Player.PLAYER2;
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.getOpposingPlayer();
    }
}
