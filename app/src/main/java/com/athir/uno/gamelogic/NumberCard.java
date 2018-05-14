package com.athir.uno.gamelogic;

/**
 * Represents a numbered card.
 */
class NumberCard implements ICard {

    private ICard.Color color;
    private ICard.Rank rank;

    /**
     * Create a numbered card of the given color and rank
     *
     * @param color the color of the card
     * @param rank the rank of the card as in int
     */
    NumberCard(ICard.Color color, int rank) {
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
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof NumberCard)) {
            return false;
        }
        NumberCard card = (NumberCard) o;
        return color == card.color && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return color.ordinal() * 1000 + rank.ordinal();
    }

}