package com.aksu.mancalagame.mancala;

import com.aksu.mancalagame.mancala.domain.MancalaGame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MancalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MancalaApplication.class, args);
	}

	// Added to let spring control the game object
	// and keep domain separate from spring
	@Bean
	public MancalaGame createGame() {
		return new MancalaGame();
	}
}
