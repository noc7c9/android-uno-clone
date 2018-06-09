package com.athir.uno.gamelogic;

/**
 * Represents a reverse card.
 */
class ReverseCard extends AbstractBaseCard {

    /**
     * Create a reverse card of the given color
     *
     * @param color the color of the card
     */
    ReverseCard(ICard.Color color) {
        super(color, Rank.REVERSE);
    }

    @Override
    public void onPlay(GameState gameState) {
        if (gameState.getNumPlayers() == 2) {
            gameState.skipNextPlayer();
        } else {
            gameState.reverseTurnOrder();
        }
    }

}
