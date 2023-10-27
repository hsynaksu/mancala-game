package com.aksu.mancalagame.mancala.web;

import com.aksu.mancalagame.mancala.domain.Board;
import com.aksu.mancalagame.mancala.domain.Player;

import java.util.List;

public record BoardView(
        List<Integer> player1pits,
        List<Integer> player2pits,
        int player1BigPit,
        int player2BigPit
) {
    public static BoardView from(Board board) {
        return new BoardView(
                board.getSmallPits(Player.PLAYER1),
                board.getSmallPits(Player.PLAYER2),
                board.getBigPit(Player.PLAYER1),
                board.getBigPit(Player.PLAYER2)
        );
    }
}
