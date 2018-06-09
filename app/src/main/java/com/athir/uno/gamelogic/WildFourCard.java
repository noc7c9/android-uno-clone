package com.athir.uno.gamelogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a wild four card.
 */
class WildFourCard extends AbstractBaseCard {

    /**
     * Create a wild four card
     */
    WildFourCard() {
        super(null, Rank.WILD_FOUR);
    }

    private WildFourCard(ICard.Color color, int cardID) {
        super(color, Rank.WILD_FOUR, cardID);
    }

    @Override
    public void onPlay(GameState gameState) {
        gameState.forceDrawNextPlayer(4);
        gameState.skipNextPlayer();
    }

    @Override
    public boolean isValidOn(ICard cardPlayedOn) {
        return true;
    }

    @Override
    public List<IMove> getMoves(GameState gameState) {
        List<IMove> moves = new ArrayList<>();

        for (ICard.Color color : ICard.Color.values()) {
            WildFourCard coloredClone = new WildFourCard(color, getCardID());
            moves.add(new PlayCardMove(gameState.getCurrentTurn(), coloredClone));
        }

        return moves;
    }

}
