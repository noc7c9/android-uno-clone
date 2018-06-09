package com.athir.uno.gamelogic;

/**
 * Represents a skip card.
 */
class SkipCard extends AbstractBaseCard {

    /**
     * Create a skip card of the given color
     *
     * @param color the color of the card
     */
    SkipCard(ICard.Color color) {
        super(color, Rank.SKIP);
    }

    @Override
    public void onPlay(GameState gameState) {
        gameState.skipNextPlayer();
    }

}
