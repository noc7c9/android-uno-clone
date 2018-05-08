package com.athir.uno;

import com.athir.uno.gamecore.GameState;
import com.athir.uno.gamecore.ICard;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        GameState gs = new GameState(2);

        for (ICard card : gs.getHand(0)) {
            System.out.println(card.toString());
        }

        assertEquals(5, 2 + 3);
    }

}