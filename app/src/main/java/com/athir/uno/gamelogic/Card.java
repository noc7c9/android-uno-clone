package com.athir.uno.gamelogic;

import java.util.Locale;

public class Card {

    private CardColor color;
    private int value;

    public Card(CardColor color, int value) {
        this.color = color;
        this.value = value;
    }

    public String toString() {
        String colorString = this.color.toString();
        Locale locale = Locale.getDefault();
        return String.format(locale, "%s%s %d",
                colorString.charAt(0), colorString.substring(1).toLowerCase(locale), this.value);
    }

    public CardColor getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Card)) {
            return false;
        }
        Card card = (Card) o;
        return color == card.color && value == card.value;
    }

    @Override
    public int hashCode() {
        return color.ordinal() * 1000 + value;
    }

}