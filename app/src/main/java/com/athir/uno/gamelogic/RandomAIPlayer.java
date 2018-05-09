package com.athir.uno.gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAIPlayer implements IPlayer {

    private Random rng;

    public RandomAIPlayer() {
        rng = new Random();
    }

    @Override
    public void updateState(int playerID, Card topDiscardCard, int drawPileSize,
                            List<Integer> handSizes, List<Card> hand) { }

    @Override
    public void requestMove(Referee referee, List<IMove> moves) {
        RandomSelectMoveVisitor visitor = new RandomSelectMoveVisitor(moves.size());
        for (IMove move : moves) {
            move.accept(visitor);
        }
        referee.play(visitor.getSelection());
    }

    @Override
    public void notifyGameOver(boolean isWinner) { }


    private class RandomSelectMoveVisitor implements IMoveVisitor {

        private List<PlayCardMove> playMoves;
        private DrawCardMove drawMove;

        public RandomSelectMoveVisitor(int capacity) {
            playMoves = new ArrayList<>(capacity);
        }

        public IMove getSelection() {
            if (playMoves.size() > 0) {
                return playMoves.get(rng.nextInt(playMoves.size()));
            } else {
                return drawMove;
            }
        }

        @Override
        public void visit(PlayCardMove move) {
            playMoves.add(move);
        }

        @Override
        public void visit(DrawCardMove move) {
            drawMove = move;
        }

    }

}