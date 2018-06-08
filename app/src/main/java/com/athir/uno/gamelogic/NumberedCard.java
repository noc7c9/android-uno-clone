package com.athir.uno.gamelogic;

/**
 * Represents a numbered card.
 */
class NumberedCard implements ICard {

    private ICard.Color color;
    private ICard.Rank rank;

    /**
     * Create a numbered card of the given color and rank
     *
     * @param color the color of the card
     * @param rank the rank of the card as in int
     */
    NumberedCard(ICard.Color color, int rank) {
        this.color = color;
        this.rank = ICard.Rank.getNumberRank(rank);
    }

    public String toString() {
        String colorString = this.color.toString();
        String rankString = this.rank.toString();
        return colorString + " " + rankString;
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
    public void onPlay(GameState gameState) {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof NumberedCard)) {
            return false;
        }
        NumberedCard card = (NumberedCard) o;
        return color == card.color && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return color.ordinal() * 1000 + rank.ordinal();
    }

}