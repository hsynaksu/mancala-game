package com.aksu.mancalagame.mancala.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SowCalculatorTest {
    @Test
    public void selectingFirstPitCorrectlyDistributesStones() {
        SowCalculator calculator = new SowCalculator();
        Board board = new Board(6);

        calculator.sow(board, 0, Player.PLAYER1);

        List<Integer> smallPits = board.getSmallPits(Player.PLAYER1);
        Integer bigPit = board.getBigPit(Player.PLAYER1);

        assertThat(smallPits).isEqualTo(List.of(0, 7, 7, 7, 7, 7));
        assertThat(bigPit).isEqualTo(1);
    }

    @Test
    public void selectingSecondPitCorrectlyDistributesStones() {
        SowCalculator calculator = new SowCalculator();
        Board board = new Board(6);

        SowResult result = calculator.sow(board, 1, Player.PLAYER1);

        List<Integer> smallPits = board.getSmallPits(Player.PLAYER1);
        Integer bigPit = board.getBigPit(Player.PLAYER1);

        assertThat(smallPits).isEqualTo(List.of(6, 0, 7, 7, 7, 7));
        assertThat(bigPit).isEqualTo(1);
        assertThat(result).isEqualTo(SowResult.DEFAULT);
    }

    @Test
    public void selectingSecondPitDistributesStoneToOtherPlayer() {
        SowCalculator calculator = new SowCalculator();
        Board board = new Board(6);

        SowResult result = calculator.sow(board, 1, Player.PLAYER1);

        List<Integer> opponentSmallPits = board.getSmallPits(Player.PLAYER2);

        assertThat(opponentSmallPits).isEqualTo(List.of(7, 6, 6, 6, 6, 6));
        assertThat(result).isEqualTo(SowResult.DEFAULT);
    }

    @Test
    public void sowingSkipsOpponentBigPit() {
        SowCalculator calculator = new SowCalculator();
        Board board = new Board(12);

        calculator.sow(board, 5, Player.PLAYER1);

        assertThat(board.getBigPit(Player.PLAYER2)).isZero();
    }

    @Test
    public void sowReturnsCorrectResultWhenSpecialCasesNotHappen() {
        Board board = new Board(6);
        SowCalculator calculator = new SowCalculator();
        SowResult result = calculator.sow(board, 1, Player.PLAYER1);

        assertThat(result).isEqualTo(SowResult.DEFAULT);
    }

    @Test
    public void sowEndingInBigPitReturnsCorrectResult() {
        Board board = new Board(6);
        SowCalculator calculator = new SowCalculator();
        SowResult result = calculator.sow(board, 0, Player.PLAYER1);

        assertThat(result).isEqualTo(SowResult.ENDED_ON_BIG_PIT);
    }

    @Test
    public void sowEndingInEmptySquareOfPlayerCalculatesBigPitCorrectly() {
        SowCalculator calculator = new SowCalculator();

        Board board = new Board(Map.of(
                Player.PLAYER1, new PlayerBoard(List.of(1, 0, 2, 2, 4, 6), 0),
                Player.PLAYER2, new PlayerBoard(List.of(3, 4, 5, 2, 2, 1), 0)
        ));

        calculator.sow(board, 0, Player.PLAYER1);

        assertThat(board.getBigPit(Player.PLAYER1)).isEqualTo(3);
    }

    @Test
    public void sowEndingInEmptySquareOfPlayerCalculatesSmallPitsCorrectly() {
        SowCalculator calculator = new SowCalculator();

        Board board = new Board(Map.of(
                Player.PLAYER1, new PlayerBoard(List.of(3, 5, 5, 0, 6, 7), 0),
                Player.PLAYER2, new PlayerBoard(List.of(4, 2, 9, 5, 0, 3), 0)
        ));

        calculator.sow(board, 0, Player.PLAYER1);

        assertThat(board.getSmallPits(Player.PLAYER1)).isEqualTo(List.of(0, 6, 6, 0, 6, 7));
        assertThat(board.getSmallPits(Player.PLAYER2)).isEqualTo(List.of(4, 2, 0, 5, 0, 3));
    }
}
