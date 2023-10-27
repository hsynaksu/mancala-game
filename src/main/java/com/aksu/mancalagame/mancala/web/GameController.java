package com.aksu.mancalagame.mancala.web;

import com.aksu.mancalagame.mancala.domain.MancalaGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GameController {
    final MancalaGame mancalaGame;

    @Autowired
    public GameController(MancalaGame mancalaGame) {
        this.mancalaGame = mancalaGame;
    }

    @GetMapping("/")
    public String gameView(Model model) {
        BoardView boardView = BoardView.from(mancalaGame.getBoardContent());

        model.addAttribute("board", boardView);
        model.addAttribute("currentPlayer", mancalaGame.getCurrentPlayer());
        model.addAttribute("winner", mancalaGame.getWinner());

        return "game";
    }

    @PostMapping("/select-pit")
    public String selectPit(String pitIndex) {
        mancalaGame.makeMove(Integer.parseInt(pitIndex));

        return "redirect:/";
    }
}
