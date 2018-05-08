package com.athir.uno.ui;

import android.graphics.Color;

import com.athir.uno.R;
import com.athir.uno.gamecore.CardColor;

public final class Utility {

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
