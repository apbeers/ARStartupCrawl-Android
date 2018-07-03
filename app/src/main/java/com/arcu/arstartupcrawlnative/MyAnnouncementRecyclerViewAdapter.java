package com.arcu.arstartupcrawlnative;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arcu.arstartupcrawlnative.AnnouncementFragment.OnListFragmentInteractionListener;


import java.util.List;


public class MyAnnouncementRecyclerViewAdapter extends RecyclerView.Adapter<MyAnnouncementRecyclerViewAdapter.ViewHolder> {

    private final List<Announcement> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAnnouncementRecyclerViewAdapter(List<Announcement> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_announcement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mContentView.setText(mValues.get(position).getDescription());
        holder.mDateTimeView.setText(mValues.get(position).getDateTime());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mContentView;
        public final TextView mDateTimeView;
        public Announcement mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.announcement_title);
            mContentView = (TextView) view.findViewById(R.id.announcement_content);
            mDateTimeView = (TextView) view.findViewById(R.id.announcement_datetime);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
