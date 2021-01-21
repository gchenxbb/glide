package com.glide.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolder> {


    private List<String> mfilenames;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;

        public ViewHolder(View view) {
            super(view);
            fileName = (TextView) view.findViewById(R.id.tv_name);
        }

    }

    public LocalAdapter(List<String> filenames) {
        mfilenames = filenames;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_localview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mfilenames.get(position);
        holder.fileName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mfilenames.size();
    }
}