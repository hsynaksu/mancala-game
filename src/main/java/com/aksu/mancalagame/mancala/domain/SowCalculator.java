package com.aksu.mancalagame.mancala.domain;

import java.util.List;

public class SowCalculator {

    /**
     * Calculates new board state from current board state using pit index and current player.
     * Starts from selected index and if it reaches the end of the small pits,
     * switches players to continue sowing until all stones are sowed.
     */

    public SowResult sow(Board board, int selectedPitIndex, Player player) {
        int numberOfStones = takeStones(board, selectedPitIndex, player);
        int currentIndex = selectedPitIndex + 1;
        Player currentPlayer = player;

        while (numberOfStones > 0) {
            boolean hasArrivedToBigPit = currentIndex == board.getSmallPits(currentPlayer).size();
            boolean isOwnSide = currentPlayer == player;

            if (hasArrivedToBigPit && isOwnSide) {
                board.sowBigPit(currentPlayer);
                numberOfStones--;
            }

            if (!hasArrivedToBigPit) {
                board.sowSmallPit(currentPlayer, currentIndex);
                numberOfStones--;
            }

            boolean sowingEnded = numberOfStones == 0;
            // If sowing continues, move to next pit
            if (!sowingEnded) {
                if (hasArrivedToBigPit) {
                    // Switch sides to continue
                    currentPlayer = currentPlayer.getOpposingPlayer();
                    currentIndex = 0;
                } else {
                    currentIndex++;
                }
            }
        }

        boolean endedOnBigPit = currentIndex == board.getSmallPits(currentPlayer).size();
        boolean endedOnOwnSide = player == currentPlayer;

        if (endedOnBigPit) {
            return SowResult.ENDED_ON_BIG_PIT;
        } else if (endedOnOwnSide) {
            captureStonesIfPossible(board, player, currentIndex);
        }

        return SowResult.DEFAULT;
    }

    private int takeStones(Board board, int selectedPitIndex, Player player) {
        int stones = board.getSmallPits(player).get(selectedPitIndex);
        board.clearPit(player, selectedPitIndex);
        return stones;
    }

    private void captureStonesIfPossible(Board board, Player player, int lastIndex) {
        // If end pit contains one stone only, that means we added that stone and pit was empty before
        boolean isEndPitContainsOneStone = board.getSmallPitContent(player, lastIndex) == 1;

        if (isEndPitContainsOneStone) {
            board.clearPit(player, lastIndex);
            int opposingPitIndex = PlayerBoard.PIT_COUNT - lastIndex - 1;
            Integer opposingSideStones = board.getSmallPits(player.getOpposingPlayer()).get(opposingPitIndex);
            board.clearPit(player.getOpposingPlayer(), opposingPitIndex);
            board.addToBigPit(player, opposingSideStones + 1);
        }
    }

    public void sowAllSmallPitsToBigPit(Board board, Player player) {
        List<Integer> smallPits = board.getSmallPits(player);
        int totalStones = smallPits.stream().mapToInt(Integer::intValue).sum();

        board.addToBigPit(player, totalStones);
        board.clearAllSmallPits(player);
    }
}
