package com.aksu.mancalagame.mancala.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Board {
    private final Map<Player, PlayerBoard> playerBoards;

    public Board(Map<Player, PlayerBoard> playerBoards) {
        this.playerBoards = playerBoards;
    }

    public Board(Integer initialStoneCount) {
        this.playerBoards = Map.of(
                Player.PLAYER1, new PlayerBoard(initialStoneCount),
                Player.PLAYER2, new PlayerBoard(initialStoneCount)
        );
    }

    public List<Integer> getSmallPits(Player player) {
        return playerBoards.get(player).getSmallPits();
    }

    public Integer getSmallPitContent(Player player, int index) {
        return getSmallPits(player).get(index);
    }

    public Integer getBigPit(Player player) {
        return playerBoards.get(player).getBigPit();
    }

    public Player getPlayerWithEmptySmallPits() {
        for (Map.Entry<Player, PlayerBoard> entry : playerBoards.entrySet()) {
            Player player = entry.getKey();
            List<Integer> smallPits = entry.getValue().getSmallPits();
            boolean isAllZeros = smallPits.stream().allMatch(i -> i == 0);

            if (isAllZeros) {
                return player;
            }
        }

        return null;
    }

    public void clearPit(Player player, int index) {
        playerBoards.get(player).clearPit(index);
    }

    public void clearAllSmallPits(Player player) {
        playerBoards.get(player).clearAllSmallPits();
    }

    public void sowSmallPit(Player player, int index) {
        List<Integer> smallPits = playerBoards.get(player).getSmallPits();
        Integer stones = smallPits.get(index);
        smallPits.set(index, stones + 1);
    }

    public void sowBigPit(Player player) {
        addToBigPit(player, 1);
    }

    public void addToBigPit(Player player, Integer stones) {
        PlayerBoard board = playerBoards.get(player);
        board.setBigPit(board.getBigPit() + stones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board that = (Board) o;
        return playerBoards.equals(that.playerBoards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerBoards);
    }
}
