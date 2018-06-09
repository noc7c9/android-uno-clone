package com.athir.uno.gamelogic;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Interface that represents a card.
 */
public interface ICard {

    /**
     * @return the color of the card
     */
    ICard.Color getColor();

    /**
     * @return the rank of the card
     */
    ICard.Rank getRank();

    /**
     * Returns a list of moves that this card be used for.
     *
     * @param gameState the game state object
     * @return the list of moves
     */
    List<IMove> getMoves(GameState gameState);

    /**
     * Returns whether or not the card be played on the given card.
     *
     * @param cardPlayedOn the card to be played on
     * @return should be true if the play is valid
     */
    boolean isValidOn(ICard cardPlayedOn);

    /**
     * @param gameState the game state object
     */
    void onPlay(GameState gameState);

    /**
     * All possible card colors.
     */
    @SuppressWarnings("JavaDoc")
    enum Color {
        RED("Red"), BLUE("Blue"), YELLOW("Yellow"), GREEN("Green");

        private final String displayName;

        /**
         * @param displayName the enum name for display purposes
         */
        Color(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    /**
     * All possible card ranks.
     */
    @SuppressWarnings("JavaDoc")
    enum Rank {
        NUM_0("0"), NUM_1("1"), NUM_2("2"), NUM_3("3"), NUM_4("4"),
        NUM_5("5"), NUM_6("6"), NUM_7("7"), NUM_8("8"), NUM_9("9"),

        SKIP("Skip"), REVERSE("Reverse"), DRAW_TWO("Draw +2"),
        WILD("Wild"), WILD_FOUR("Wild +4");

        private final String displayName;

        /**
         * @param displayName the enum name for display purposes
         */
        Rank(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }

        /**
         * Returns the enum value that corresponds to the given int.
         *
         * Throws an InvalidParameterException if the number is out of range.
         *
         * @param number int value of the expected card
         * @return the enum value
         */
        static Rank getNumberRank(int number) {
            switch (number) {
                case 0: return NUM_0;
                case 1: return NUM_1;
                case 2: return NUM_2;
                case 3: return NUM_3;
                case 4: return NUM_4;
                case 5: return NUM_5;
                case 6: return NUM_6;
                case 7: return NUM_7;
                case 8: return NUM_8;
                case 9: return NUM_9;
                default:
                    throw new InvalidParameterException("number rank must be in the range 0 to 9");
            }
        }
    }

}
