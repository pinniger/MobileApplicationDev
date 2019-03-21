package com.example.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
    private final LinkedList<Contact> mContactList;
    private LayoutInflater mInflater;

    public ContactListAdapter(Context context, LinkedList<Contact> contactList) {
        mInflater = LayoutInflater.from(context);
        this.mContactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.contactlist_item, parent, false);
        return new ContactViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact mCurrent = mContactList.get(position);
        holder.nameTextView.setText(mCurrent.getContactName());
        holder.cellTextView.setText(mCurrent.getCellNumber());
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView nameTextView;
        public final TextView cellTextView;
        final ContactListAdapter mAdapter;

        public ContactViewHolder(View itemView, ContactListAdapter adapter) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            cellTextView = itemView.findViewById(R.id.cell);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Contact contact = mContactList.get(mPosition);
            String newName = "Clicked! " + contact.getContactName();
            contact.setContactName(newName);
            mContactList.set(mPosition, contact);
            mAdapter.notifyDataSetChanged();
        }
    }
}
