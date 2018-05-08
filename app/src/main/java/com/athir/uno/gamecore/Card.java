package com.athir.uno.gamecore;

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
}