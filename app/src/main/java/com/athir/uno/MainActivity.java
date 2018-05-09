package com.athir.uno;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.athir.uno.gamelogic.Card;
import com.athir.uno.gamelogic.GameState;
import com.athir.uno.gamelogic.IPlayer;
import com.athir.uno.gamelogic.RandomAIPlayer;
import com.athir.uno.gamelogic.Referee;
import com.athir.uno.ui.DiscardMoveItem;
import com.athir.uno.ui.DrawMoveItem;
import com.athir.uno.ui.IMoveItem;
import com.athir.uno.ui.InvalidMoveItem;
import com.athir.uno.ui.MoveViewAdaptor;
import com.athir.uno.ui.UIUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements IPlayer {

    private static final int PLAYER_ID = 0;
    private static final int CPU_ID = 1;

    private TextView cpuHandText;
    private TextView playerHandText;
    private TextView drawPileText;
    private TextView discardPileText;

    private MoveViewAdaptor handViewAdaptor;

    private Referee referee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpuHandText = findViewById(R.id.cpuHand);
        playerHandText = findViewById(R.id.playerHand);
        drawPileText = findViewById(R.id.drawPile);
        discardPileText = findViewById(R.id.discardPile);


        List<IPlayer> players = new ArrayList<>();
        players.add(PLAYER_ID, this);
        players.add(CPU_ID, new RandomAIPlayer());

        GridView handView = findViewById(R.id.handView);
        handViewAdaptor = new MoveViewAdaptor(this);
        handView.setAdapter(handViewAdaptor);

        handView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private MoveItemPlayVisitor playVisitor;

            public void onItemClick(AdapterView parent, View v, int position, long id) {
                if (playVisitor == null) {
                    playVisitor = new MoveItemPlayVisitor(referee);
                }
                handViewAdaptor.getItem(position).accept(playVisitor);
            }
        });

        referee = new Referee(players);
    }

    @Override
    public void updateState(int playerID, Card topDiscardCard, int drawPileSize,
                            List<Integer> handSizes, List<Card> hand) {
        cpuHandText.setText(String.format(Locale.getDefault(),
                "CPU Hand: %d cards", handSizes.get(CPU_ID)));
        playerHandText.setText(String.format(Locale.getDefault(),
                "Your Hand: %d cards", handSizes.get(PLAYER_ID)));

        drawPileText.setText(String.format(Locale.getDefault(),
                "%d cards\nremaining\nin deck", drawPileSize));

        discardPileText.setText(topDiscardCard.toString());
        discardPileText.setBackgroundColor(
                UIUtility.cardColorToColorValue(topDiscardCard.getColor(), this));

        // update the moves view
        List<IMoveItem> moveItems = new ArrayList<>(hand.size() + 1);

        for (Card card : hand) {
            if (GameState.isValidPlay(topDiscardCard, card)) {
                moveItems.add(new DiscardMoveItem(card));
            } else {
                moveItems.add(new InvalidMoveItem(card));
            }
        }
        moveItems.add(new DrawMoveItem());

        handViewAdaptor.updateMoveItems(moveItems);
    }

    @Override
    public void requestMove(Referee referee) {

    }

}