package com.aksu.mancalagame.mancala.web;

import com.aksu.mancalagame.mancala.domain.Board;
import com.aksu.mancalagame.mancala.domain.MancalaGame;
import com.aksu.mancalagame.mancala.domain.Player;
import com.aksu.mancalagame.mancala.domain.PlayerBoard;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GameControllerTest {
    @Test
    public void givenModelResultsGameInformationPutInModel() throws Exception {
        GameController controller = new GameController(new MancalaGame());

        Model model = new ConcurrentModel();
        controller.gameView(model);

        assertThat(model.containsAttribute("board")).isTrue();
        assertThat(model.containsAttribute("currentPlayer")).isTrue();
    }

    @Test
    public void selectingFifthPitForPlayer1ResultsInCorrectBoard() throws Exception {
        GameController controller = new GameController(new MancalaGame());
        controller.selectPit("1");

        Model model = new ConcurrentModel();
        controller.gameView(model);

        BoardView boardContent = (BoardView) model.getAttribute("board");

        assertThat(boardContent).isNotNull();
        assertThat(boardContent.player1pits())
                .isEqualTo(List.of(6, 0, 7, 7, 7, 7));
    }


    @Test
    public void initiallyWinnerAttributeMustNotExistInModel() throws Exception {
        GameController controller = new GameController(new MancalaGame());

        Model model = new ConcurrentModel();
        controller.gameView(model);

        assertThat(model.containsAttribute("winner")).isFalse();
    }

    @Test
    public void afterGameEndingMoveWinnerAttributeMustExistInModel() throws Exception {
        Board board = new Board(Map.of(
                Player.PLAYER1, new PlayerBoard(List.of(0, 0, 0, 0, 0, 4), 5),
                Player.PLAYER2, new PlayerBoard(List.of(1, 4, 5, 6, 2, 0), 3)
        ));
        MancalaGame game = new MancalaGame(board, Player.PLAYER1);
        GameController controller = new GameController(game);

        controller.selectPit("5");

        Model model = new ConcurrentModel();
        controller.gameView(model);

        assertThat(model.containsAttribute("winner")).isTrue();
    }
}