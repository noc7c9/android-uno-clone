package com.athir.uno.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Responsible for populating the move view in the UI according to the given IMoveItems.
 */
public class MoveViewAdaptor extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<IMoveItem> moveItems;

    private ColorStateList defaultTextColors = null;

    /**
     * Creates a MoveViewAdaptor with no move items.
     *
     * @param context the context of the view
     */
    public MoveViewAdaptor(Context context) {
        // Start with a blank view
        this.moveItems = Collections.emptyList();

        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Update the view with new move items.
     * This will replace the old list of move items.
     *
     * @param moveItems list of new move items
     */
    public void updateMoveItems(List<IMoveItem> moveItems) {
        this.moveItems = moveItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return moveItems.size();
    }

    @Override
    public IMoveItem getItem(int position) {
        return moveItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return moveItems.get(position).hashCode();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return moveItems.get(position).isEnabled();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            // Keep a backup of what the default item colors to allow restoring later.
            if (defaultTextColors == null) {
                TextView textView = convertView.findViewById(android.R.id.text1);
                defaultTextColors = textView.getTextColors();
            }
        }

        IMoveItem move = moveItems.get(position);
        TextView textView = convertView.findViewById(android.R.id.text1);

        // Update the text view according to the options in the move item.
        textView.setText(move.getLabel());

        boolean isEnabled = moveItems.get(position).isEnabled();
        textView.setEnabled(isEnabled);

        int colorID = moveItems.get(position).getColorID();
        if (isEnabled && colorID > 0) {
            textView.setTextColor(context.getResources().getColor(colorID));
        } else {
            textView.setTextColor(defaultTextColors);
        }

        return convertView;
    }

}