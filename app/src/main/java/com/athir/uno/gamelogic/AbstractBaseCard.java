package com.athir.uno.gamelogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class that implements a lot of shared functionality for most cards.
 */
abstract class AbstractBaseCard implements ICard {

    private static int globalCardID = 0;
    private int cardID;

    private final ICard.Color color;
    private final ICard.Rank rank;

    /**
     * Create a card of the given color and rank.
     *
     * @param color the color of the card
     * @param rank the rank of the card
     */
    AbstractBaseCard(ICard.Color color, ICard.Rank rank) {
        this.color = color;
        this.rank = rank;

        cardID = globalCardID++;
    }

    /**
     * Create a card of the given color, rank and ID.
     *
     * @param color the color of the card
     * @param rank the rank of the card
     * @param cardID the id to use
     */
    AbstractBaseCard(ICard.Color color, ICard.Rank rank, int cardID) {
        this(color, rank);
        this.cardID = cardID;
    }

    @Override
    public String toString() {
        String colorString = color != null ? color.toString() : "Uncolored";
        String rankString = rank.toString();
        return colorString + " " + rankString;
    }

    /**
     * @return the card's ID
     */
    int getCardID() {
        return cardID;
    }

    @Override
    public ICard.Color getColor() {
        return color;
    }

    @Override
    public ICard.Rank getRank() {
        return rank;
    }

    @Override
    public List<IMove> getMoves(GameState gameState) {
        List<IMove> moves = new ArrayList<>();

        moves.add(new PlayCardMove(gameState.getCurrentTurn(), this));

        return moves;
    }

    @Override
    public boolean isValidOn(ICard cardPlayedOn) {
        boolean isSameColor = cardPlayedOn.getColor() == null
            || cardPlayedOn.getColor() == this.getColor();
        boolean isSameValue = cardPlayedOn.getRank() == this.getRank();

        return isSameColor || isSameValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AbstractBaseCard)) {
            return false;
        }
        AbstractBaseCard otherCard = (AbstractBaseCard) o;
        return cardID == otherCard.cardID;
    }

    @Override
    public int hashCode() {
        return cardID;
    }

}
