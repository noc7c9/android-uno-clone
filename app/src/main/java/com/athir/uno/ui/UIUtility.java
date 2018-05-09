package com.athir.uno.ui;

import android.content.Context;

import com.athir.uno.R;
import com.athir.uno.gamelogic.CardColor;

public final class UIUtility {

    public static int cardColorToColorValue(CardColor cardColor, Context context) {
        int colorId = cardColorToColorId(cardColor);
        return context.getResources().getColor(colorId);
    }

    public static int cardColorToColorId(CardColor cardColor) {
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
