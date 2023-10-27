# Mancala Game

This is an ancient turn-based strategy board game implemented with Spring Boot and Thymeleaf.
I tried to follow Hexagonal architecture during development and developed with TDD.

## Terminology and Rules

GAME has 2 players and each of them have 6 small pits and 1 big pit on the board.
Game starts with 6 stones on each of the small pits.
We know a game is over when one of the players empties all stones in their small pits.

## Playing the Game

### Start the Game

* Within IntelliJ IDEA: Run the `MancalaApplication` class

* From the command line: Use `./mvnw spring-boot:run` (or Windows: `mvnw spring-boot:run`).

### Open in Browser

In your browser, go to: http://localhost:8080/

## Development Next Steps

* Draw logic
* Restart/New Game logic
* Store game state and front-end cookies to access the game, maybe ability to access game by game id
* Backend validations for illegal moves etc.