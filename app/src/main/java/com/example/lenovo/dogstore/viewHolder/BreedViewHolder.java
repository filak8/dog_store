package com.example.lenovo.dogstore.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.dogstore.R;

public class BreedViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvBreed;

    public BreedViewHolder(View v) {
        super(v);
        mTvBreed = (TextView) v.findViewById(R.id.text_view);
    }

    public TextView getmTvBreed() {
        return mTvBreed;
    }
}
