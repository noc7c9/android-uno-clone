package com.athir.uno.gamelogic;

/**
 * Represents a draw two card.
 */
class DrawTwoCard extends AbstractBaseCard {

    /**
     * Create a draw two card of the given color
     *
     * @param color the color of the card
     */
    DrawTwoCard(ICard.Color color) {
        super(color, Rank.DRAW_TWO);
    }

    @Override
    public void onPlay(GameState gameState) {
        gameState.forceDrawNextPlayer(2);
        gameState.skipNextPlayer();
    }

}
