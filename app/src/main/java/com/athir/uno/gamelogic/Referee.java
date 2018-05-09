package com.athir.uno.gamelogic;

import java.util.List;

public class Referee {

    private GameState gameState;
    private List<IPlayer> players;

    public Referee(List<IPlayer> players) {
        this.players = players;
        this.gameState = new GameState(players.size());

        updatePlayersOnStateChange();
        requestNextMove();
    }

    public void play(IMove move) {
        gameState.play(move);
        updatePlayersOnStateChange();
        requestNextMove();
    }

    private void updatePlayersOnStateChange() {
        int playerID = 0;
        for (IPlayer player : players) {
            player.updateState(
                    playerID,
                    gameState.getTopCard(),
                    gameState.getDrawPileSize(),
                    gameState.getHandSizes(),
                    gameState.getHand(playerID)
            );
            playerID += 1;
        }
    }

    private void requestNextMove() {
        if (gameState.isGameOver()) {
            notifyPlayersOnGameOver();
        } else {
            players.get(gameState.getCurrentTurn()).requestMove(this, gameState.getMoves());
        }
    }

    private void notifyPlayersOnGameOver() {
        int winner = gameState.getWinner();
        int playerID = 0;
        for (IPlayer player : players) {
            player.notifyGameOver(playerID == winner);
            playerID += 1;
        }
    }

}