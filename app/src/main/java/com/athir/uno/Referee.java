package com.athir.uno;

import com.athir.uno.gamelogic.GameState;
import com.athir.uno.gamelogic.IMove;

import java.util.List;

/**
 * Responsible for managing the game state and the players,
 * making sure no each player is notified when the game state changes and when they can move.
 *
 * Designed to support asynchronous players (eg: the user)
 */
class Referee {

    private final GameState gameState;
    private final List<IPlayer> players;

    /**
     * Creates a new game with the given players.
     *
     * @param players the players that will play the game
     */
    Referee(List<IPlayer> players) {
        this.players = players;
        this.gameState = new GameState(players.size());

        iterateOneTurn();
    }

    /**
     * Should be called by a player when they want to make a move.
     *
     * @param move the move to make
     */
    public void play(IMove move) {
        gameState.play(move);

        iterateOneTurn();
    }

    /**
     * Iterate over one turn.
     * Updating players on new state and requesting the next player to make their move
     */
    private void iterateOneTurn() {
        updatePlayersOnStateChange();

        if (gameState.isGameOver()) {
            notifyPlayersOnGameOver();
        } else {
            players.get(gameState.getCurrentTurn()).requestMove(this, gameState.getMoves());
        }
    }

    /**
     * Updates all players with the current state of the game.
     */
    private void updatePlayersOnStateChange() {
        int playerID = 0;
        for (IPlayer player : players) {
            player.updateState(
                    playerID,
                    gameState.getCurrentTurn(),
                    gameState.getTopCard(),
                    gameState.getDrawPileSize(),
                    gameState.getHandSizes(),
                    gameState.getHand(playerID)
            );
            playerID += 1;
        }
    }

    /**
     * Notifies all players when the game is over.
     */
    private void notifyPlayersOnGameOver() {
        int winner = gameState.getWinner();
        int playerID = 0;
        for (IPlayer player : players) {
            player.notifyGameOver(playerID == winner);
            playerID += 1;
        }
    }

}
