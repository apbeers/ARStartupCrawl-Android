package com.example.arstartupcrawl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shawn on 1/10/2018.
 */

public class MyStartupRecyclerViewAdapter extends RecyclerView.Adapter<MyStartupRecyclerViewAdapter.ViewHolder>{

    private List<Startup> startupItems;
    private Context context;

    public MyStartupRecyclerViewAdapter(List<Startup> startupItems, Context context) {
        this.startupItems = startupItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_startups, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Startup startup = startupItems.get(position);

        holder.titleTextView.setText(startup.getTitle());
        holder.content.setText(startup.getDescription());
    }

    @Override
    public int getItemCount() {
        return startupItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleTextView, content;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
