package com.example.gametracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayersListAdapter extends RecyclerView.Adapter<PlayersListAdapter.PlayerViewHolder> {

    private final List<Player> playerList = new ArrayList<>();
    private LayoutInflater mInflater;

    public PlayersListAdapter(Context context, List<Player> list) {
        mInflater = LayoutInflater.from(context);
        this.playerList.addAll(list);
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(R.layout.playerlist_item, parent, false);
        return new PlayerViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player mCurrent = playerList.get(position);
        holder.nameTextView.setText(mCurrent.getName());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView nameTextView;
        final PlayersListAdapter mAdapter;

        public PlayerViewHolder(View itemView, PlayersListAdapter adapter) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*
            int mPosition = getLayoutPosition();
            Player player = playerList.get(mPosition);
            String newName = "Clicked! " + player.getName();
            player.setName(newName);
            playerList.set(mPosition, player);
            mAdapter.notifyDataSetChanged();
            */
        }
    }
}
