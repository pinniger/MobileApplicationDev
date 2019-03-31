package com.example.gametracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PlayersListAdapter extends RecyclerView.Adapter<PlayersListAdapter.PlayerViewHolder> {

    private final List<Player> playerList = new ArrayList<>();
    private LayoutInflater mInflater;
    private int view;
    private Context mContext;

    public PlayersListAdapter(Context context, List<Player> list, int v) {
        mInflater = LayoutInflater.from(context);
        this.view = v;
        this.playerList.addAll(list);
        mContext = context;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(view, parent, false);
        return new PlayerViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(final PlayerViewHolder holder, final int position) {
        final Player mCurrent = playerList.get(holder.getAdapterPosition());
        holder.nameTextView.setText(mCurrent.getName());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, new_player.class);
                intent.putExtra("id", Integer.toString(mCurrent.getId()));
                mContext.startActivity(intent);
                //Toast.makeText(mContext, "You want to edit " + mCurrent.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player deletedPlayer = playerList.get(holder.getAdapterPosition());
                boolean isDeleted = false;
                try {
                    PlayersDataSource pds = new PlayersDataSource(mContext);
                    pds.open();
                    isDeleted = pds.deletePlayer(deletedPlayer);
                    pds.close();
                } catch (Exception e) {
                    Log.d(TAG, "onClick: Unable to delete player");
                    Toast.makeText(mContext, "Unable to delete " + deletedPlayer.getName(), Toast.LENGTH_SHORT).show();
                }

                if(isDeleted) {
                    playerList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    Toast.makeText(mContext, deletedPlayer.getName() + " deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Unable to delete " + deletedPlayer.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView nameTextView;
        public final ImageButton editButton;
        public final ImageButton deleteButton;
        final PlayersListAdapter mAdapter;

        public PlayerViewHolder(View itemView, PlayersListAdapter adapter) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int mPosition = getLayoutPosition();
            Player player = playerList.get(mPosition);
            Toast.makeText(mContext,"You clicked " + player.getName(), Toast.LENGTH_LONG).show();

            /*
            String newName = "Clicked! " + player.getName();
            player.setName(newName);
            playerList.set(mPosition, player);
            mAdapter.notifyDataSetChanged();
            */
        }
    }
}
