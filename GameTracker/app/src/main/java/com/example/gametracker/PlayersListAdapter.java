package com.example.gametracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import static android.content.ContentValues.TAG;

public class PlayersListAdapter extends RecyclerView.Adapter<PlayersListAdapter.PlayerViewHolder> {

    private  List<Player> mPlayerList;
    private LayoutInflater mInflater;
    private int mView;
    private Context mContext;

    public PlayersListAdapter(Context context, List<Player> list, int v) {
        // init member vars
        this.mInflater = LayoutInflater.from(context);
        this.mView = v;
        this.mPlayerList = list;
        this.mContext = context;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(mView, parent, false);
        return new PlayerViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(final PlayerViewHolder holder, final int position) {

        // bind the view
        final Player mCurrent = mPlayerList.get(holder.getAdapterPosition());
        holder.nameTextView.setText(mCurrent.getName());
        editButtonClickListener(holder, mCurrent);
        deleteButtonClickListener(holder);

        holder.imageView.setImageDrawable(Helper.getDrawableName(mCurrent.getName()));
    }

    private void deleteButtonClickListener(final PlayerViewHolder holder) {
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player deletedPlayer = mPlayerList.get(holder.getAdapterPosition());
                boolean isDeleted = false;
                try {
                    DataSourceHelper pds = new DataSourceHelper(mContext);
                    pds.open();
                    isDeleted = pds.deletePlayer(deletedPlayer);
                    pds.close();
                } catch (Exception e) {
                    Log.d(TAG, "deleteButtonClickListener: Unable to delete player " + e.getMessage());
                    Toast.makeText(mContext, "Unable to delete " + deletedPlayer.getName(), Toast.LENGTH_SHORT).show();
                }

                if(isDeleted) {
                    mPlayerList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    Toast.makeText(mContext, deletedPlayer.getName() + " deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Unable to delete " + deletedPlayer.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editButtonClickListener(PlayerViewHolder holder, final Player mCurrent) {
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, new_player.class);
                intent.putExtra("id", mCurrent.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView nameTextView;
        public final ImageView imageView;
        public final ImageButton editButton;
        public final ImageButton deleteButton;
        final PlayersListAdapter mAdapter;

        public PlayerViewHolder(View itemView, PlayersListAdapter adapter) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            imageView = itemView.findViewById(R.id.image_view);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Player player = mPlayerList.get(mPosition);
            Intent intent = new Intent(mContext, profile.class);
            intent.putExtra("id", player.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        }
    }
}
