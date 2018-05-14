package com.athir.uno.ui;

import android.content.Context;

import com.athir.uno.R;
import com.athir.uno.gamelogic.ICard;

/**
 * Utility functions for the user interface code.
 */
public final class UIUtility {

    /**
     * This class shouldn't be instantiated.
     */
    private UIUtility() {}

    /**
     * Turn a card color enum value to a concrete color value.
     *
     * @param cardColor the card color
     * @param context   the context for the color to be resolved
     * @return the color value
     */
    public static int cardColorToColorValue(ICard.Color cardColor, Context context) {
        int colorId = cardColorToColorId(cardColor);
        return context.getResources().getColor(colorId);
    }

    /**
     * Turn a card color enum value to a color resource id.
     *
     * @param cardColor the card color
     * @return the resource id
     */
    public static int cardColorToColorId(ICard.Color cardColor) {
        switch (cardColor) {
            case RED:
                return R.color.colorRedCard;
            case YELLOW:
                return R.color.colorYellowCard;
            case GREEN:
                return R.color.colorGreenCard;
            case BLUE:
                return R.color.colorBlueCard;
            default:
                return R.color.uncolored;
        }
    }

}
