package com.example.gametracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

public class GamesListAdapter extends RecyclerView.Adapter<GamesListAdapter.GameViewHolder> {
    private List<Game> mGamesList;
    private LayoutInflater mInflater;
    private int mView;
    private Context mContext;

    public GamesListAdapter(Context context, List<Game> list, int v) {
        mInflater = LayoutInflater.from(context);
        this.mView = v;
        this.mGamesList = list;
        this.mContext = context;
    }

    @Override
    public GamesListAdapter.GameViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(mView, parent, false);
        return new GameViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(final GamesListAdapter.GameViewHolder holder, final int position) {
        final Game mCurrent = mGamesList.get(holder.getAdapterPosition());
        holder.gameTextView.setText(mCurrent.getDate());
        editButtonClickListener(holder, mCurrent);
        deleteButtonClickListener(holder);
    }

    private void deleteButtonClickListener(final GamesListAdapter.GameViewHolder holder) {
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game deletedGame = mGamesList.get(holder.getAdapterPosition());
                boolean isDeleted = false;
                try {
                    DataSourceHelper pds = new DataSourceHelper(mContext);
                    pds.open();
                    isDeleted = pds.deleteGame(deletedGame);
                    pds.close();
                } catch (Exception e) {
                    Log.d(TAG, "onClick: Unable to delete player");
                    Toast.makeText(mContext, "Unable to delete " + deletedGame.getDate(), Toast.LENGTH_SHORT).show();
                }

                if(isDeleted) {
                    mGamesList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    Toast.makeText(mContext, deletedGame.getDate() + " deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Unable to delete " + deletedGame.getDate(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editButtonClickListener(GamesListAdapter.GameViewHolder holder, final Game mCurrent) {
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, new_game.class);
                intent.putExtra("id", Integer.toString(mCurrent.getId()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGamesList.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView gameTextView;
        public final ImageButton editButton;
        public final ImageButton deleteButton;
        final GamesListAdapter mAdapter;

        public GameViewHolder(View itemView, GamesListAdapter adapter) {
            super(itemView);
            gameTextView = itemView.findViewById(R.id.gameTextView);
            editButton = itemView.findViewById(R.id.gameEditButton);
            deleteButton = itemView.findViewById(R.id.gameDeleteButton);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Game game = mGamesList.get(mPosition);
            Toast.makeText(mContext,"You clicked " + game.getDate(), Toast.LENGTH_LONG).show();
        }
    }

}
