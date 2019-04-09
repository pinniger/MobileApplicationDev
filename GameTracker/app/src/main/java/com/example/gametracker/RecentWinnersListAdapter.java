package com.example.gametracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecentWinnersListAdapter extends RecyclerView.Adapter<RecentWinnersListAdapter.RecentWinnersViewHolder> {

    private List<RecentWinner> mRecentWinners;
    private LayoutInflater mInflater;
    private int mView;
    private Context mContext;

    public RecentWinnersListAdapter(Context context, List<RecentWinner> list, int v) {
        this.mInflater = LayoutInflater.from(context);
        this.mView = v;
        this.mRecentWinners = list;
        this.mContext = context;
    }

    @Override
    public RecentWinnersViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(mView, parent, false);
        return new RecentWinnersViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(final RecentWinnersViewHolder holder, final int position) {
        final RecentWinner mCurrent = mRecentWinners.get(holder.getAdapterPosition());
        holder.nameTextView.setText(mCurrent.getName());
        holder.dateTextView.setText(new SimpleDateFormat("MMMM dd").format(mCurrent.getDate()));

        holder.imageView.setImageDrawable(Helper.getDrawableName(mCurrent.getName()));
    }

    @Override
    public int getItemCount() {
        return mRecentWinners.size();
    }

    class RecentWinnersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView dateTextView;
        public final TextView nameTextView;
        public final ImageView imageView;
        final RecentWinnersListAdapter mAdapter;

        public RecentWinnersViewHolder(View itemView, RecentWinnersListAdapter adapter) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.text_recent_winners_date);
            nameTextView = itemView.findViewById(R.id.text_recent_winners_name);
            imageView = itemView.findViewById(R.id.image_recent_winners);
            mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // open the profile activity for the clicked player
            int mPosition = getLayoutPosition();
            RecentWinner player = mRecentWinners.get(mPosition);
            Intent intent = new Intent(mContext, profile.class);
            intent.putExtra("id", player.getPlayerId());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        }
    }
}
