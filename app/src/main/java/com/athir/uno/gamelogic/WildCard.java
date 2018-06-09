package com.athir.uno.gamelogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a wild card.
 */
class WildCard extends AbstractBaseCard {

    /**
     * Create a wild card
     */
    WildCard() {
        super(null, Rank.WILD);
    }

    private WildCard(ICard.Color color, int cardID) {
        super(color, Rank.WILD, cardID);
    }

    @Override
    public void onPlay(GameState gameState) {
        gameState.forceDrawNextPlayer(2);
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
            WildCard coloredClone = new WildCard(color, getCardID());
            moves.add(new PlayCardMove(gameState.getCurrentTurn(), coloredClone));
        }

        return moves;
    }

}
