package com.aksu.mancalagame.mancala.domain;

public enum Player {
    PLAYER1,
    PLAYER2;

    public Player getOpposingPlayer() {
        return this == PLAYER1 ? PLAYER2 : PLAYER1;
    }
}
