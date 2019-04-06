package com.example.gametracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

public class TopPlayersListAdapter extends RecyclerView.Adapter<TopPlayersListAdapter.TopPlayersViewHolder> {
    private List<PlayerDetail> mTopPlayers;
    private LayoutInflater mInflater;
    private int mView;
    private Context mContext;

    public TopPlayersListAdapter(Context context, List<PlayerDetail> list, int v) {
        this.mInflater = LayoutInflater.from(context);
        this.mView = v;
        this.mTopPlayers = list;
        this.mContext = context;
    }

    @Override
    public TopPlayersListAdapter.TopPlayersViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(mView, parent, false);
        return new TopPlayersListAdapter.TopPlayersViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(final TopPlayersListAdapter.TopPlayersViewHolder holder, final int position) {
        final PlayerDetail mCurrent = mTopPlayers.get(holder.getAdapterPosition());
        holder.nameTextView.setText(mCurrent.getName());
        int playerScore = mCurrent.getTotalScore();
        holder.scoreTextView.setText(Integer.toString(playerScore));

        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(mCurrent.getName().substring(0, 1), generator.getRandomColor());

        holder.imageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return mTopPlayers.size();
    }

    class TopPlayersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView nameTextView;
        public final TextView scoreTextView;
        public final ImageView imageView;
        final TopPlayersListAdapter mAdapter;

        public TopPlayersViewHolder(View itemView, TopPlayersListAdapter adapter) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.text_top_players_name);
            this.scoreTextView = itemView.findViewById(R.id.text_top_players_score);
            this.imageView = itemView.findViewById(R.id.image_top_players);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            PlayerDetail player = mTopPlayers.get(mPosition);
            Intent intent = new Intent(mContext, profile.class);
            intent.putExtra("id", player.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        }
    }
}
