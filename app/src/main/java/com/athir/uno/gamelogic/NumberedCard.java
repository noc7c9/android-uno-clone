package com.athir.uno.gamelogic;

/**
 * Represents a numbered card.
 */
class NumberedCard extends AbstractBaseCard {

    /**
     * Create a numbered card of the given color and rank
     *
     * @param color the color of the card
     * @param rank the rank of the card as in int
     */
    NumberedCard(ICard.Color color, int rank) {
        super(color, ICard.Rank.getNumberRank(rank));
    }

    @Override
    public void onPlay(GameState gameState) {
    }

}
